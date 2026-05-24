
public class TabelaHash1 extends TabelaHash {

    public TabelaHash1(int capacidade) {
        super(capacidade);
    }

    @Override
    protected int funcaoHash(String chave) {
        int soma = 0;
        for (int i = 0; i < chave.length(); i++) {
            soma += chave.charAt(i);
        }
        return soma % capacidade;
    }
}
