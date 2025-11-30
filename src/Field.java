import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Representa um grid retangular de posições do campo.
 * Cada posição pode armazenar um único animal.
 * Melhorado com uso de generics.
 * 
 * @author Código melhorado com POO
 * @version 2025
 */
public class Field
{
    private static final Random rand = new Random();
    
    // Dimensões do campo
    private int depth, width;
    // Armazenamento para os animais
    private Animal[][] field;

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
    }
    
    /**
     * Limpa o campo.
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
     * Generate a random location that is adjacent to the
     * given location, or is the same location.
     * The returned location will be within the valid bounds
     * of the field.
     * @param location The location from which to generate an adjacency.
     * @return A valid location within the grid area. This
     *         may be the same object as the location parameter.
     */
    public Location randomAdjacentLocation(Location location)
    {
        int row = location.getRow();
        int col = location.getCol();
        // Generate an offset of -1, 0, or +1 for both the current row and col.
        int nextRow = row + rand.nextInt(3) - 1;
        int nextCol = col + rand.nextInt(3) - 1;
        // Check in case the new location is outside the bounds.
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
     * Tenta encontrar uma localização livre adjacente à localização dada.
     * @param location Localização de referência
     * @return Localização válida livre ou null se todas estão ocupadas
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
        // Verifica se a localização atual está livre
        if(field[location.getRow()][location.getCol()] == null) {
            return location;
        } 
        else {
            return null;
        }
    }

    /**
     * Gera um iterator sobre uma lista embaralhada de localizações adjacentes.
     * @param location Localização de referência
     * @return Iterator sobre localizações adjacentes
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
                    // Exclui localizações inválidas e a localização original
                    if(nextCol >= 0 && nextCol < width && (roffset != 0 || coffset != 0)) {
                        locations.add(new Location(nextRow, nextCol));
                    }
                }
            }
        }
        Collections.shuffle(locations, rand);
        return locations.iterator();
    }

    /**
     * @return The depth of the field.
     */
    public int getDepth()
    {
        return depth;
    }
    
    /**
     * @return The width of the field.
     */
    public int getWidth()
    {
        return width;
    }
}
