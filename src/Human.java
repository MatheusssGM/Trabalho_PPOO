import java.util.Iterator;
import java.util.List;

public class Human extends Animal implements Predator{
    // Características compartilhadas por todos os humanos
    private static final int BREEDING_AGE = 20;
    private static final int MAX_AGE = 100;
    private static final double BREEDING_PROBABILITY = 0.05;
    private static final int MAX_LITTER_SIZE = 2;
    private static final int FOX_FOOD_VALUE = 7;
    private static final int RABBIT_FOOD_VALUE = 3;
    private static final int LION_FOOD_VALUE = 10;

    // Nível de comida do humano
    private int foodLevel;
    
    /**
     * Cria um humano. Pode ser criado recém-nascido ou com idade aleatória.
     * @param randomAge Se verdadeiro, terá idade e nível de fome aleatórios
     */
    public Human(boolean randomAge)
    {
        super(randomAge);
        if(randomAge) {
            foodLevel = rand.nextInt(LION_FOOD_VALUE);
        }
        else {
            foodLevel = LION_FOOD_VALUE;
        }
    }
    
    /**
     * Implementa o comportamento do humano na simulação.
     * Caça leões, raposas e coelhos, se reproduz, envelhece e pode morrer.
     */
    @Override
    public void act(Field currentField, Field updatedField, List<Animal> newAnimals)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            // Reprodução
            int births = breed();
            for(int b = 0; b < births; b++) {
                Human newHuman = new Human(false);
                newAnimals.add(newHuman);
                Location loc = updatedField.randomAdjacentLocation(location);
                if(loc != null) {
                    newHuman.setLocation(loc);
                    updatedField.place(newHuman, loc);
                }
            }
            // Movimento - usa interface Predator para caçar
            Location newLocation = hunt(currentField, location);
            if(newLocation == null) {
                newLocation = updatedField.freeAdjacentLocation(location);
            }
            if(newLocation != null) {
                setLocation(newLocation);
                updatedField.place(this, newLocation);
            }
            else {
                setDead();
            }
        }
    }
    
    /**
     * Aumenta a fome do lobo. Pode resultar em morte.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
    /**
     * Implementa comportamento de caça da interface Predator.
     * Humanos preferem leões mas também comem raposas e coelhos.
     * @param field Campo onde caçar
     * @param location Localização atual
     * @return Local onde a presa foi encontrada, ou null
     */
    @Override
    public Location hunt(Field field, Location location)
    {
        Iterator<Location> adjacentLocations = field.adjacentLocations(location);
        
        // Primeiro procura por raposas (presa preferida)
        Iterator<Location> adjacentLocationsCopy = field.adjacentLocations(location);
        while(adjacentLocationsCopy.hasNext()) {
            Location where = adjacentLocationsCopy.next();
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
        
        // Primeiro procura por raposas (presa preferida)
        while(adjacentLocationsCopy.hasNext()) {
            Location where = adjacentLocationsCopy.next();
            Animal animal = field.getObjectAt(where);
            if(animal instanceof Fox) {
                Fox fox = (Fox) animal;
                if(fox.isAlive()) {
                    fox.setDead();
                    foodLevel = FOX_FOOD_VALUE;
                    return where;
                }
            }
        }
        
        // Se não encontrar raposas, procura por coelhos
        while(adjacentLocations.hasNext()) {
            Location where = adjacentLocations.next();
            Animal animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()) {
                    rabbit.setDead();
                    foodLevel = RABBIT_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    }
    
    /**
     * Métodos implementados da classe abstrata Animal
     */
    @Override
    protected Animal createOffspring(boolean randomAge)
    {
        return new Lion(randomAge);
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
    
    /**
     * Implementa método da interface Predator.
     * @return Nível de comida atual
     */
    @Override
    public int getFoodLevel()
    {
        return foodLevel;
    }
    
    /**
     * Implementa método da interface Predator.
     * @return true se está com fome (foodLevel baixo)
     */
    @Override
    public boolean isHungry()
    {
        return foodLevel < (LION_FOOD_VALUE / 2);
    }
}
