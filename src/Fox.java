import java.util.Iterator;
import java.util.List;

/**
 * Modelo de uma raposa no simulador.
 * Raposas caçam coelhos. Balanceado para reprodução e sobrevivência média.
 *
 * Regras:
 * - Come coelhos
 * - Não come leões nem humanos
 * - Reproduz com probabilidade moderada
 *
 * @author
 * @version 2025
 */
public class Fox extends Animal implements Predator
{
    private static final int BREEDING_AGE = 8;
    private static final int MAX_AGE = 150;
    private static final double BREEDING_PROBABILITY = 0.18;
    private static final int MAX_LITTER_SIZE = 3;

    private static final int INITIAL_FOOD_LEVEL = 20;
    private static final int HUNGER_LOSS = 1;
    private static final int RABBIT_FOOD_VALUE = 12;

    private int foodLevel;

    public Fox(boolean randomAge)
    {
        super(randomAge);
        if(randomAge)
            foodLevel = rand.nextInt(INITIAL_FOOD_LEVEL) + (INITIAL_FOOD_LEVEL / 4);
        else
            foodLevel = INITIAL_FOOD_LEVEL;
    }

    @Override
    public void act(Campo currentField, Campo updatedField, List<Animal> newAnimals)
    {
        incrementAge();
        incrementHunger();
        if(!isAlive()) return;

        // reprodução: tentar colocar filhotes em locais adjacentes livres que o ambiente permita
        int births = breed();
        for(int b = 0; b < births; b++) {
            Fox young = new Fox(false);
            Location loc = updatedField.freeAdjacentLocation(location);
            if(loc != null && updatedField.getObjectAt(loc) == null && updatedField.getEnvironment(loc).canEnter(young)) {
                young.setLocation(loc);
                newAnimals.add(young);
                updatedField.place(young, loc);
            }
        }

        // movimento/caça
        Location newLocation = hunt(currentField, location);
        if(newLocation == null) {
            newLocation = updatedField.freeAdjacentLocation(location);
        }

        if(newLocation != null && updatedField.getEnvironment(newLocation).canEnter(this) && updatedField.getObjectAt(newLocation) == null) {
            setLocation(newLocation);
            updatedField.place(this, newLocation);
        } else {
            // tenta permanecer na mesma célula
            if(updatedField.getObjectAt(location) == null && updatedField.getEnvironment(location).canEnter(this)) {
                updatedField.place(this, location);
            } else {
                setDead();
            }
        }
    }

    private void incrementHunger()
    {
        foodLevel -= HUNGER_LOSS;
        if(foodLevel <= 0) setDead();
    }

    @Override
    public Location hunt(Campo field, Location location)
    {
        Iterator<Location> it = field.adjacentLocations(location);
        while(it.hasNext()) {
            Location where = it.next();
            Animal prey = field.getObjectAt(where);
            if(prey instanceof Rabbit && prey.isAlive()) {
                // come o coelho
                prey.setDead();
                // agenda ganho de comida (não ultrapassar limite razoável)
                foodLevel = Math.min(foodLevel + RABBIT_FOOD_VALUE, INITIAL_FOOD_LEVEL);
                return where;
            }
        }
        return null;
    }

    @Override
    protected Animal createOffspring(boolean randomAge)
    {
        return new Fox(randomAge);
    }

    @Override protected int getMaxAge() { return MAX_AGE; }
    @Override protected int getBreedingAge() { return BREEDING_AGE; }
    @Override protected double getBreedingProbability() { return BREEDING_PROBABILITY; }
    @Override protected int getMaxLitterSize() { return MAX_LITTER_SIZE; }

    @Override public int getFoodLevel() { return foodLevel; }
    @Override public boolean isHungry() { return foodLevel < (INITIAL_FOOD_LEVEL / 3); }
}
