/**
 * Representa o ambiente de Planície (padrão).
 * Sem restrições: Todos os animais podem entrar.
 * @author Seu Nome/Grupo
 * @version 2025
 */
public class Plains extends Environment
{
    /**
     * Verifica se o animal pode entrar.
     * Na planície, todos são bem-vindos.
     */
    @Override
    public boolean canEnter(Animal animal)
    {
        return true;
    }

    @Override
    public String toString()
    {
        return "Planície";
    }
}