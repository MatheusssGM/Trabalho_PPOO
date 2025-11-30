import java.util.List;
import java.util.Iterator;

/**
 * Modelo de uma raposa no simulador.
 * Raposas envelhecem, se movem, caçam coelhos e morrem.
 * Implementa herança da classe Animal e interface Predator.
 * 
 * @author Código melhorado com POO
 * @version 2025
 */
public class Fox extends Animal implements Predator
{
    // Características compartilhadas por todas as raposas
    private static final int BREEDING_AGE = 10;
    private static final int MAX_AGE = 150;
    private static final double BREEDING_PROBABILITY = 0.15;
    private static final int MAX_LITTER_SIZE = 4;
    private static final int RABBIT_FOOD_VALUE = 4;
    
    // Nível de comida da raposa
    private int foodLevel;

    /**
     * Cria uma raposa. Pode ser criada recém-nascida ou com idade aleatória.
     * @param randomAge Se verdadeiro, terá idade e nível de fome aleatórios
     */
    public Fox(boolean randomAge)
    {
        super(randomAge);
        if(randomAge) {
            foodLevel = rand.nextInt(RABBIT_FOOD_VALUE);
        }
        else {
            foodLevel = RABBIT_FOOD_VALUE;
        }
    }
    
    /**
     * Implementa o comportamento da raposa na simulação.
     * Caça coelhos, se reproduz, envelhece e pode morrer.
     */
    @Override
    public void act(Field currentField, Field updatedField, List<Animal> newAnimals)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            // Reprodução - novos filhotes nascem em localizações adjacentes
            int births = breed();
            for(int b = 0; b < births; b++) {
                Fox newFox = new Fox(false);
                newAnimals.add(newFox);
                Location loc = updatedField.randomAdjacentLocation(location);
                if(loc != null) {
                    newFox.setLocation(loc);
                    updatedField.place(newFox, loc);
                }
            }
            // Movimento - usa interface Predator para caçar
            Location newLocation = hunt(currentField, location);
            if(newLocation == null) {  // sem comida - move aleatoriamente
                newLocation = updatedField.freeAdjacentLocation(location);
            }
            if(newLocation != null) {
                setLocation(newLocation);
                updatedField.place(this, newLocation);
            }
            else {
                // não pode se mover - superpopulação
                setDead();
            }
        }
    }
    
    /**
     * Aumenta a fome da raposa. Pode resultar em morte.
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
     * @param field Campo onde caçar
     * @param location Localização atual
     * @return Local onde a presa foi encontrada, ou null
     */
    @Override
    public Location hunt(Field field, Location location)
    {
        Iterator<Location> adjacentLocations = field.adjacentLocations(location);
        while(adjacentLocations.hasNext()) {
            Location where = adjacentLocations.next();
            Animal animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()) { 
                    rabbit.setDead(); // Come o coelho
                    foodLevel = RABBIT_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    }
        
    /**
     * Cria uma nova raposa (método da classe Animal).
     * @param randomAge Se deve ter idade aleatória
     * @return Nova raposa
     */
    @Override
    protected Animal createOffspring(boolean randomAge)
    {
        return new Fox(randomAge);
    }
    
    /**
     * Métodos implementados da classe abstrata Animal
     */
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
        return foodLevel < (RABBIT_FOOD_VALUE / 2);
    }
}
