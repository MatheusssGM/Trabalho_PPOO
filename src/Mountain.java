/**
 * Representa o ambiente de Montanha.
 * Restrição: Inacessível para leões.
 * * @author Matheus Gomes
 * @version 2025
 */
public class Mountain extends Environment
{
    /**
     * Verifica se o animal pode entrar na montanha.
     * Leões não podem entrar.
     * * @param animal O animal que tenta entrar.
     * @return true se não for um leão.
     */
    @Override
    public boolean canEnter(Animal animal)
    {
        // Verifica se o animal é uma instância de Lion (Leão)
        // Nota: A classe Lion deve ser criada pelo outro membro do grupo
        if(animal.getClass().getSimpleName().equals("Lion")) {
            return false;
        }

        // Se a classe Lion ainda não existir e você quiser testar,
        // o código acima usa o nome da classe (String) para evitar erros de compilação agora.
        // O ideal no futuro é usar: return !(animal instanceof Lion);

        return true;
    }

    @Override
    public String toString()
    {
        return "Montanha";
    }
}