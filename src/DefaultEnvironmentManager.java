import java.util.Random;

/**
 * Implementação concreta do gerenciador de ambientes.
 * Define a estratégia de distribuição de ambientes no campo.
 * 
 * @author Código melhorado com POO
 * @version 2025
 */
public class DefaultEnvironmentManager implements EnvironmentManager {
    
    private static final double BURROW_PROBABILITY = 0.01;
    private static final int MOUNTAIN_ZONE_HEIGHT = 5;
    private static final int SAVANNA_START_ROW = 20;
    private static final int SAVANNA_END_ROW = 35;
    
    private final Random random;
    
    public DefaultEnvironmentManager() {
        this.random = new Random();
    }
    
    @Override
    public void populateEnvironments(Campo field) {
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Environment env = getEnvironmentForPosition(row, col, field.getDepth(), field.getWidth());
                field.setEnvironmentAt(row, col, env);
            }
        }
    }
    
    @Override
    public Environment getEnvironmentForPosition(int row, int col, int fieldDepth, int fieldWidth) {
        // Montanhas no topo
        if (row < MOUNTAIN_ZONE_HEIGHT) {
            return new Mountain();
        }
        // Savana no meio
        else if (row >= SAVANNA_START_ROW && row <= SAVANNA_END_ROW) {
            return new Savanna();
        }
        // Planícies com tocas aleatórias no restante
        else {
            if (random.nextDouble() < BURROW_PROBABILITY) {
                return new Burrow();
            } else {
                return new Plains();
            }
        }
    }
}