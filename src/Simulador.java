import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;


/**
 * Classe principal do simulador predador-presa com ambientes naturais.
 * Esta versão inclui Humanos, Leões, Raposas e Coelhos.
 * Os ambientes incluem Montanhas, Savanas, Tocas e Planícies.
 *
 * A simulação ocorre em passos discretos, onde cada animal age, se move,
 * caça, reproduz e pode morrer. O campo é atualizado a cada passo.
 *
 * @author Versão modificada
 * @version 2025
 */
public class Simulador
{
    // Interfaces para baixo acoplamento (Dependency Injection)
    private final ConfigurationProvider configProvider;
    private final AnimalFactory animalFactory;
    private final EnvironmentManager environmentManager;
    private final ViewManager viewManager;
    
    // Configurações de tamanho do campo (valores padrão)
    private static final int DEFAULT_WIDTH = 50;
    private static final int DEFAULT_DEPTH = 50;

    // Estado da simulação
    private List<Animal> animals;
    private List<Animal> newAnimals;
    private Campo field;
    private Campo updatedField;
    private int step;

    /**
     * Construtor padrão usando implementações concretas (para compatibilidade)
     */
    public Simulador()
    {
        this(new ConfigurationManager(), 
             new ConcreteAnimalFactory(), 
             new DefaultEnvironmentManager());
    }

    /**
     * Construtor com injeção de dependência (baixo acoplamento)
     * @param configProvider provedor de configurações
     * @param animalFactory fábrica de animais  
     * @param environmentManager gerenciador de ambientes
     */
    public Simulador(ConfigurationProvider configProvider, 
                     AnimalFactory animalFactory,
                     EnvironmentManager environmentManager)
    {
        this.configProvider = configProvider;
        this.animalFactory = animalFactory;
        this.environmentManager = environmentManager;
        
        configProvider.displayConfiguration();
        
        int depth = configProvider.getFieldHeight();
        int width = configProvider.getFieldWidth();
        
        if(width <= 0 || depth <= 0) {
            System.out.println("As dimensões devem ser maiores que zero. Usando valores padrão.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        
        this.viewManager = new DefaultViewManager(depth, width);
        initializeSimulator(depth, width);
    }

    /**
     * Construtor com dimensões específicas (para compatibilidade)
     */
    public Simulador(int depth, int width)
    {
        this(new ConfigurationManager(), 
             new ConcreteAnimalFactory(), 
             new DefaultEnvironmentManager());
    }
    
    /**
     * Inicializa os componentes do simulador usando baixo acoplamento
     */
    private void initializeSimulator(int depth, int width)
    {
        animals = new ArrayList<>();
        newAnimals = new ArrayList<>();
        field = new Campo(depth, width);
        updatedField = new Campo(depth, width);

        // Usa EnvironmentManager para popular ambientes
        environmentManager.populateEnvironments(field);
        environmentManager.populateEnvironments(updatedField);

        reset();
    }







    /**
     * Executa um único passo da simulação.
     * Atualiza todos os animais e troca os campos.
     */
    public void simulateOneStep()
    {
        step++;
        newAnimals.clear();

        for(Iterator<Animal> iter = animals.iterator(); iter.hasNext();) {
            Animal animal = iter.next();
            if(animal.isAlive()) {
                animal.act(field, updatedField, newAnimals);
            } else {
                iter.remove();
            }
        }

        animals.addAll(newAnimals);

        Campo temp = field;
        field = updatedField;
        updatedField = temp;

        updatedField.clear(); // animais somente!

        viewManager.showStatus(step, field);
    }

    /**
     * Reinicia completamente a simulação.
     * limpa o campo e repopula com animais.
     */
    public void reset()
    {
        step = 0;
        animals.clear();
        field.clear();
        updatedField.clear();

        populate(field);

        viewManager.showStatus(step, field);
    }

    /**
     * Insere os animais iniciais no campo usando AnimalFactory (baixo acoplamento)
     *
     * @param field Campo onde os animais serão inseridos.
     */
    private void populate(Campo field)
    {
        Random rand = new Random();
        field.clear();

        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                
                double probability = rand.nextDouble();
                Environment env = field.getEnvironment(row, col);

                // Usa AnimalFactory para criar animais (desacoplado)
                Animal animal = animalFactory.createAnimal(probability, configProvider);
                
                if(animal != null && animalFactory.canPlace(animal, env)) {
                    animals.add(animal);
                    animal.setLocation(row, col);
                    field.place(animal, row, col);
                }
            }
        }

        Collections.shuffle(animals);
    }

    /**
     * @return passo atual da simulação.
     */
    public int getStep()
    {
        return step;
    }

    /**
     * @return lista de todos os animais vivos no campo.
     */
    public List<Animal> getAnimals()
    {
        return new ArrayList<>(animals);
    }





    /**
     * @return o provedor de configurações
     */
    public ConfigurationProvider getConfigProvider()
    {
        return configProvider;
    }
}
