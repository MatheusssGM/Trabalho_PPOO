import java.awt.Color;

/**
 * Implementação concreta do gerenciador de visualização.
 * Encapsula a lógica de visualização usando SimulatorView.
 * 
 * @author Código melhorado com POO
 * @version 2025
 */
public class DefaultViewManager implements ViewManager {
    
    private final VisualizadorSimulacao view;
    
    public DefaultViewManager(int depth, int width) {
        this.view = new VisualizadorSimulacao(depth, width);
        setupColors();
    }
    
    @Override
    public void setupColors() {
        setAnimalColor(Fox.class, Color.blue);
        setAnimalColor(Rabbit.class, Color.orange);
        setAnimalColor(Lion.class, Color.red);
        setAnimalColor(Human.class, Color.black);
    }
    
    @Override
    public void showStatus(int step, Campo field) {
        view.showStatus(step, field);
    }

    @Override
    public boolean isViable(Campo field) {
        return view.isViable(field);
    }
    
    @Override
    public void setAnimalColor(Class<?> animalClass, Color color) {
        view.setColor(animalClass, color);
    }
}