/**
 * Interface para gerenciamento de configurações da simulação.
 * Segue o princípio da inversão de dependência (DIP).
 * 
 * @author Código melhorado com POO
 * @version 2025
 */
public interface ConfigurationProvider {
    
    /**
     * Obtém a largura do campo
     * @return largura do campo
     */
    int getFieldWidth();
    
    /**
     * Obtém a altura do campo
     * @return altura do campo
     */
    int getFieldHeight();
    
    /**
     * Obtém a probabilidade inicial de coelhos
     * @return probabilidade de coelhos (0.0 a 1.0)
     */
    double getRabbitProbability();
    
    /**
     * Obtém a probabilidade inicial de raposas
     * @return probabilidade de raposas (0.0 a 1.0)
     */
    double getFoxProbability();
    
    /**
     * Obtém a probabilidade inicial de leões
     * @return probabilidade de leões (0.0 a 1.0)
     */
    double getLionProbability();
    
    /**
     * Obtém a probabilidade inicial de humanos
     * @return probabilidade de humanos (0.0 a 1.0)
     */
    double getHumanProbability();
    
    /**
     * Obtém o número de passos da simulação
     * @return número de passos
     */
    int getSimulationSteps();
    
    /**
     * Exibe as configurações atuais
     */
    void displayConfiguration();
}