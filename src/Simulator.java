import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.awt.Color;

/**
 * Simulador simples predador-presa melhorado com conceitos de POO.
 * Agora inclui a geração de ambientes (Montanha, Savana, Toca, Planície).
 * * @author Código melhorado com POO
 * @version 2025
 */
public class Simulator
{
    // Configurações da simulação
    private static final int DEFAULT_WIDTH = 50;
    private static final int DEFAULT_DEPTH = 50;
    private static final double FOX_CREATION_PROBABILITY = 0.02;
    private static final double RABBIT_CREATION_PROBABILITY = 0.08;
    private static final double WOLF_CREATION_PROBABILITY = 0.005;

    // Lista de animais no campo
    private List<Animal> animals;
    // Lista de animais recém-nascidos
    private List<Animal> newAnimals;
    // Estado atual do campo
    private Field field;
    // Campo usado para construir o próximo estágio
    private Field updatedField;
    // Passo atual da simulação
    private int step;
    // Interface gráfica da simulação
    private SimulatorView view;

    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("As dimensões devem ser maiores que zero.");
            System.out.println("Usando valores padrão.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        animals = new ArrayList<>();
        newAnimals = new ArrayList<>();
        field = new Field(depth, width);
        updatedField = new Field(depth, width);

        // Inicializa os ambientes (Montanhas, Savanas, etc.)
        populateEnvironments(field);
        populateEnvironments(updatedField); // O campo de atualização deve ter o mesmo mapa

        view = new SimulatorView(depth, width);
        view.setColor(Fox.class, Color.blue);
        view.setColor(Rabbit.class, Color.orange);
        view.setColor(Wolf.class, Color.red);

        reset();
    }

    /**
     * Define onde ficam as Montanhas, Savanas, Tocas e Planícies no campo.
     */
    private void populateEnvironments(Field field) {
        Random rand = new Random();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {

                // Exemplo de Mapa:
                // Linhas 0 a 4: Montanhas (No topo)
                if (row < 5) {
                    field.setEnvironmentAt(row, col, new Mountain());
                }
                // Linhas 20 a 35: Savana (No meio)
                else if (row >= 20 && row <= 35) {
                    field.setEnvironmentAt(row, col, new Savanna());
                }
                // O resto é Planície, mas colocaremos algumas Tocas aleatórias
                else {
                    // 1% de chance de ser uma Toca nas áreas de planície
                    if (rand.nextDouble() < 0.01) {
                        field.setEnvironmentAt(row, col, new Burrow());
                    } else {
                        field.setEnvironmentAt(row, col, new Plains());
                    }
                }
            }
        }
    }

    public void runLongSimulation()
    {
        simulate(500);
    }

    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
        }
    }

    public void simulateOneStep()
    {
        step++;
        newAnimals.clear();

        for(Iterator<Animal> iter = animals.iterator(); iter.hasNext(); ) {
            Animal animal = iter.next();
            if(animal.isAlive()) {
                animal.act(field, updatedField, newAnimals);
            }
            else {
                iter.remove();
            }
        }

        animals.addAll(newAnimals);

        Field temp = field;
        field = updatedField;
        updatedField = temp;
        // Importante: Ao limpar o updatedField, não limpamos os Ambientes, apenas os animais
        updatedField.clear();

        view.showStatus(step, field);
    }

    public void reset()
    {
        step = 0;
        animals.clear();
        field.clear();
        updatedField.clear();
        populate(field);
        view.showStatus(step, field);
    }

    private void populate(Field field)
    {
        Random rand = new Random();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                double probability = rand.nextDouble();

                // Antes de criar o animal, verificamos se ele pode nascer ali (se o ambiente permite)
                Environment env = field.getEnvironment(row, col);

                if(probability <= WOLF_CREATION_PROBABILITY) {
                    Wolf wolf = new Wolf(true);
                    // Só coloca se o ambiente permitir
                    if (env != null && env.canEnter(wolf)) {
                        animals.add(wolf);
                        wolf.setLocation(row, col);
                        field.place(wolf, row, col);
                    }
                }
                else if(probability <= WOLF_CREATION_PROBABILITY + FOX_CREATION_PROBABILITY) {
                    Fox fox = new Fox(true);
                    if (env != null && env.canEnter(fox)) {
                        animals.add(fox);
                        fox.setLocation(row, col);
                        field.place(fox, row, col);
                    }
                }
                else if(probability <= WOLF_CREATION_PROBABILITY + FOX_CREATION_PROBABILITY + RABBIT_CREATION_PROBABILITY) {
                    Rabbit rabbit = new Rabbit(true);
                    if (env != null && env.canEnter(rabbit)) {
                        animals.add(rabbit);
                        rabbit.setLocation(row, col);
                        field.place(rabbit, row, col);
                    }
                }
            }
        }
        Collections.shuffle(animals);
    }

    public int getStep()
    {
        return step;
    }

    public List<Animal> getAnimals()
    {
        return new ArrayList<>(animals);
    }
}