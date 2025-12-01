import javax.swing.*;

/**
 * Classe principal - inicia simulação com config.txt
 */
public class Principal {
    public static void main(String[] args) {
        // Criar simulador com configurações do arquivo
        ConfigurationProvider config = new ConfigurationManager();
        AnimalFactory factory = new ConcreteAnimalFactory();
        EnvironmentManager environment = new DefaultEnvironmentManager();
        
        Simulador simulator = new Simulador(config, factory, environment);
        
        // Iniciar interface de controle
        SwingUtilities.invokeLater(() -> {
            new ControleSimulacaoInterativa(simulator).setVisible(true);
        });
    }
}
