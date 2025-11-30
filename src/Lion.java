import java.util.List;

/**
 * Modelo de um leão no simulador.
 * Leões caçam raposas e coelhos; reprodução rara mas suficiente para manter população estável.
 *
 * Regras:
 * - Prefere raposas (maior valor)
 * - Come coelhos se não encontrar raposas
 * - Reproduz pouco (ninhadas pequenas)
 *
 * @author
 * @version 2025
 */
public class Lion extends Animal implements Predator
{
    private static final int BREEDING_AGE = 18;
    private static final int MAX_AGE = 180;
    private static final double BREEDING_PROBABILITY = 0.10;
    private static final int MAX_LITTER_SIZE = 2;

    private static final int INITIAL_FOOD_LEVEL = 60;
    private static final int HUNGER_LOSS = 1;
    private static final int FOX_FOOD_VALUE = 14;
    private static final int RABBIT_FOOD_VALUE = 6;

    private int foodLevel;

    public Lion(boolean randomAge)
    {
        super(randomAge);
        if(randomAge)
            foodLevel = rand.nextInt(INITIAL_FOOD_LEVEL) + (INITIAL_FOOD_LEVEL / 4);
        else
            foodLevel = INITIAL_FOOD_LEVEL;
    }

    @Override
    public void act(Field currentField, Field updatedField, List<Animal> newAnimals)
    {
        incrementAge();
        incrementHunger();
        if(!isAlive()) return;

        // reprodução (ninhada pequena)
        int births = breed();
        for(int b = 0; b < births; b++) {
            Lion cub = new Lion(false);
            Location loc = updatedField.freeAdjacentLocation(location);
            if(loc != null && updatedField.getObjectAt(loc) == null && updatedField.getEnvironment(loc).canEnter(cub)) {
                cub.setLocation(loc);
                newAnimals.add(cub);
                updatedField.place(cub, loc);
            }
        }

        // caça / movimento
        Location newLocation = hunt(currentField, location);
        if(newLocation == null) {
            newLocation = updatedField.freeAdjacentLocation(location);
        }

        if(newLocation != null && updatedField.getObjectAt(newLocation) == null && updatedField.getEnvironment(newLocation).canEnter(this)) {
            setLocation(newLocation);
            updatedField.place(this, newLocation);
        } else {
            // tentar permanecer
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
    public Location hunt(Field field, Location location)
    {
        // primeiro procura por raposas (preferência)
        for(Location where : field.adjacentLocationsList(location)) {
            Animal a = field.getObjectAt(where);
            if(a instanceof Fox && a.isAlive()) {
                a.setDead();
                foodLevel = FOX_FOOD_VALUE;
                return where;
            }
        }

        // depois procura por coelhos
        for(Location where : field.adjacentLocationsList(location)) {
            Animal a = field.getObjectAt(where);
            if(a instanceof Rabbit && a.isAlive()) {
                a.setDead();
                foodLevel = RABBIT_FOOD_VALUE;
                return where;
            }
        }

        return null;
    }

    @Override protected Animal createOffspring(boolean randomAge) { return new Lion(randomAge); }
    @Override protected int getMaxAge() { return MAX_AGE; }
    @Override protected int getBreedingAge() { return BREEDING_AGE; }
    @Override protected double getBreedingProbability() { return BREEDING_PROBABILITY; }
    @Override protected int getMaxLitterSize() { return MAX_LITTER_SIZE; }

    @Override public int getFoodLevel() { return foodLevel; }
    @Override public boolean isHungry() { return foodLevel < (INITIAL_FOOD_LEVEL / 4); }
}
