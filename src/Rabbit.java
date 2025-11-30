import java.util.List;
import java.util.Iterator;

/**
 * Modelo de um coelho no simulador.
 * Coelhos envelhecem, se movem, se reproduzem e morrem.
 * Implementa herança da classe Animal e interface Prey.
 * 
 * @author Código melhorado com POO
 * @version 2025
 */
public class Rabbit extends Animal implements Prey
{
    // Características compartilhadas por todos os coelhos
    private static final int BREEDING_AGE = 5;
    private static final int MAX_AGE = 50;
    private static final double BREEDING_PROBABILITY = 0.15;
    private static final int MAX_LITTER_SIZE = 5;

    /**
     * Cria um novo coelho. Pode ser criado recém-nascido ou com idade aleatória.
     * @param randomAge Se verdadeiro, terá idade aleatória
     */
    public Rabbit(boolean randomAge)
    {
        super(randomAge);
    }
    
    /**
     * Implementa o comportamento do coelho na simulação.
     * Corre, se reproduz, envelhece e pode morrer.
     */
    @Override
    public void act(Field currentField, Field updatedField, List<Animal> newAnimals)
    {
        incrementAge();
        if(isAlive()) {
            // Reprodução - novos filhotes nascem em localizações adjacentes
            int births = breed();
            for(int b = 0; b < births; b++) {
                Rabbit newRabbit = new Rabbit(false);
                newAnimals.add(newRabbit);
                Location loc = updatedField.randomAdjacentLocation(location);
                if(loc != null) {
                    newRabbit.setLocation(loc);
                    updatedField.place(newRabbit, loc);
                }
            }
            // Movimento - tenta escapar de predadores primeiro
            Location newLocation = null;
            if(detectsPredators(currentField, location)) {
                newLocation = escape(currentField, location);
            }
            if(newLocation == null) {
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
     * Cria um novo coelho (método da classe Animal).
     * @param randomAge Se deve ter idade aleatória
     * @return Novo coelho
     */
    @Override
    protected Animal createOffspring(boolean randomAge)
    {
        return new Rabbit(randomAge);
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
     * Implementa comportamento de escape da interface Prey.
     * @param field Campo onde está localizado
     * @param location Localização atual
     * @return Nova localização de escape, ou null se não conseguir
     */
    @Override
    public Location escape(Field field, Location location)
    {
        Iterator<Location> adjacentLocations = field.adjacentLocations(location);
        while(adjacentLocations.hasNext()) {
            Location where = adjacentLocations.next();
            Animal animal = field.getObjectAt(where);
            // Procura localização sem predadores
            if(animal == null || !(animal instanceof Predator)) {
                return where;
            }
        }
        return null;
    }
    
    /**
     * Implementa detecção de predadores da interface Prey.
     * @param field Campo para verificar
     * @param location Localização atual
     * @return true se há predadores por perto
     */
    @Override
    public boolean detectsPredators(Field field, Location location)
    {
        Iterator<Location> adjacentLocations = field.adjacentLocations(location);
        while(adjacentLocations.hasNext()) {
            Location where = adjacentLocations.next();
            Animal animal = field.getObjectAt(where);
            if(animal instanceof Predator && animal.isAlive()) {
                return true;
            }
        }
        return false;
    }
}
