/**
 * Interface que define comportamento de caça para predadores.
 * Demonstra o uso de interfaces para definir contratos específicos.
 * 
 * @author Código melhorado com POO
 * @version 2025
 */
public interface Predator
{
    /**
     * Caça por presas em uma localização específica.
     * @param field Campo onde caçar
     * @param location Localização do predador
     * @return Localização onde a presa foi encontrada, ou null
     */
    Location hunt(Campo field, Location location);
    
    /**
     * Obtém o nível de comida atual do predador.
     * @return Nível de comida
     */
    int getFoodLevel();
    
    /**
     * Define se o predador está com fome.
     * @return true se está com fome
     */
    boolean isHungry();
}