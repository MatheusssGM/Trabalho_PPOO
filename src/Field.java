import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Representa um grid retangular de posições do campo.
 * Cada posição pode armazenar um único animal e possui um Ambiente associado.
 * * @author Código melhorado com POO
 * @version 2025
 */
public class Field
{
    private static final Random rand = new Random();

    // Dimensões do campo
    private int depth, width;
    // Armazenamento para os animais
    private Animal[][] field;
    // Armazenamento para os ambientes (terreno)
    private Environment[][] environments;

    /**
     * Representa um campo com as dimensões dadas.
     * @param depth Profundidade do campo
     * @param width Largura do campo
     */
    public Field(int depth, int width)
    {
        this.depth = depth;
        this.width = width;
        field = new Animal[depth][width];
        environments = new Environment[depth][width];
    }

    /**
     * Limpa o campo (remove animais), mas mantem os ambientes.
     */
    public void clear()
    {
        for(int row = 0; row < depth; row++) {
            for(int col = 0; col < width; col++) {
                field[row][col] = null;
            }
        }
    }

    /**
     * Define o ambiente para uma localização específica.
     * @param env O ambiente a ser definido (Montanha, Savana, etc.)
     * @param row Linha
     * @param col Coluna
     */
    public void setEnvironmentAt(int row, int col, Environment env) {
        if(row >= 0 && row < depth && col >= 0 && col < width) {
            environments[row][col] = env;
        }
    }

    /**
     * Retorna o ambiente na localização dada.
     * @param location Localização no campo
     * @return O ambiente na localização
     */
    public Environment getEnvironment(Location location) {
        return getEnvironment(location.getRow(), location.getCol());
    }

    /**
     * Retorna o ambiente nas coordenadas dadas.
     * @param row Linha
     * @param col Coluna
     * @return O ambiente ou null se fora dos limites
     */
    public Environment getEnvironment(int row, int col) {
        if(row >= 0 && row < depth && col >= 0 && col < width) {
            return environments[row][col];
        }
        return null;
    }

    /**
     * Coloca um animal na localização dada.
     * @param animal Animal a ser colocado
     * @param row Coordenada da linha
     * @param col Coordenada da coluna
     */
    public void place(Animal animal, int row, int col)
    {
        place(animal, new Location(row, col));
    }

    /**
     * Coloca um animal na localização dada.
     * @param animal Animal a ser colocado
     * @param location Onde colocar o animal
     */
    public void place(Animal animal, Location location)
    {
        field[location.getRow()][location.getCol()] = animal;
    }

    /**
     * Retorna o animal na localização dada, se houver.
     * @param location Localização no campo
     * @return Animal na localização ou null se não houver
     */
    public Animal getObjectAt(Location location)
    {
        return getObjectAt(location.getRow(), location.getCol());
    }

    /**
     * Retorna o animal na localização dada, se houver.
     * @param row Linha desejada
     * @param col Coluna desejada
     * @return Animal na localização ou null se não houver
     */
    public Animal getObjectAt(int row, int col)
    {
        return field[row][col];
    }

    /**
     * Gera uma localização aleatória adjacente.
     */
    public Location randomAdjacentLocation(Location location)
    {
        int row = location.getRow();
        int col = location.getCol();
        int nextRow = row + rand.nextInt(3) - 1;
        int nextCol = col + rand.nextInt(3) - 1;
        if(nextRow < 0 || nextRow >= depth || nextCol < 0 || nextCol >= width) {
            return location;
        }
        else if(nextRow != row || nextCol != col) {
            return new Location(nextRow, nextCol);
        }
        else {
            return location;
        }
    }

    /**
     * Tenta encontrar uma localização livre adjacente.
     */
    public Location freeAdjacentLocation(Location location)
    {
        Iterator<Location> adjacent = adjacentLocations(location);
        while(adjacent.hasNext()) {
            Location next = adjacent.next();
            if(field[next.getRow()][next.getCol()] == null) {
                return next;
            }
        }
        if(field[location.getRow()][location.getCol()] == null) {
            return location;
        }
        else {
            return null;
        }
    }

    /**
     * Gera um iterator sobre uma lista embaralhada de localizações adjacentes.
     */
    public Iterator<Location> adjacentLocations(Location location)
    {
        int row = location.getRow();
        int col = location.getCol();
        LinkedList<Location> locations = new LinkedList<>();
        for(int roffset = -1; roffset <= 1; roffset++) {
            int nextRow = row + roffset;
            if(nextRow >= 0 && nextRow < depth) {
                for(int coffset = -1; coffset <= 1; coffset++) {
                    int nextCol = col + coffset;
                    if(nextCol >= 0 && nextCol < width && (roffset != 0 || coffset != 0)) {
                        locations.add(new Location(nextRow, nextCol));
                    }
                }
            }
        }
        Collections.shuffle(locations, rand);
        return locations.iterator();
    }

    public int getDepth() { return depth; }
    public int getWidth() { return width; }
}