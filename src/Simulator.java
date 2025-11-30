import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.awt.Color;

/**
 * Simulador simples predador-presa melhorado com conceitos de POO.
 * Baseado em um campo contendo coelhos e raposas.
 * Usa herança, polimorfismo, interfaces e generics.
 * 
 * @author Código melhorado com POO
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

    // Lista de animais no campo (usando generics)
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
    
    /**
     * Constrói um campo de simulação com tamanho padrão.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    /**
     * Cria um campo de simulação com o tamanho dado.
     * @param depth Profundidade do campo. Deve ser maior que zero.
     * @param width Largura do campo. Deve ser maior que zero.
     */
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

        // Cria uma visualização do estado de cada localização no campo
        view = new SimulatorView(depth, width);
        view.setColor(Fox.class, Color.blue);
        view.setColor(Rabbit.class, Color.orange);
        view.setColor(Lion.class, Color.red);
        view.setColor(Lion.class, Color.black);
        
        // Configura um ponto inicial válido
        reset();
    }
    
    /**
     * Executa a simulação por um período longo (500 passos).
     */
    public void runLongSimulation()
    {
        simulate(500);
    }
    
    /**
     * Executa a simulação pelo número dado de passos.
     * Para antes se a simulação deixar de ser viável.
     * @param numSteps Número de passos a executar
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
        }
    }
    
    /**
     * Executa um único passo da simulação.
     * Itera sobre todo o campo atualizando o estado de cada animal.
     * Demonstra polimorfismo - todos os animais implementam act().
     */
    public void simulateOneStep()
    {
        step++;
        newAnimals.clear();
        
        // Permite que todos os animais ajam (polimorfismo)
        for(Iterator<Animal> iter = animals.iterator(); iter.hasNext(); ) {
            Animal animal = iter.next();
            if(animal.isAlive()) {
                // Polimorfismo - chama o método act() apropriado
                animal.act(field, updatedField, newAnimals);
            }
            else {
                iter.remove(); // Remove animais mortos da coleção
            }
        }
        
        // Adiciona novos animais nascidos à lista
        animals.addAll(newAnimals);
        
        // Troca os campos no final do passo
        Field temp = field;
        field = updatedField;
        updatedField = temp;
        updatedField.clear();

        // Exibe o novo campo na tela
        view.showStatus(step, field);
    }
        
    /**
     * Reseta a simulação para a posição inicial.
     */
    public void reset()
    {
        step = 0;
        animals.clear();
        field.clear();
        updatedField.clear();
        populate(field);
        
        // Mostra o estado inicial na visualização
        view.showStatus(step, field);
    }
    
    /**
     * Povoa o campo com raposas, coelhos e lobos.
     * @param field Campo a ser povoado
     */
    private void populate(Field field)
    {
        Random rand = new Random();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                double probability = rand.nextDouble();
                if(probability <= WOLF_CREATION_PROBABILITY) {
                    Lion wolf = new Lion(true);
                    animals.add(wolf);
                    wolf.setLocation(row, col);
                    field.place(wolf, row, col);
                }
                else if(probability <= WOLF_CREATION_PROBABILITY + FOX_CREATION_PROBABILITY) {
                    Fox fox = new Fox(true);
                    animals.add(fox);
                    fox.setLocation(row, col);
                    field.place(fox, row, col);
                }
                else if(probability <= WOLF_CREATION_PROBABILITY + FOX_CREATION_PROBABILITY + RABBIT_CREATION_PROBABILITY) {
                    Rabbit rabbit = new Rabbit(true);
                    animals.add(rabbit);
                    rabbit.setLocation(row, col);
                    field.place(rabbit, row, col);
                }
                // caso contrário, deixa a localização vazia
            }
        }
        Collections.shuffle(animals);
    }
    
    /**
     * Obtém o número atual de passos da simulação.
     * @return Passo atual
     */
    public int getStep()
    {
        return step;
    }
    
    /**
     * Obtém a lista atual de animais.
     * @return Lista de animais
     */
    public List<Animal> getAnimals()
    {
        return new ArrayList<>(animals); // Retorna cópia para segurança
    }
}
