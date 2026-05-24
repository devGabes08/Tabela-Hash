public abstract class TabelaHash {

    protected String[] tabela;
    protected int capacidade;
    protected int colisoes;
    protected int totalElementos;

    public TabelaHash(int capacidade) {
        this.capacidade = capacidade;
        this.tabela = new String[capacidade];
        this.colisoes = 0;
        this.totalElementos = 0;
    }

    protected abstract int funcaoHash(String chave);

    public void inserir(String chave) {
        int pos = funcaoHash(chave);
        int tentativas = 0;

        while (tabela[pos] != null && tentativas < capacidade) {
            colisoes++;
            pos = (pos + 1) % capacidade; // sondagem linear
            tentativas++;
        }

        if (tentativas < capacidade) {
            tabela[pos] = chave;
            totalElementos++;
        }
    }

    public int buscar(String chave) {
        int pos = funcaoHash(chave);
        int tentativas = 0;

        while (tabela[pos] != null && tentativas < capacidade) {
            if (tabela[pos].equals(chave)) return pos;
            pos = (pos + 1) % capacidade;
            tentativas++;
        }

        return -1;
    }

    public int getColisoes() { return colisoes; }
    public int getTotalElementos() { return totalElementos; }
    public int getCapacidade() { return capacidade; }
    public String[] getTabela() { return tabela; }
}
