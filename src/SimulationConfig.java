/**
 * Classe de configuração da simulação usando padrão Builder.
 * Permite definir parâmetros iniciais da simulação de forma flexível.
 * 
 * @author Código melhorado com POO
 * @version 2025
 */
public class SimulationConfig {
    private final int width;
    private final int height;
    private final double rabbitProbability;
    private final double foxProbability;
    private final double wolfProbability;
    private final double lionProbability;
    private final double humanProbability;
    
    private SimulationConfig(Builder builder) {
        this.width = builder.width;
        this.height = builder.height;
        this.rabbitProbability = builder.rabbitProbability;
        this.foxProbability = builder.foxProbability;
        this.wolfProbability = builder.wolfProbability;
        this.lionProbability = builder.lionProbability;
        this.humanProbability = builder.humanProbability;
    }
    
    // Getters
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public double getRabbitProbability() { return rabbitProbability; }
    public double getFoxProbability() { return foxProbability; }
    public double getWolfProbability() { return wolfProbability; }
    public double getLionProbability() { return lionProbability; }
    public double getHumanProbability() { return humanProbability; }
    
    /**
     * Builder para criar configurações de simulação
     */
    public static class Builder {
        private int width = 50;
        private int height = 50;
        private double rabbitProbability = 0.08;
        private double foxProbability = 0.02;
        private double wolfProbability = 0.005;
        private double lionProbability = 0.005;
        private double humanProbability = 0.01;
        
        public Builder setDimensions(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }
        
        public Builder setRabbitProbability(double probability) {
            this.rabbitProbability = probability;
            return this;
        }
        
        public Builder setFoxProbability(double probability) {
            this.foxProbability = probability;
            return this;
        }
        
        public Builder setWolfProbability(double probability) {
            this.wolfProbability = probability;
            return this;
        }
        
        public Builder setLionProbability(double probability) {
            this.lionProbability = probability;
            return this;
        }
        
        public Builder setHumanProbability(double probability) {
            this.humanProbability = probability;
            return this;
        }
        
        public SimulationConfig build() {
            return new SimulationConfig(this);
        }
    }
}