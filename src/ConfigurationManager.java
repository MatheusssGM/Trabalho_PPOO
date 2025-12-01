import java.io.*;
import java.util.Properties;

/**
 * Gerenciador de configurações da simulação através de arquivo texto.
 * Implementa ConfigurationProvider para baixo acoplamento.
 * 
 * @author Código melhorado com POO
 * @version 2025
 */
public class ConfigurationManager implements ConfigurationProvider {
    private static final String CONFIG_FILE = "config.txt";
    private Properties config;
    
    // Valores padrão
    private static final String DEFAULT_WIDTH = "50";
    private static final String DEFAULT_HEIGHT = "50";
    private static final String DEFAULT_RABBIT_PROB = "0.08";
    private static final String DEFAULT_FOX_PROB = "0.02";
    private static final String DEFAULT_LION_PROB = "0.005";
    private static final String DEFAULT_HUMAN_PROB = "0.01";
    private static final String DEFAULT_SIMULATION_STEPS = "500";
    
    public ConfigurationManager() {
        config = new Properties();
        loadConfiguration();
    }
    
    /**
     * Carrega configurações do arquivo texto
     */
    public void loadConfiguration() {
        File configFile = new File(CONFIG_FILE);
        
        if (configFile.exists()) {
            try (FileInputStream fis = new FileInputStream(configFile)) {
                config.load(fis);
                System.out.println("Configuracoes carregadas de: " + CONFIG_FILE);
            } catch (IOException e) {
                System.out.println("Erro ao carregar configuracoes: " + e.getMessage());
                setDefaultValues();
            }
        } else {
            System.out.println("Arquivo de configuracao nao encontrado. Criando com valores padrao...");
            setDefaultValues();
            saveConfiguration();
        }
    }
    
    /**
     * Define valores padrão
     */
    private void setDefaultValues() {
        config.setProperty("field.width", DEFAULT_WIDTH);
        config.setProperty("field.height", DEFAULT_HEIGHT);
        config.setProperty("animal.rabbit.probability", DEFAULT_RABBIT_PROB);
        config.setProperty("animal.fox.probability", DEFAULT_FOX_PROB);
        config.setProperty("animal.lion.probability", DEFAULT_LION_PROB);
        config.setProperty("animal.human.probability", DEFAULT_HUMAN_PROB);
        config.setProperty("simulation.steps", DEFAULT_SIMULATION_STEPS);
    }
    
    /**
     * Salva configurações no arquivo texto
     */
    public void saveConfiguration() {
        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
            config.store(fos, "Configuracoes da Simulacao Predador-Presa");
            System.out.println("Configuracoes salvas em: " + CONFIG_FILE);
        } catch (IOException e) {
            System.out.println("Erro ao salvar configuracoes: " + e.getMessage());
        }
    }
    
    /**
     * Cria arquivo de exemplo com comentários
     */
    public void createExampleConfigFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CONFIG_FILE))) {
            writer.println("# Configuracoes da Simulacao Predador-Presa");
            writer.println("# Modifique os valores abaixo e reinicie o programa");
            writer.println("");
            writer.println("# Dimensoes do campo");
            writer.println("field.width=" + getFieldWidth());
            writer.println("field.height=" + getFieldHeight());
            writer.println("");
            writer.println("# Probabilidades iniciais dos animais (0.0 a 1.0)");
            writer.println("animal.rabbit.probability=" + getRabbitProbability());
            writer.println("animal.fox.probability=" + getFoxProbability());
            writer.println("animal.lion.probability=" + getLionProbability());
            writer.println("animal.human.probability=" + getHumanProbability());
            writer.println("");
            writer.println("# Numero de passos da simulacao");
            writer.println("simulation.steps=" + getSimulationSteps());
            writer.println("");
            writer.println("# Dicas:");
            writer.println("# - Coelhos devem ter maior probabilidade (presas)");
            writer.println("# - Humanos devem ter menor probabilidade (predador supremo)");
            writer.println("# - Mantenha o equilibrio para evitar extincoes rapidas");
            
            System.out.println("Arquivo de configuracao criado: " + CONFIG_FILE);
        } catch (IOException e) {
            System.out.println("Erro ao criar arquivo de configuracao: " + e.getMessage());
        }
    }
    
    // Getters para as configurações
    public int getFieldWidth() {
        return Integer.parseInt(config.getProperty("field.width", DEFAULT_WIDTH));
    }
    
    public int getFieldHeight() {
        return Integer.parseInt(config.getProperty("field.height", DEFAULT_HEIGHT));
    }
    
    public double getRabbitProbability() {
        return Double.parseDouble(config.getProperty("animal.rabbit.probability", DEFAULT_RABBIT_PROB));
    }
    
    public double getFoxProbability() {
        return Double.parseDouble(config.getProperty("animal.fox.probability", DEFAULT_FOX_PROB));
    }
    
    public double getLionProbability() {
        return Double.parseDouble(config.getProperty("animal.lion.probability", DEFAULT_LION_PROB));
    }
    
    public double getHumanProbability() {
        return Double.parseDouble(config.getProperty("animal.human.probability", DEFAULT_HUMAN_PROB));
    }
    
    public int getSimulationSteps() {
        return Integer.parseInt(config.getProperty("simulation.steps", DEFAULT_SIMULATION_STEPS));
    }
    
    // Setters para atualizar configurações
    public void setFieldWidth(int width) {
        config.setProperty("field.width", String.valueOf(width));
    }
    
    public void setFieldHeight(int height) {
        config.setProperty("field.height", String.valueOf(height));
    }
    
    public void setRabbitProbability(double prob) {
        config.setProperty("animal.rabbit.probability", String.valueOf(prob));
    }
    
    public void setFoxProbability(double prob) {
        config.setProperty("animal.fox.probability", String.valueOf(prob));
    }
    
    public void setLionProbability(double prob) {
        config.setProperty("animal.lion.probability", String.valueOf(prob));
    }
    
    public void setHumanProbability(double prob) {
        config.setProperty("animal.human.probability", String.valueOf(prob));
    }
    
    public void setSimulationSteps(int steps) {
        config.setProperty("simulation.steps", String.valueOf(steps));
    }
    
    /**
     * Exibe configurações atuais
     */
    public void displayConfiguration() {
        System.out.println("=== Configuracoes Atuais ===");
        System.out.println("Campo: " + getFieldWidth() + "x" + getFieldHeight());
        System.out.println("Coelhos: " + (getRabbitProbability() * 100) + "%");
        System.out.println("Raposas: " + (getFoxProbability() * 100) + "%");
        System.out.println("Leoes: " + (getLionProbability() * 100) + "%");
        System.out.println("Humanos: " + (getHumanProbability() * 100) + "%");
        System.out.println("Passos: " + getSimulationSteps());
        System.out.println("============================");
    }
}