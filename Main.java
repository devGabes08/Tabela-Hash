public class Main {

    static final int CAPACIDADE = 16;
    static final String ARQUIVO = "nomes_20000_reais_distintos.csv";
    static final int TOTAL_BUSCAS = 100;

    public static void main(String[] args) throws Exception {


        System.out.println("Lendo arquivo CSV...");
        String[] nomes = LeitorCSV.lerNomes(ARQUIVO);
        System.out.println("Total de nomes lidos: " + nomes.length);


        TabelaHash t1 = new TabelaHash1(CAPACIDADE);
        TabelaHash t2 = new TabelaHash2(CAPACIDADE);


        long inicioInsercao1 = System.nanoTime();
        for (int i = 0; i < nomes.length; i++) {
            t1.inserir(nomes[i]);
        }
        long fimInsercao1 = System.nanoTime();


        long inicioInsercao2 = System.nanoTime();
        for (int i = 0; i < nomes.length; i++) {
            t2.inserir(nomes[i]);
        }
        long fimInsercao2 = System.nanoTime();


        long inicioBusca1 = System.nanoTime();
        for (int i = 0; i < TOTAL_BUSCAS && i < nomes.length; i++) {
            t1.buscar(nomes[i]);
        }
        long fimBusca1 = System.nanoTime();

        long inicioBusca2 = System.nanoTime();
        for (int i = 0; i < TOTAL_BUSCAS && i < nomes.length; i++) {
            t2.buscar(nomes[i]);
        }
        long fimBusca2 = System.nanoTime();


        imprimirRelatorio(t1, "Hash 1 (Soma ASCII)",
                fimInsercao1 - inicioInsercao1,
                fimBusca1 - inicioBusca1);

        System.out.println("\n" + "=".repeat(60) + "\n");

        imprimirRelatorio(t2, "Hash 2 (Polinomial djb2)",
                fimInsercao2 - inicioInsercao2,
                fimBusca2 - inicioBusca2);
    }

    static void imprimirRelatorio(TabelaHash t, String nome, long tempoIns, long tempoBusca) {
        System.out.println("========================================");
        System.out.println("TABELA: " + nome);
        System.out.println("========================================");
        System.out.println("Capacidade da tabela : " + t.getCapacidade());
        System.out.println("Elementos inseridos  : " + t.getTotalElementos());
        System.out.println("Total de colisoes    : " + t.getColisoes());
        System.out.printf("Tempo de insercao    : %d ns (%.3f ms)%n", tempoIns, tempoIns / 1_000_000.0);
        System.out.printf("Tempo de busca (%d)  : %d ns (%.3f ms)%n", TOTAL_BUSCAS, tempoBusca, tempoBusca / 1_000_000.0);

        System.out.println("\n--- Distribuicao das chaves por posicao ---");
        String[] tabela = t.getTabela();
        for (int i = 0; i < tabela.length; i++) {
            String conteudo = tabela[i] != null ? tabela[i] : "(vazio)";
            System.out.printf("Posicao %2d: %s%n", i, conteudo);
        }

        System.out.println("\n--- Clusterizacao (colisoes por posicao) ---");

        int[] tamanhoCluster = new int[tabela.length];
        int clusterAtual = 0;
        int inicioClusters = -1;

        for (int i = 0; i < tabela.length; i++) {
            if (tabela[i] != null) {
                if (clusterAtual == 0) inicioClusters = i;
                clusterAtual++;
            } else {
                if (clusterAtual > 0) {
                    System.out.printf("  Cluster na posicao %2d: tamanho %d (colisoes internas: %d)%n",
                            inicioClusters, clusterAtual, clusterAtual - 1);
                }
                clusterAtual = 0;
            }
        }
        if (clusterAtual > 0) {
            System.out.printf("  Cluster na posicao %2d: tamanho %d (colisoes internas: %d)%n",
                    inicioClusters, clusterAtual, clusterAtual - 1);
        }

        int ocupadas = 0;
        for (int i = 0; i < tabela.length; i++) {
            if (tabela[i] != null) ocupadas++;
        }
        System.out.printf("%nPosicoes ocupadas: %d / %d (%.1f%%)%n",
                ocupadas, tabela.length, (ocupadas * 100.0 / tabela.length));
    }
}
