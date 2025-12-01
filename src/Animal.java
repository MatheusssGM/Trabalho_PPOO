import java.util.List;
import java.util.Random;

/**
 * Classe abstrata que representa um animal no simulador.
 * Fornece funcionalidades comuns para todos os animais como idade,
 * localização, reprodução e morte.
 * 
 * @author Código melhorado com POO
 * @version 2025
 */
public abstract class Animal implements Simulable
{
    // Gerador de números aleatórios compartilhado
    protected static final Random rand = new Random();
    
    // Características individuais do animal
    protected int age;
    protected boolean alive;
    protected Location location;
    
    /**
     * Cria um novo animal.
     * @param randomAge Se verdadeiro, o animal terá idade aleatória.
     */
    public Animal(boolean randomAge)
    {
        age = 0;
        alive = true;
        if(randomAge) {
            age = rand.nextInt(getMaxAge());
        }
    }
    
    /**
     * Simula um passo do animal na simulação.
     * @param currentField Campo atual
     * @param updatedField Campo atualizado
     * @param newAnimals Lista de novos animais nascidos
     */
    @Override
    public abstract void act(Campo currentField, Campo updatedField, List<Animal> newAnimals);
    
    /**
     * Incrementa a idade do animal. Pode resultar em morte.
     */
    protected void incrementAge()
    {
        age++;
        if(age > getMaxAge()) {
            alive = false;
        }
    }
    
    /**
     * Gera filhotes se o animal puder se reproduzir.
     * @return Número de filhotes nascidos
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBreedingProbability()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }
    
    /**
     * Verifica se o animal pode se reproduzir.
     * @return true se pode se reproduzir
     */
    protected boolean canBreed()
    {
        return age >= getBreedingAge();
    }
    
    /**
     * Cria uma nova instância do mesmo tipo de animal.
     * @param randomAge Se o novo animal deve ter idade aleatória
     * @return Nova instância do animal
     */
    protected abstract Animal createOffspring(boolean randomAge);
    
    // Métodos abstratos que devem ser implementados pelas subclasses
    protected abstract int getMaxAge();
    protected abstract int getBreedingAge();
    protected abstract double getBreedingProbability();
    protected abstract int getMaxLitterSize();
    
    /**
     * Verifica se o animal está vivo.
     * @return true se está vivo
     */
    public boolean isAlive()
    {
        return alive;
    }
    
    /**
     * Define o animal como morto.
     */
    public void setDead()
    {
        alive = false;
    }
    
    /**
     * Define a localização do animal.
     * @param newLocation Nova localização
     */
    public void setLocation(Location newLocation)
    {
        this.location = newLocation;
    }
    
    /**
     * Define a localização do animal usando coordenadas.
     * @param row Linha
     * @param col Coluna
     */
    public void setLocation(int row, int col)
    {
        this.location = new Location(row, col);
    }
    
    /**
     * Obtém a localização atual do animal.
     * @return Localização atual
     */
    public Location getLocation()
    {
        return location;
    }
    
    /**
     * Obtém a idade do animal.
     * @return Idade atual
     */
    public int getAge()
    {
        return age;
    }
}