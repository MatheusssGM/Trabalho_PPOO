import java.util.List;
import java.util.Iterator;

/**
 * Modelo de um Humano no simulador.
 * Humanos predam somente Leões.
 * São inteligentes, têm boa sobrevivência,
 * mas não quebram o equilíbrio do ecossistema.
 *
 * Configuração: Força moderada (população entre 20–60 após longas simulações).
 *
 * @author
 * @version 2025
 */
public class Human extends Animal implements Predator
{
    // Características da espécie
    private static final int BREEDING_AGE = 20;
    private static final int MAX_AGE = 180;
    private static final double BREEDING_PROBABILITY = 0.10; // Aumentado
    private static final int MAX_LITTER_SIZE = 3;           // Leve aumento

    // Valores nutricionais ao caçar
    private static final int LION_FOOD_VALUE = 28; // Alimenta bem o humano

    // Nível de comida do humano
    private int foodLevel;

    /**
     * Cria um humano.
     */
    public Human(boolean randomAge)
    {
        super(randomAge);
        foodLevel = 120; // Mais resistente (mas não exagerado)
    }

    /**
     * Comportamento do humano.
     */
    @Override
    public void act(Campo currentField, Campo updatedField, List<Animal> newAnimals)
    {
        incrementAge();
        incrementHunger();

        if(isAlive()) {

            // Reprodução
            int births = breed();
            for(int b = 0; b < births; b++) {
                Human baby = new Human(false);
                Location loc = updatedField.randomAdjacentLocation(location);
                if(loc != null && updatedField.getObjectAt(loc) == null &&
                        updatedField.getEnvironment(loc).canEnter(baby)) {
                    baby.setLocation(loc);
                    updatedField.place(baby, loc);
                    newAnimals.add(baby);
                }
            }

            // Caça
            Location huntLoc = hunt(currentField, location);

            Location newLocation = huntLoc;
            if(newLocation == null) { // não achou comida
                newLocation = updatedField.freeAdjacentLocation(location);
            }

            if(newLocation != null &&
                    updatedField.getObjectAt(newLocation) == null &&
                    updatedField.getEnvironment(newLocation).canEnter(this)) {

                setLocation(newLocation);
                updatedField.place(this, newLocation);
            }
            else {
                setDead(); // superlotação
            }
        }
    }

    /**
     * Fome ao longo do tempo.
     */
    private void incrementHunger()
    {
        foodLevel -= 1; // Consumo normal
        if(foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Humanos caçam apenas leões.
     */
    @Override
    public Location hunt(Campo field, Location location)
    {
        Iterator<Location> it = field.adjacentLocations(location);
        while(it.hasNext()) {
            Location where = it.next();
            Animal animal = field.getObjectAt(where);

            if(animal instanceof Lion) {
                Lion lion = (Lion) animal;
                if(lion.isAlive()) {
                    lion.setDead();
                    foodLevel = LION_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    }

    // Implementações da classe Animal
    @Override
    protected Animal createOffspring(boolean randomAge)
    {
        return new Human(randomAge);
    }

    @Override
    protected int getMaxAge()
    {
        return MAX_AGE;
    }

    @Override
    protected int getBreedingAge()
    {
        return BREEDING_AGE;
    }

    @Override
    protected double getBreedingProbability()
    {
        return BREEDING_PROBABILITY;
    }

    @Override
    protected int getMaxLitterSize()
    {
        return MAX_LITTER_SIZE;
    }

    // Métodos da interface Predator

    @Override
    public int getFoodLevel()
    {
        return foodLevel;
    }

    @Override
    public boolean isHungry()
    {
        return foodLevel < 60;
    }
}
