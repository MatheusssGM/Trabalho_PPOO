import java.util.List;

/**
 * Interface que define o comportamento simulável para elementos
 * que participam da simulação.
 * 
 * @author Código melhorado com POO
 * @version 2025
 */
public interface Simulable
{
    /**
     * Executa uma ação na simulação.
     * @param currentField Campo atual da simulação
     * @param updatedField Campo que será atualizado
     * @param newAnimals Lista para adicionar novos animais nascidos
     */
    void act(Campo currentField, Campo updatedField, List<Animal> newAnimals);
    
    /**
     * Verifica se o elemento ainda está ativo na simulação.
     * @return true se ainda está ativo
     */
    boolean isAlive();
}