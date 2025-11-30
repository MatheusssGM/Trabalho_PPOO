/**
 * Interface Observer para notificar mudanças na configuração da simulação.
 * Implementa o padrão Observer para desacoplar a interface do simulador.
 * 
 * @author Código melhorado com POO
 * @version 2025
 */
public interface SimulationConfigObserver {
    /**
     * Método chamado quando a configuração da simulação é atualizada
     * @param config Nova configuração da simulação
     */
    void onConfigurationChanged(SimulationConfig config);
}