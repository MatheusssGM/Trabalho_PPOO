import java.util.List;
import java.util.Iterator;

/**
 * Modelo de um coelho no simulador.
 * Coelhos se reproduzem rápido, fogem de predadores e usam tocas como refúgio.
 *
 * Versão balanceada 2025:
 * - Reprodução aumentada (0.30, ninhada até 6)
 * - Fuga mais eficiente
 * - Melhor detecção de predadores
 * - Coelho permanece na toca sempre que possível
 */
public class Rabbit extends Animal implements Prey
{
    // Configurações balanceadas
    private static final int BREEDING_AGE = 5;
    private static final int MAX_AGE = 50;
    private static final double BREEDING_PROBABILITY = 0.30;
    private static final int MAX_LITTER_SIZE = 6;

    public Rabbit(boolean randomAge)
    {
        super(randomAge);
    }

    @Override
    public void act(Campo currentField, Campo updatedField, List<Animal> newAnimals)
    {
        incrementAge();
        if(!isAlive()) return;

        // ⛺ Se o coelho estiver na toca, ele fica nela e só reproduz
        if(currentField.getEnvironment(location) instanceof Burrow) {
            int births = breed();
            for(int b = 0; b < births; b++) {
                Rabbit young = new Rabbit(false);
                Location loc = updatedField.randomAdjacentLocation(location);
                if(loc != null &&
                        updatedField.getObjectAt(loc) == null &&
                        updatedField.getEnvironment(loc).canEnter(young)) {

                    young.setLocation(loc);
                    newAnimals.add(young);
                    updatedField.place(young, loc);
                }
            }

            updatedField.place(this, location);
            return; // não sai da toca
        }

        // Se detecta predadores, tenta fugir
        if(detectsPredators(currentField, location)) {
            Location escapeLoc = escape(currentField, location);
            if(escapeLoc != null) {
                setLocation(escapeLoc);
                updatedField.place(this, escapeLoc);
                return;
            }
        }

        // Reprodução normal
        int births = breed();
        for(int b = 0; b < births; b++) {
            Rabbit young = new Rabbit(false);
            Location loc = updatedField.randomAdjacentLocation(location);
            if(loc != null &&
                    updatedField.getObjectAt(loc) == null &&
                    updatedField.getEnvironment(loc).canEnter(young)) {

                young.setLocation(loc);
                newAnimals.add(young);
                updatedField.place(young, loc);
            }
        }

        // Movimento normal se nada de especial acontecer
        Location newLocation = updatedField.freeAdjacentLocation(location);
        if(newLocation != null &&
                updatedField.getEnvironment(newLocation).canEnter(this)) {

            setLocation(newLocation);
            updatedField.place(this, newLocation);
        }
        else {
            updatedField.place(this, location);
        }
    }

    //  Predadores reais: Fox e Lion
    @Override
    public boolean detectsPredators(Campo field, Location location)
    {
        Iterator<Location> it = field.adjacentLocations(location);
        while(it.hasNext()) {
            Animal a = field.getObjectAt(it.next());
            if(a == null) continue;

            // Raposa
            if(a instanceof Fox) return true;

            // Leão (evitar instanceof para não quebrar enquanto Lion não existir)
            if(a.getClass().getSimpleName().equals("Lion")) return true;
        }
        return false;
    }

    // Fuga inteligente - evita predadores e ambientes ilegais
    @Override
    public Location escape(Campo field, Location location)
    {
        Iterator<Location> it = field.adjacentLocations(location);

        while(it.hasNext()) {
            Location loc = it.next();

            // Local precisa estar livre
            if(field.getObjectAt(loc) != null) continue;

            // Ambiente precisa aceitar o coelho
            if(!field.getEnvironment(loc).canEnter(this)) continue;

            // Verificar se tem predadores no novo local
            Iterator<Location> check = field.adjacentLocations(loc);
            boolean danger = false;

            while(check.hasNext()) {
                Animal a = field.getObjectAt(check.next());
                if(a == null) continue;

                if(a instanceof Fox) { danger = true; break; }
                if(a.getClass().getSimpleName().equals("Lion")) { danger = true; break; }
            }

            if(!danger) return loc; // ⟶ fuga bem-sucedida
        }

        return null; // nenhuma fuga possível
    }

    @Override
    protected Animal createOffspring(boolean randomAge)
    {
        return new Rabbit(randomAge);
    }

    @Override
    protected int getMaxAge()
    {
        return MAX_AGE;
    }

    @Override
    protected int getBreedingAge()
    {
        return BREEDING_AGE;
    }

    @Override
    protected double getBreedingProbability()
    {
        return BREEDING_PROBABILITY;
    }

    @Override
    protected int getMaxLitterSize()
    {
        return MAX_LITTER_SIZE;
    }
}
