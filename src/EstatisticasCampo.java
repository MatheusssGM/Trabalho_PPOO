import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Esta classe coleta e fornece dados estatísticos sobre o estado do campo.
 * É flexível: criará e manterá um contador para qualquer classe de objeto
 * encontrada no campo. Melhorada com uso de generics.
 * 
 * @author Código melhorado com POO
 * @version 2025
 */
public class EstatisticasCampo
{
    // Contadores para cada tipo de entidade (raposa, coelho, etc.)
    private Map<Class<?>, Counter> counters;
    // Se os contadores estão atualizados
    private boolean countsValid;

    /**
     * Constrói um objeto de estatísticas de campo.
     */
    public EstatisticasCampo()
    {
        // Configura uma coleção para contadores de cada tipo de animal
        counters = new HashMap<>();
        countsValid = true;
    }

    /**
     * @return String descrevendo quais animais estão no campo
     */
    public String getPopulationDetails(Campo field)
    {
        StringBuilder buffer = new StringBuilder();
        if(!countsValid) {
            generateCounts(field);
        }
        for(Counter info : counters.values()) {
            buffer.append(info.getName());
            buffer.append(": ");
            buffer.append(info.getCount());
            buffer.append(' ');
        }
        return buffer.toString();
    }
    
    /**
     * Invalida o conjunto atual de estatísticas; reseta todas as contagens.
     */
    public void reset()
    {
        countsValid = false;
        for(Counter cnt : counters.values()) {
            cnt.reset();
        }
    }

    /**
     * Incrementa a contagem para uma classe de animal.
     * @param animalClass Classe do animal
     */
    public void incrementCount(Class<?> animalClass)
    {
        Counter cnt = counters.get(animalClass);
        if(cnt == null) {
            // Ainda não temos um contador para esta espécie - cria um
            cnt = new Counter(animalClass.getSimpleName());
            counters.put(animalClass, cnt);
        }
        cnt.increment();
    }

    /**
     * Indica que uma contagem de animais foi completada.
     */
    public void countFinished()
    {
        countsValid = true;
    }

    /**
     * Determina se a simulação ainda é viável.
     * @return true se há mais de uma espécie viva
     */
    public boolean isViable(Campo field)
    {
        int nonZero = 0;
        if(!countsValid) {
            generateCounts(field);
        }
        for(Counter info : counters.values()) {
            if(info.getCount() > 0) {
                nonZero++;
            }
        }
        return nonZero > 1;
    }
    
    /**
     * Gera contagens do número de raposas e coelhos.
     * Só atualiza quando uma solicitação é feita.
     * @param field Campo a ser analisado
     */
    private void generateCounts(Campo field)
    {
        reset();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Animal animal = field.getObjectAt(row, col);
                if(animal != null) {
                    incrementCount(animal.getClass());
                }
            }
        }
        countsValid = true;
    }
}
