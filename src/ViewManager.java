import java.awt.Color;

/**
 * Interface para gerenciamento da visualização da simulação.
 * Desacopla o Simulator da implementação específica de visualização.
 * 
 * @author Código melhorado com POO
 * @version 2025
 */
public interface ViewManager {
    
    /**
     * Configura as cores para diferentes tipos de animais
     */
    void setupColors();
    
    /**
     * Exibe o status atual da simulação
     * @param step passo atual
     * @param field campo atual
     */
    void showStatus(int step, Campo field);
    
    /**
     * Verifica se a simulação ainda é viável
     * @param field campo atual
     * @return true se a simulação pode continuar
     */
    boolean isViable(Campo field);
    
    /**
     * Define a cor para um tipo específico de animal
     * @param animalClass classe do animal
     * @param color cor a ser usada
     */
    void setAnimalColor(Class<?> animalClass, Color color);
}