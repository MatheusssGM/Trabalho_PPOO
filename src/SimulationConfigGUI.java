import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Interface gráfica para configurar os parâmetros iniciais da simulação.
 * Utiliza padrão Observer para notificar mudanças e padrão Builder para configuração.
 * 
 * @author Código melhorado com POO
 * @version 2025
 */
public class SimulationConfigGUI extends JFrame {
    private final List<SimulationConfigObserver> observers = new ArrayList<>();
    
    // Componentes da interface
    private JSpinner widthSpinner;
    private JSpinner heightSpinner;
    private JSlider rabbitSlider;
    private JSlider foxSlider;
    private JSlider wolfSlider;
    private JSlider lionSlider;
    private JSlider humanSlider;
    
    // Labels para mostrar valores atuais
    private JLabel rabbitValueLabel;
    private JLabel foxValueLabel;
    private JLabel wolfValueLabel;
    private JLabel lionValueLabel;
    private JLabel humanValueLabel;
    
    public SimulationConfigGUI() {
        initializeComponents();
        setupLayout();
        setupEventListeners();
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }
    
    /**
     * Adiciona um observer para receber notificações de mudança de configuração
     */
    public void addObserver(SimulationConfigObserver observer) {
        observers.add(observer);
    }
    
    /**
     * Remove um observer
     */
    public void removeObserver(SimulationConfigObserver observer) {
        observers.remove(observer);
    }
    
    /**
     * Notifica todos os observers sobre mudança na configuração
     */
    private void notifyObservers(SimulationConfig config) {
        for (SimulationConfigObserver observer : observers) {
            observer.onConfigurationChanged(config);
        }
    }
    
    private void initializeComponents() {
        setTitle("Configuracao da Simulacao");        
        // Spinners para dimensões
        widthSpinner = new JSpinner(new SpinnerNumberModel(50, 20, 200, 5));
        heightSpinner = new JSpinner(new SpinnerNumberModel(50, 20, 200, 5));
        
        // Sliders para probabilidades (0-20%)
        rabbitSlider = createProbabilitySlider(8); // 8%
        foxSlider = createProbabilitySlider(2);    // 2%
        wolfSlider = createProbabilitySlider(1);   // 0.5%
        lionSlider = createProbabilitySlider(1);   // 0.5%
        humanSlider = createProbabilitySlider(1);  // 1%
        
        // Labels para valores
        rabbitValueLabel = new JLabel("8.0%");
        foxValueLabel = new JLabel("2.0%");
        wolfValueLabel = new JLabel("0.5%");
        lionValueLabel = new JLabel("0.5%");
        humanValueLabel = new JLabel("1.0%");
    }
    
    private JSlider createProbabilitySlider(int initialValue) {
        JSlider slider = new JSlider(0, 200, initialValue * 10); // Multiplica por 10 para precisão
        slider.setMajorTickSpacing(50);
        slider.setMinorTickSpacing(10);
        slider.setPaintTicks(true);
        return slider;
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Titulo
        JLabel titleLabel = new JLabel("Configuracao Inicial da Simulacao");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Dimensões
        addSeparator(mainPanel, gbc, 1, "Dimensoes do Campo");
        addSpinnerRow(mainPanel, gbc, 2, "Largura:", widthSpinner);
        addSpinnerRow(mainPanel, gbc, 3, "Altura:", heightSpinner);
        
        // Probabilidades
        addSeparator(mainPanel, gbc, 4, "Probabilidades Iniciais dos Animais");
        addSliderRow(mainPanel, gbc, 5, "Coelhos:", rabbitSlider, rabbitValueLabel);
        addSliderRow(mainPanel, gbc, 6, "Raposas:", foxSlider, foxValueLabel);
        addSliderRow(mainPanel, gbc, 7, "Lobos:", wolfSlider, wolfValueLabel);
        addSliderRow(mainPanel, gbc, 8, "Leoes:", lionSlider, lionValueLabel);
        addSliderRow(mainPanel, gbc, 9, "Humanos:", humanSlider, humanValueLabel);
        
        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton applyButton = new JButton("Aplicar Configuracao");
        JButton resetButton = new JButton("Valores Padrao");
        JButton cancelButton = new JButton("Cancelar");
        
        applyButton.addActionListener(e -> applyConfiguration());
        resetButton.addActionListener(e -> resetToDefaults());
        cancelButton.addActionListener(e -> setVisible(false));
        
        buttonPanel.add(applyButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(cancelButton);
        
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void addSeparator(JPanel panel, GridBagConstraints gbc, int row, String text) {
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel label = new JLabel(text);
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        panel.add(label, gbc);
        
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
    }
    
    private void addSpinnerRow(JPanel panel, GridBagConstraints gbc, int row, String label, JSpinner spinner) {
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel(label), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(spinner, gbc);
        
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
    }
    
    private void addSliderRow(JPanel panel, GridBagConstraints gbc, int row, String label, JSlider slider, JLabel valueLabel) {
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel(label), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(slider, gbc);
        
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(valueLabel, gbc);
    }
    
    private void setupEventListeners() {
        rabbitSlider.addChangeListener(e -> rabbitValueLabel.setText(String.format("%.1f%%", rabbitSlider.getValue() / 10.0)));
        foxSlider.addChangeListener(e -> foxValueLabel.setText(String.format("%.1f%%", foxSlider.getValue() / 10.0)));
        wolfSlider.addChangeListener(e -> wolfValueLabel.setText(String.format("%.1f%%", wolfSlider.getValue() / 10.0)));
        lionSlider.addChangeListener(e -> lionValueLabel.setText(String.format("%.1f%%", lionSlider.getValue() / 10.0)));
        humanSlider.addChangeListener(e -> humanValueLabel.setText(String.format("%.1f%%", humanSlider.getValue() / 10.0)));
    }
    
    private void applyConfiguration() {
        SimulationConfig config = new SimulationConfig.Builder()
                .setDimensions((Integer) widthSpinner.getValue(), (Integer) heightSpinner.getValue())
                .setRabbitProbability(rabbitSlider.getValue() / 1000.0) // Converte para probabilidade (0-1)
                .setFoxProbability(foxSlider.getValue() / 1000.0)
                .setWolfProbability(wolfSlider.getValue() / 1000.0)
                .setLionProbability(lionSlider.getValue() / 1000.0)
                .setHumanProbability(humanSlider.getValue() / 1000.0)
                .build();
        
        notifyObservers(config);
        setVisible(false);
        
        JOptionPane.showMessageDialog(this, 
                "Configuracao aplicada com sucesso!\nA nova simulacao sera iniciada com os parametros definidos.",
                "Configuracao Aplicada", 
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void resetToDefaults() {
        widthSpinner.setValue(50);
        heightSpinner.setValue(50);
        rabbitSlider.setValue(80);  // 8.0%
        foxSlider.setValue(20);     // 2.0%
        wolfSlider.setValue(5);     // 0.5%
        lionSlider.setValue(5);     // 0.5%
        humanSlider.setValue(10);   // 1.0%
    }
}