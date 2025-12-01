import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Representa o campo da simulação.
 * Armazena referências a animais (ou null) e aos ambientes de cada célula.
 *
 * Implementa:
 * - iterator de locais adjacentes (método existente no projeto original)
 * - lista de locais adjacentes (útil para for-each)
 * - métodos auxiliares: freeAdjacentLocation, randomAdjacentLocation, place, getObjectAt, clear, etc.
 *
 * @author
 * @version 2025
 */
public class Campo
{
    private int depth, width;
    private Object[][] field; // armazena Animal ou null
    private Environment[][] environments;
    private Random rand = new Random();

    /**
     * Cria um campo com profundidade e largura.
     *
     * @param depth número de linhas
     * @param width número de colunas
     */
    public Campo(int depth, int width)
    {
        this.depth = depth;
        this.width = width;
        field = new Object[depth][width];
        environments = new Environment[depth][width];
    }

    /**
     * Limpa apenas os animais do campo, preservando os ambientes.
     */
    public void clear()
    {
        for(int r = 0; r < depth; r++) {
            for(int c = 0; c < width; c++) {
                field[r][c] = null;
            }
        }
    }

    /**
     * Coloca um animal na coordenada (row, col).
     * Não realiza checagem de ambiente aqui — quem chama deve validar canEnter.
     *
     * @param animal animal a ser colocado
     * @param row linha
     * @param col coluna
     */
    public void place(Animal animal, int row, int col)
    {
        field[row][col] = animal;
    }

    /**
     * Coloca um animal numa Location.
     * @param animal animal a ser colocado
     * @param loc localização alvo
     */
    public void place(Animal animal, Location loc)
    {
        place(animal, loc.getRow(), loc.getCol());
    }

    /**
     * Recupera o objeto (animal) na Location.
     * @param loc localização
     * @return Animal ou null
     */
    public Animal getObjectAt(Location loc)
    {
        return (Animal) field[loc.getRow()][loc.getCol()];
    }

    /**
     * Recupera o objeto na coordenada.
     */
    public Animal getObjectAt(int row, int col)
    {
        return (Animal) field[row][col];
    }

    /**
     * Retorna a profundidade (número de linhas).
     */
    public int getDepth()
    {
        return depth;
    }

    /**
     * Retorna a largura (número de colunas).
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * Define o ambiente em uma célula.
     * @param row linha
     * @param col coluna
     * @param env environment
     */
    public void setEnvironmentAt(int row, int col, Environment env)
    {
        environments[row][col] = env;
    }

    /**
     * Retorna o ambiente na célula.
     * @param row linha
     * @param col coluna
     * @return Environment
     */
    public Environment getEnvironment(int row, int col)
    {
        return environments[row][col];
    }

    /**
     * Retorna o ambiente dado uma Location.
     * @param loc localização
     * @return Environment
     */
    public Environment getEnvironment(Location loc)
    {
        return getEnvironment(loc.getRow(), loc.getCol());
    }

    /**
     * Retorna um Iterator<Location> representando as localizações adjacentes
     * (8 vizinhos) dentro do campo. Este método preserva compatibilidade com projetos que
     * esperam um Iterator.
     *
     * @param location localização central
     * @return Iterator<Location>
     */
    public Iterator<Location> adjacentLocations(final Location location)
    {
        final List<Location> locs = adjacentLocationsList(location);
        return locs.iterator();
    }

    /**
     * Retorna uma lista com as localizações adjacentes válidas.
     * Útil para for-each e para evitar reter/consumir iterators.
     *
     * @param location localização central
     * @return List<Location> com locais adjacentes
     */
    public List<Location> adjacentLocationsList(Location location)
    {
        List<Location> locations = new ArrayList<>();
        int row = location.getRow();
        int col = location.getCol();

        for(int roff = -1; roff <= 1; roff++) {
            int nextRow = row + roff;
            if(nextRow < 0 || nextRow >= depth) continue;
            for(int coff = -1; coff <= 1; coff++) {
                int nextCol = col + coff;
                if(nextCol < 0 || nextCol >= width) continue;
                // pula a própria célula
                if(roff == 0 && coff == 0) continue;
                locations.add(new Location(nextRow, nextCol));
            }
        }
        return locations;
    }

    /**
     * Retorna uma localização adjacente livre (primeira encontrada), ou null.
     * Observação: a validação do ambiente (Environment.canEnter) fica a cargo de quem chama.
     *
     * @param location localização central
     * @return Location livre ou null
     */
    public Location freeAdjacentLocation(Location location)
    {
        List<Location> adj = adjacentLocationsList(location);
        for(Location loc : adj) {
            if(getObjectAt(loc) == null) {
                return loc;
            }
        }
        return null;
    }

    /**
     * Retorna uma localização adjacente aleatória (pode estar ocupada), ou null.
     * Útil para espalhar filhotes em locais adjacentes aleatórios.
     *
     * @param location localização central
     * @return Location ou null
     */
    public Location randomAdjacentLocation(Location location)
    {
        List<Location> adj = adjacentLocationsList(location);
        if(adj.isEmpty()) return null;
        return adj.get(rand.nextInt(adj.size()));
    }
}
