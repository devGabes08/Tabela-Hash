
public class TabelaHash2 extends TabelaHash {

    public TabelaHash2(int capacidade) {
        super(capacidade);
    }

    @Override
    protected int funcaoHash(String chave) {
        long hash = 7;
        for (int i = 0; i < chave.length(); i++) {
            hash = hash * 31 + chave.charAt(i);
        }
        int resultado = (int) (hash % capacidade);
        if (resultado < 0) resultado += capacidade;
        return resultado;
    }
}
