/**
 * Representa o ambiente de Toca (Refúgio).
 * Restrição: Exclusivo para coelhos.
 * Predadores e humanos não podem entrar.
 * * @author Matheus Gomes
 * @version 2025
 */
public class Burrow extends Environment
{
    /**
     * Verifica se o animal pode entrar na toca.
     * Apenas Coelhos são permitidos.
     * * @param animal O animal que tenta entrar.
     * @return true se for um Coelho.
     */
    @Override
    public boolean canEnter(Animal animal)
    {
        // Verifica se é um coelho
        return (animal instanceof Rabbit);
    }

    @Override
    public String toString()
    {
        return "Toca";
    }
}