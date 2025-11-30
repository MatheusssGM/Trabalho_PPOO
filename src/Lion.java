import java.util.List;
import java.util.Iterator;

/**
 * Modelo de um leão no simulador.
 * Lobos são predadores superiores que caçam tanto raposas quanto coelhos.
 * Demonstra extensibilidade do sistema com herança e interface Predator.
 * 
 * @author Código melhorado com POO
 * @version 2025
 */
public class Lion extends Animal implements Predator
{
    // Características compartilhadas por todos os lobos
    private static final int BREEDING_AGE = 15;
    private static final int MAX_AGE = 200;
    private static final double BREEDING_PROBABILITY = 0.05;
    private static final int MAX_LITTER_SIZE = 2;
    private static final int FOX_FOOD_VALUE = 7;
    private static final int RABBIT_FOOD_VALUE = 3;
    
    // Nível de comida do lobo
    private int foodLevel;
    
    /**
     * Cria um leão. Pode ser criado recém-nascido ou com idade aleatória.
     * @param randomAge Se verdadeiro, terá idade e nível de fome aleatórios
     */
    public Lion(boolean randomAge)
    {
        super(randomAge);
        if(randomAge) {
            foodLevel = rand.nextInt(FOX_FOOD_VALUE);
        }
        else {
            foodLevel = FOX_FOOD_VALUE;
        }
    }
    
    /**
     * Implementa o comportamento do leão na simulação.
     * Caça raposas e coelhos, se reproduz, envelhece e pode morrer.
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
                Lion newLion = new Lion(false);
                newAnimals.add(newLion);
                Location loc = updatedField.randomAdjacentLocation(location);
                if(loc != null) {
                    newLion.setLocation(loc);
                    updatedField.place(newLion, loc);
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
     * Aumenta a fome do leão. Pode resultar em morte.
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
     * Leões preferem raposas mas também comem coelhos.
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
        return foodLevel < (FOX_FOOD_VALUE / 2);
    }
}