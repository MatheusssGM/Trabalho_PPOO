/**
 * Interface para criação de animais (Factory Pattern).
 * Reduz o acoplamento entre Simulator e classes específicas de animais.
 * 
 * @author Código melhorado com POO
 * @version 2025
 */
public interface AnimalFactory {
    
    /**
     * Cria um animal baseado no tipo e probabilidade
     * @param probability valor aleatório para determinar o tipo
     * @param config provedor de configurações
     * @return animal criado ou null se não deve criar nenhum
     */
    Animal createAnimal(double probability, ConfigurationProvider config);
    
    /**
     * Verifica se um animal pode ser colocado em um ambiente específico
     * @param animal animal a ser verificado
     * @param environment ambiente onde será colocado
     * @return true se o animal pode ser colocado
     */
    boolean canPlace(Animal animal, Environment environment);
}