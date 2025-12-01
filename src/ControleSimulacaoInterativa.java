import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Interface gráfica para controlar a simulação interativa baseada em config.txt
 * com botões para pausar, continuar e parar.
 * 
 * @author Código melhorado com POO
 * @version 2025
 */
public class ControleSimulacaoInterativa extends JFrame {
    private Simulador simulator;
    private JButton stopButton;
    private JButton pauseButton;
    private JButton runCompleteButton;
    private JLabel statusLabel;
    private JLabel stepLabel;
    private JLabel animalsLabel;
    
    private Timer fastTimer; // Timer para execução completa
    private boolean isPaused = false;
    private int currentStep = 0;
    private int maxSteps;
    
    public ControleSimulacaoInterativa(Simulador simulator) {
        this.simulator = simulator;
        this.maxSteps = simulator.getConfigProvider().getSimulationSteps();
        
        setupUI();
    }
    
    private void setupUI() {
        setTitle("Simulacao Predador-Presa - Controles");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Painel de informações
        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Status Atual"));
        
        stepLabel = new JLabel("Passo: 0 de " + maxSteps);
        animalsLabel = new JLabel("Animais Vivos: " + simulator.getAnimals().size());
        statusLabel = new JLabel("Estado: Pronto");
        
        stepLabel.setFont(new Font("Arial", Font.BOLD, 14));
        animalsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        infoPanel.add(stepLabel);
        infoPanel.add(animalsLabel);
        infoPanel.add(statusLabel);
        
        // Painel de controles
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.setBorder(BorderFactory.createTitledBorder("Controles"));
        
        pauseButton = new JButton("Pausar");
        runCompleteButton = new JButton("Executar Completo");
        stopButton = new JButton("Parar");
        
        pauseButton.addActionListener(e -> togglePause());
        runCompleteButton.addActionListener(e -> runComplete());
        stopButton.addActionListener(e -> stopSimulation());
        
        // Inicialmente pauseButton está desabilitado
        pauseButton.setEnabled(false);
        
        controlPanel.add(pauseButton);
        controlPanel.add(runCompleteButton);
        controlPanel.add(stopButton);
        
        // Painel de configurações
        JPanel configPanel = new JPanel(new GridLayout(2, 1));
        configPanel.setBorder(BorderFactory.createTitledBorder("Informacoes"));
        
        JLabel configInfo = new JLabel("Configuracoes: config.txt");
        JLabel speedInfo = new JLabel("Controle: Manual com botoes");
        
        configPanel.add(configInfo);
        configPanel.add(speedInfo);
        
        add(infoPanel, BorderLayout.NORTH);
        add(controlPanel, BorderLayout.CENTER);
        add(configPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Garantir que todos os botões estejam visíveis inicialmente
        stopButton.setEnabled(true);
        stopButton.setVisible(true);
        
        updateStatus();
    }
    

    

    

    
    private void stopSimulation() {
        // Parar timer de execução completa se estiver rodando
        if (fastTimer != null && fastTimer.isRunning()) {
            fastTimer.stop();
        }
        
        pauseButton.setEnabled(false);
        runCompleteButton.setEnabled(false);
        stopButton.setVisible(true); // Garantir que botão esteja visível
        stopButton.removeActionListener(stopButton.getActionListeners()[0]);
        stopButton.addActionListener(e -> System.exit(0));
        statusLabel.setText("Estado: Simulação finalizada");
        
        System.out.println("\n=== SIMULACAO FINALIZADA ===");
        System.out.println("Passos executados: " + currentStep);
        System.out.println("Animais restantes: " + simulator.getAnimals().size());
    }
    
    private void togglePause() {
        if (fastTimer != null && fastTimer.isRunning()) {
            isPaused = !isPaused;
            
            if (isPaused) {
                pauseButton.setText("Continuar");
                statusLabel.setText("Estado: Execução pausada");
                stopButton.setVisible(false); // Esconder botão parar
            } else {
                pauseButton.setText("Pausar");
                statusLabel.setText("Estado: Executando simulação completa...");
                stopButton.setVisible(true); // Mostrar botão parar
            }
        }
    }
    
    private void runComplete() {
        // Habilitar pausar e manter parar durante execução completa
        pauseButton.setEnabled(true);
        pauseButton.setText("Pausar");
        runCompleteButton.setEnabled(false);
        stopButton.setEnabled(true);
        stopButton.setVisible(true);
        isPaused = false;
        statusLabel.setText("Estado: Executando simulação completa...");
        
        // Timer para execução rápida mas visível
        fastTimer = new Timer(50, null); // 50ms entre passos (20 passos por segundo)
        
        fastTimer.addActionListener(e -> {
            if (!isPaused && currentStep < maxSteps && simulator.getAnimals().size() > 0) {
                simulator.simulateOneStep();
                currentStep++;
                updateStatus(); // Atualizar a cada passo para ver progresso
                
                // Verificar se chegou ao fim
                if (currentStep >= maxSteps || simulator.getAnimals().size() == 0) {
                    fastTimer.stop();
                    stopSimulation();
                    
                    JOptionPane.showMessageDialog(this, 
                        "Simulação completa executada!\nPassos executados: " + currentStep + 
                        "\nAnimais restantes: " + simulator.getAnimals().size(),
                        "Execução Completa Finalizada", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
            // Se pausado, timer continua rodando mas não executa passos
        });
        
        fastTimer.start();
    }
    
    private void updateStatus() {
        stepLabel.setText("Passo: " + currentStep + " de " + maxSteps);
        animalsLabel.setText("Animais Vivos: " + simulator.getAnimals().size());
        
        if (currentStep >= maxSteps) {
            statusLabel.setText("Estado: Simulação finalizada");
        } else {
            statusLabel.setText("Estado: Pronto");
        }
    }
}