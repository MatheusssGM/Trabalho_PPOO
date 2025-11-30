/**
 * Representa o ambiente de Savana.
 * Restrição: Acessível apenas a humanos e leões.
 * Animais como coelhos, raposas e lobos não podem entrar.
 * * @author Matheus Gomes
 * @version 2025
 */
public class Savanna extends Environment
{
    /**
     * Verifica se o animal pode entrar na savana.
     * Apenas Humanos e Leões são permitidos.
     * * @param animal O animal que tenta entrar.
     * @return true se for Humano ou Leão.
     */
    @Override
    public boolean canEnter(Animal animal)
    {
        String className = animal.getClass().getSimpleName();

        // A lógica abaixo verifica pelos nomes das classes para evitar erros 
        // caso Human e Lion ainda não tenham sido criados no seu projeto.
        // Quando existirem, substitua por: 
        // return (animal instanceof Human) || (animal instanceof Lion);

        if(className.equals("Human") || className.equals("Lion")) {
            return true;
        }

        return false;
    }

    @Override
    public String toString()
    {
        return "Savana";
    }
}