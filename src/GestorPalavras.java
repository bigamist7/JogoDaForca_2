import java.io.*;
import java.util.*;

public class GestorPalavras {
    private static final String FICHEIRO_PALAVRAS = "src/palavras.txt";
    private static List<String> todasPalavras = new ArrayList<>();
    private static Set<String> palavrasUsadas = new HashSet<>();

    static {
        carregarPalavrasDoFicheiro();
    }

    private static void carregarPalavrasDoFicheiro() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FICHEIRO_PALAVRAS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String palavra = linha.trim().toLowerCase();
                if (!palavra.isEmpty()) {
                    todasPalavras.add(palavra);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o ficheiro de palavras: " + e.getMessage());
        }
    }

    public static String obterPalavraAleatoria() {
        List<String> palavrasDisponiveis = new ArrayList<>(todasPalavras);
        palavrasDisponiveis.removeAll(palavrasUsadas);

        if (palavrasDisponiveis.isEmpty()) {
            palavrasUsadas.clear(); // Recomeça se todas já foram usadas
            palavrasDisponiveis = new ArrayList<>(todasPalavras);
        }

        String palavra = palavrasDisponiveis.get(new Random().nextInt(palavrasDisponiveis.size()));
        palavrasUsadas.add(palavra);
        return palavra;
    }

    public static void adicionarPalavra(String novaPalavra) {
        novaPalavra = novaPalavra.trim().toLowerCase();
        if (!todasPalavras.contains(novaPalavra)) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(FICHEIRO_PALAVRAS, true))) {
                writer.println(novaPalavra);
                todasPalavras.add(novaPalavra);
                System.out.println("Palavra adicionada com sucesso.");
            } catch (IOException e) {
                System.out.println("Erro ao adicionar palavra: " + e.getMessage());
            }
        } else {
            System.out.println("Essa palavra já existe.");
        }
    }
}