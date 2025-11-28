/**
 * Interface que define comportamento de presa para herbívoros.
 * Demonstra o uso de interfaces para categorizar comportamentos.
 * 
 * @author Código melhorado com POO
 * @version 2025
 */
public interface Prey
{
    /**
     * Tenta escapar de predadores.
     * @param field Campo onde está localizado
     * @param location Localização atual
     * @return Nova localização de escape, ou null se não conseguir escapar
     */
    Location escape(Field field, Location location);
    
    /**
     * Verifica se há predadores nas proximidades.
     * @param field Campo para verificar
     * @param location Localização atual
     * @return true se há predadores por perto
     */
    boolean detectsPredators(Field field, Location location);
}