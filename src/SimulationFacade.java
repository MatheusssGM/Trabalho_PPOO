import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Fachada (Facade) para a aplicação de simulação.
 * Coordena a interface gráfica de configuração com o simulador.
 * Implementa padrão Facade para simplificar a interação entre componentes.
 * 
 * @author Código melhorado com POO
 * @version 2025
 */
public class SimulationFacade {
    private Simulator simulator;
    private SimulationConfigGUI configGUI;
    private JFrame mainFrame;
    
    public SimulationFacade() {
        initializeComponents();
        setupMainInterface();
    }
    
    /**
     * Inicializa os componentes principais da aplicação
     */
    private void initializeComponents() {
        // Cria o simulador com configuração padrão
        simulator = new Simulator();
        
        // Cria a interface de configuração
        configGUI = new SimulationConfigGUI();
        
        // Conecta o observador
        configGUI.addObserver(simulator);
    }
    
    /**
     * Configura a interface principal com menu de controle
     */
    private void setupMainInterface() {
        mainFrame = new JFrame("Simulacao Predador-Presa - Controle Principal");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
        
        // Painel de controles
        JPanel controlPanel = new JPanel(new FlowLayout());
        
        JButton configButton = new JButton("Configurar Simulacao");
        JButton startButton = new JButton("Iniciar Simulacao");
        JButton stepButton = new JButton("Executar Passo");
        JButton resetButton = new JButton("Reiniciar");
        JButton longSimButton = new JButton("Simulacao Longa (500 passos)");
        
        // Event listeners
        configButton.addActionListener(e -> showConfigurationDialog());
        startButton.addActionListener(e -> startSimulation());
        stepButton.addActionListener(e -> executeStep());
        resetButton.addActionListener(e -> resetSimulation());
        longSimButton.addActionListener(e -> runLongSimulation());
        
        controlPanel.add(configButton);
        controlPanel.add(new JSeparator(SwingConstants.VERTICAL));
        controlPanel.add(startButton);
        controlPanel.add(stepButton);
        controlPanel.add(resetButton);
        controlPanel.add(new JSeparator(SwingConstants.VERTICAL));
        controlPanel.add(longSimButton);
        
        // Painel de informações
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        JLabel titleLabel = new JLabel("Simulacao Predador-Presa com Interface Configuravel", JLabel.CENTER);
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        
        JLabel instructionLabel = new JLabel(
            "<html><center>Use 'Configurar Simulacao' para definir parametros iniciais<br>" +
            "Clique em 'Iniciar Simulacao' para comecar ou use os controles de passo</center></html>", 
            JLabel.CENTER);
        
        infoPanel.add(titleLabel);
        infoPanel.add(instructionLabel);
        
        mainFrame.add(infoPanel, BorderLayout.NORTH);
        mainFrame.add(controlPanel, BorderLayout.CENTER);
        
        // Status atual
        JLabel statusLabel = new JLabel("Status: Simulacao carregada com configuracao padrao", JLabel.CENTER);
        mainFrame.add(statusLabel, BorderLayout.SOUTH);
        
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
    }
    
    /**
     * Exibe o diálogo de configuração
     */
    private void showConfigurationDialog() {
        configGUI.setVisible(true);
    }
    
    /**
     * Inicia uma simulação interativa
     */
    private void startSimulation() {
        new Thread(() -> {
            simulator.simulate(50);
        }).start();
        
        JOptionPane.showMessageDialog(mainFrame, 
                "Simulacao iniciada! A janela grafica mostrara a evolucao dos animais.", 
                "Simulacao Iniciada", 
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Executa um único passo da simulação
     */
    private void executeStep() {
        simulator.simulateOneStep();
    }
    
    /**
     * Reinicia a simulação
     */
    private void resetSimulation() {
        simulator.reset();
        JOptionPane.showMessageDialog(mainFrame, 
                "Simulacao reiniciada com a configuracao atual.", 
                "Simulacao Reiniciada", 
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Executa simulação longa
     */
    private void runLongSimulation() {
        new Thread(() -> {
            simulator.runLongSimulation();
        }).start();
        
        JOptionPane.showMessageDialog(mainFrame, 
                "Simulacao longa iniciada (500 passos)! Observe a janela grafica.", 
                "Simulacao Longa Iniciada", 
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Exibe a interface principal
     */
    public void show() {
        SwingUtilities.invokeLater(() -> {
            mainFrame.setVisible(true);
        });
    }
    
    /**
     * Obtém o simulador (para extensibilidade)
     * @return Simulador atual
     */
    public Simulator getSimulator() {
        return simulator;
    }
}