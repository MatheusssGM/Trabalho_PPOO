import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.HashMap;

/**
 * Visualizador gráfico da simulação
 */
public class VisualizadorSimulacao extends JFrame
{
    // Colors used for empty locations.
    private static final Color EMPTY_COLOR = Color.white;

    // Color used for objects that have no defined color.
    private static final Color UNKNOWN_COLOR = Color.gray;

    private final String STEP_PREFIX = "Step: ";
    private final String POPULATION_PREFIX = "Population: ";
    private JLabel stepLabel, population;
    private FieldView fieldView;

    // A map for storing colors for participants in the simulation
    private HashMap colors;
    // A statistics object computing and storing simulation information
    private EstatisticasCampo stats;

    public VisualizadorSimulacao(int height, int width)
    {
        stats = new EstatisticasCampo();
        colors = new HashMap();
        setTitle("Simulacao Predador-Presa");
        stepLabel = new JLabel(STEP_PREFIX, JLabel.CENTER);
        population = new JLabel(POPULATION_PREFIX, JLabel.CENTER);
        setLocation(100, 50);
        fieldView = new FieldView(height, width);
        Container contents = getContentPane();
        contents.add(stepLabel, BorderLayout.NORTH);
        contents.add(fieldView, BorderLayout.CENTER);
        contents.add(population, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

    /**
     * Define a color to be used for a given class of animal.
     */
    public void setColor(Class animalClass, Color color)
    {
        colors.put(animalClass, color);
    }

    /**
     * Define a color to be used for a given class of animal.
     */
    private Color getColor(Class animalClass)
    {
        Color col = (Color)colors.get(animalClass);
        if(col == null) {
            // no color defined for this class
            return UNKNOWN_COLOR;
        }
        else {
            return col;
        }
    }

    private Color getEnvironmentColor(Environment env) {
        if (env == null) return EMPTY_COLOR;
        String envName = env.getClass().getSimpleName();
        switch (envName) {
            case "Mountain": return Color.DARK_GRAY;
            case "Savanna": return new Color(240, 230, 140);
            case "Burrow": return new Color(139, 69, 19);
            case "Plains": return new Color(144, 238, 144);
            default: return EMPTY_COLOR;
        }
    }

    /**
     * Show the current status of the field.
     * @param step Which iteration step it is.
     * @param field Status of the field to be represented.
     */
    public void showStatus(int step, Campo field)
    {
        if(!isVisible())
            setVisible(true);

        stepLabel.setText(STEP_PREFIX + step);

        stats.reset();
        fieldView.preparePaint();

        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Object animal = field.getObjectAt(row, col);

                if(animal != null) {
                    stats.incrementCount(animal.getClass());
                    fieldView.drawMark(col, row, getColor(animal.getClass()));
                }
                else {
                    // SE NÃO TIVER ANIMAL, PINTA O AMBIENTE
                    Environment env = field.getEnvironment(row, col);
                    Color envColor = getEnvironmentColor(env);
                    fieldView.drawMark(col, row, envColor);
                }
            }
        }
        stats.countFinished();

        population.setText(POPULATION_PREFIX + stats.getPopulationDetails(field));
        fieldView.repaint();
    }

    /**
     * Determine whether the simulation should continue to run.
     * @return true If there is more than one species alive.
     */
    public boolean isViable(Campo field)
    {
        return stats.isViable(field);
    }

    // Classe interna para renderização do campo
    private class FieldView extends JPanel
    {
        private final int GRID_VIEW_SCALING_FACTOR = 6;

        private int gridWidth, gridHeight;
        private int xScale, yScale;
        Dimension size;
        private Graphics g;
        private Image fieldImage;


        public FieldView(int height, int width)
        {
            gridHeight = height;
            gridWidth = width;
            size = new Dimension(0, 0);
        }


        public Dimension getPreferredSize()
        {
            return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR,
                    gridHeight * GRID_VIEW_SCALING_FACTOR);
        }


        public void preparePaint()
        {
            if(! size.equals(getSize())) {  // if the size has changed...
                size = getSize();
                fieldImage = fieldView.createImage(size.width, size.height);
                g = fieldImage.getGraphics();

                xScale = size.width / gridWidth;
                if(xScale < 1) {
                    xScale = GRID_VIEW_SCALING_FACTOR;
                }
                yScale = size.height / gridHeight;
                if(yScale < 1) {
                    yScale = GRID_VIEW_SCALING_FACTOR;
                }
            }
        }


        public void drawMark(int x, int y, Color color)
        {
            g.setColor(color);
            g.fillRect(x * xScale, y * yScale, xScale-1, yScale-1);
        }


        public void paintComponent(Graphics g)
        {
            if(fieldImage != null) {
                g.drawImage(fieldImage, 0, 0, null);
            }
        }
    }
}