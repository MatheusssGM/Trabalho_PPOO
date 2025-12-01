/**
 * Implementação concreta da fábrica de animais.
 * Centraliza a lógica de criação de animais seguindo o Factory Pattern.
 * 
 * @author Código melhorado com POO
 * @version 2025
 */
public class ConcreteAnimalFactory implements AnimalFactory {
    
    @Override
    public Animal createAnimal(double probability, ConfigurationProvider config) {
        double humanProb = config.getHumanProbability();
        double lionProb = config.getLionProbability();
        double foxProb = config.getFoxProbability();
        double rabbitProb = config.getRabbitProbability();
        
        if (probability <= humanProb) {
            return new Human(true);
        }
        else if (probability <= humanProb + lionProb) {
            return new Lion(true);
        }
        else if (probability <= humanProb + lionProb + foxProb) {
            return new Fox(true);
        }
        else if (probability <= humanProb + lionProb + foxProb + rabbitProb) {
            return new Rabbit(true);
        }
        
        return null; // Não criar animal
    }
    
    @Override
    public boolean canPlace(Animal animal, Environment environment) {
        return environment != null && environment.canEnter(animal);
    }
}