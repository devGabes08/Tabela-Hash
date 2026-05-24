import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeitorCSV {

    public static String[] lerNomes(String caminho) throws IOException {

        int count = 0;
        BufferedReader br = new BufferedReader(new FileReader(caminho));
        String linha = br.readLine();
        while ((linha = br.readLine()) != null) {
            if (!linha.trim().isEmpty()) count++;
        }
        br.close();


        String[] nomes = new String[count];
        br = new BufferedReader(new FileReader(caminho));
        br.readLine();
        int i = 0;
        while ((linha = br.readLine()) != null) {
            nomes[i++] = linha;
        }
        br.close();
        return nomes;
    }
}
