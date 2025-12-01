 /**
 * Classe abstrata que representa um ambiente (terreno) no simulador.
 * Define as regras de acessibilidade para os diferentes animais.
 * * @author Matheus Gomes
 * @version 2025
 */
public abstract class Environment {
    /**
     * Verifica se um determinado animal pode entrar neste ambiente.
     * @param animal O animal que tenta entrar no ambiente.
     * @return true se o animal puder entrar, false caso contrário.
     */
    public abstract boolean canEnter(Animal animal);
    /**
     * Retorna o nome do ambiente.
     * @return Nome do ambiente.
     */
    public abstract String toString();
}
