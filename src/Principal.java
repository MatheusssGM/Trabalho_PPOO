import javax.swing.*;

/**
 * Classe principal da aplicação de simulação.
 * Utiliza padrão Facade para coordenar os componentes.
 * 
 * @author Código melhorado com POO
 * @version 2025
 */
public class Principal {
    public static void main(String[] args) {
        // Cria e exibe a aplicacao usando padrao Facade
        SwingUtilities.invokeLater(() -> {
            SimulationFacade app = new SimulationFacade();
            app.show();
        });
        
        // Alternativa: executar simulacao simples sem interface (descomente a linha abaixo)
        // new Simulator().runLongSimulation();
    }
}
