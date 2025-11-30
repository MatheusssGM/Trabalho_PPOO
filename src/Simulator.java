import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.awt.Color;

/**
 * Classe principal do simulador predador-presa com ambientes naturais.
 * Esta versão inclui Humanos, Leões, Raposas e Coelhos.
 * Os ambientes incluem Montanhas, Savanas, Tocas e Planícies.
 *
 * A simulação ocorre em passos discretos, onde cada animal age, se move,
 * caça, reproduz e pode morrer. O campo é atualizado a cada passo.
 *
 * @author Versão modificada
 * @version 2025
 */
public class Simulator
{
    // Configurações de tamanho do campo
    private static final int DEFAULT_WIDTH = 50;
    private static final int DEFAULT_DEPTH = 50;

    // Probabilidades de criação inicial
    private static final double HUMAN_CREATION_PROBABILITY = 0.03;
    private static final double LION_CREATION_PROBABILITY  = 0.03;
    private static final double FOX_CREATION_PROBABILITY   = 0.06;
    private static final double RABBIT_CREATION_PROBABILITY= 0.07;

    // Lista de animais no campo
    private List<Animal> animals;
    // Lista auxiliar para novos animais
    private List<Animal> newAnimals;
    // Campo atual da simulação
    private Field field;
    // Campo usado para montar o próximo passo
    private Field updatedField;
    // Passo atual da simulação
    private int step;
    // Interface gráfica
    private SimulatorView view;

    /**
     * Cria o simulador com tamanhos padrão.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Cria o simulador com profundidade e largura especificadas.
     *
     * @param depth Profundidade do campo.
     * @param width Largura do campo.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("As dimensões devem ser maiores que zero. Usando valores padrão.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

        animals = new ArrayList<>();
        newAnimals = new ArrayList<>();
        field = new Field(depth, width);
        updatedField = new Field(depth, width);

        // Inicializa os ambientes naturais
        populateEnvironments(field);
        populateEnvironments(updatedField);

        // Configuração da interface visual
        view = new SimulatorView(depth, width);
        view.setColor(Fox.class, Color.blue);
        view.setColor(Rabbit.class, Color.orange);
        view.setColor(Lion.class, Color.red);
        view.setColor(Human.class, Color.black);

        reset();
    }

    /**
     * Popula o campo com ambientes fixos:
     * - Montanhas no topo
     * - Savana no meio
     * - Planícies no restante, com Tocas aleatórias
     *
     * @param field Campo onde os ambientes serão posicionados.
     */
    private void populateEnvironments(Field field) {
        Random rand = new Random();

        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {

                if (row < 5) {
                    field.setEnvironmentAt(row, col, new Mountain());
                }
                else if (row >= 20 && row <= 35) {
                    field.setEnvironmentAt(row, col, new Savanna());
                }
                else {
                    if (rand.nextDouble() < 0.01) {
                        field.setEnvironmentAt(row, col, new Burrow());
                    }
                    else {
                        field.setEnvironmentAt(row, col, new Plains());
                    }
                }
            }
        }
    }

    /**
     * Executa uma simulação longa de 500 passos.
     */
    public void runLongSimulation()
    {
        simulate(500);
    }

    /**
     * Executa a simulação por um número definido de passos.
     *
     * @param numSteps Número de passos da simulação.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
        }
    }

    /**
     * Executa um único passo da simulação.
     * Atualiza todos os animais e troca os campos.
     */
    public void simulateOneStep()
    {
        step++;
        newAnimals.clear();

        for(Iterator<Animal> iter = animals.iterator(); iter.hasNext();) {
            Animal animal = iter.next();
            if(animal.isAlive()) {
                animal.act(field, updatedField, newAnimals);
            } else {
                iter.remove();
            }
        }

        animals.addAll(newAnimals);

        Field temp = field;
        field = updatedField;
        updatedField = temp;

        updatedField.clear(); // animais somente!

        view.showStatus(step, field);
    }

    /**
     * Reinicia completamente a simulação.
     * limpa o campo e repopula com animais.
     */
    public void reset()
    {
        step = 0;
        animals.clear();
        field.clear();
        updatedField.clear();

        populate(field);

        view.showStatus(step, field);
    }

    /**
     * Insere os animais iniciais no campo, respeitando as regras
     * de ambiente e probabilidade de criação.
     *
     * @param field Campo onde os animais serão inseridos.
     */
    private void populate(Field field)
    {
        Random rand = new Random();
        field.clear();

        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {

                double probability = rand.nextDouble();
                Environment env = field.getEnvironment(row, col);

                if(probability <= HUMAN_CREATION_PROBABILITY) {
                    Human h = new Human(true);
                    if(env != null && env.canEnter(h)) {
                        animals.add(h);
                        h.setLocation(row, col);
                        field.place(h, row, col);
                    }
                }
                else if(probability <= HUMAN_CREATION_PROBABILITY + LION_CREATION_PROBABILITY) {
                    Lion l = new Lion(true);
                    if(env != null && env.canEnter(l)) {
                        animals.add(l);
                        l.setLocation(row, col);
                        field.place(l, row, col);
                    }
                }
                else if(probability <= HUMAN_CREATION_PROBABILITY + LION_CREATION_PROBABILITY + FOX_CREATION_PROBABILITY) {
                    Fox f = new Fox(true);
                    if(env != null && env.canEnter(f)) {
                        animals.add(f);
                        f.setLocation(row, col);
                        field.place(f, row, col);
                    }
                }
                else if(probability <= HUMAN_CREATION_PROBABILITY + LION_CREATION_PROBABILITY + FOX_CREATION_PROBABILITY + RABBIT_CREATION_PROBABILITY) {
                    Rabbit r = new Rabbit(true);
                    if(env != null && env.canEnter(r)) {
                        animals.add(r);
                        r.setLocation(row, col);
                        field.place(r, row, col);
                    }
                }
            }
        }

        Collections.shuffle(animals);
    }

    /**
     * @return passo atual da simulação.
     */
    public int getStep()
    {
        return step;
    }

    /**
     * @return lista de todos os animais vivos no campo.
     */
    public List<Animal> getAnimals()
    {
        return new ArrayList<>(animals);
    }
}
