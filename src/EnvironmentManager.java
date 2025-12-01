/**
 * Interface para gerenciamento de ambientes do campo.
 * Separa a responsabilidade de criação de ambientes do Simulator.
 * 
 * @author Código melhorado com POO
 * @version 2025
 */
public interface EnvironmentManager {
    
    /**
     * Popula um campo com ambientes apropriados
     * @param field campo a ser populado
     */
    void populateEnvironments(Campo field);
    
    /**
     * Obtém o tipo de ambiente recomendado para uma posição
     * @param row linha da posição
     * @param col coluna da posição
     * @param fieldDepth profundidade total do campo
     * @param fieldWidth largura total do campo
     * @return ambiente apropriado para a posição
     */
    Environment getEnvironmentForPosition(int row, int col, int fieldDepth, int fieldWidth);
}