import java.io.*;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static String nomeJogador;
    private static int jogosNestaSessao = 0;
    private static JogoForca jogoForca;

    private static long inicioJogo;
    private static long fimJogo;

    private static final String CAMINHO_ESTATISTICAS_GERAIS = "src/estatisticas_gerais.txt";

    public static void main(String[] args) {
        pedirNomeJogador();
        mostrarMelhorJogador();
        mostrarEstatisticasPessoais();
        menuPrincipal();
    }

    private static void pedirNomeJogador() {
        System.out.print("Digite o seu nome: ");
        nomeJogador = scanner.nextLine().trim();
        if (nomeJogador.isEmpty()) {
            nomeJogador = "Jogador";
        }
    }

    private static void mostrarMelhorJogador() {
        File file = new File(CAMINHO_ESTATISTICAS_GERAIS);
        if (!file.exists()) {
            System.out.println("\n Ainda não existem estatísticas gerais.");
            return;
        }

        String melhorJogador = "";
        String melhorPalavra = "";
        long melhorTempo = Long.MAX_VALUE;
        String melhorResultado = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;
            String jogadorAtual = "";
            String palavraAtual = "";
            long tempoAtual = 0;
            String resultadoAtual = "";

            while ((linha = reader.readLine()) != null) {
                if (linha.startsWith("Jogador: ")) {
                    jogadorAtual = linha.substring(9);
                } else if (linha.startsWith("Palavra: ")) {
                    palavraAtual = linha.substring(9);
                } else if (linha.startsWith("Resultado: ")) {
                    resultadoAtual = linha.substring(10);
                } else if (linha.startsWith("Tempo de jogo (s): ")) {
                    try {
                        tempoAtual = Long.parseLong(linha.substring(19));
                        if (resultadoAtual.equals("VITÓRIA") && tempoAtual < melhorTempo) {
                            melhorTempo = tempoAtual;
                            melhorJogador = jogadorAtual;
                            melhorPalavra = palavraAtual;
                            melhorResultado = resultadoAtual;
                        }
                    } catch (NumberFormatException e) {
                        // Ignorar entradas inválidas
                    }
                }
            }

            if (!melhorJogador.isEmpty()) {
                System.out.println("\n Melhor Jogador:");
                System.out.println("Nome: " + melhorJogador);
                System.out.println("Palavra: " + melhorPalavra);
                System.out.println("Resultado: " + melhorResultado);
                System.out.println("Tempo: " + melhorTempo + " segundos");
            } else {
                System.out.println("\n Ainda não há vitórias registadas.");
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler estatísticas gerais: " + e.getMessage());
        }
    }

    private static void mostrarEstatisticasPessoais() {
        File file = new File("src/estatisticas_" + nomeJogador + ".txt");
        if (file.exists()) {
            System.out.println("\n Estatísticas anteriores de " + nomeJogador + ":");
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String linha;
                while ((linha = reader.readLine()) != null) {
                    System.out.println(linha);
                }
            } catch (IOException e) {
                System.out.println("Erro ao ler estatísticas pessoais: " + e.getMessage());
            }
        } else {
            System.out.println("\n Olá, " + nomeJogador + "! Parece que é a tua primeira vez a jogar.");
        }
    }

    private static void menuPrincipal() {
        while (true) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Jogar");
            System.out.println("2. Sair");
            System.out.println("3. Adicionar nova palavra");
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    executarJogo();
                    break;
                case "2":
                    System.out.println("Até à próxima, " + nomeJogador + "!");
                    return;
                case "3":
                    adicionarNovaPalavra();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void executarJogo() {
        jogoForca = new JogoForca();
        jogosNestaSessao++;
        inicioJogo = System.currentTimeMillis();

        while (!jogoForca.jogoTerminado()) {
            System.out.println("\nPalavra: " + jogoForca.getPalavraEscondida());
            System.out.println("Tentativas restantes: " + jogoForca.getTentativasRestantes());
            System.out.print("Digite uma letra: ");
            String entrada = scanner.nextLine().toLowerCase();

            if (entrada.length() != 1 || !Character.isLetter(entrada.charAt(0))) {
                System.out.println("Por favor, digite apenas uma letra.");
                continue;
            }

            jogoForca.tentarLetra(entrada.charAt(0));
        }

        fimJogo = System.currentTimeMillis();
        long duracaoSegundos = (fimJogo - inicioJogo) / 1000;

        mostrarResultadoFinal(duracaoSegundos);
        guardarEstatisticasIndividual(duracaoSegundos);
        guardarEstatisticasGerais(duracaoSegundos);
    }

    private static void mostrarResultadoFinal(long duracaoSegundos) {
        System.out.println("\n=== FIM DO JOGO ===");
        System.out.println("Palavra correta: " + jogoForca.getPalavraSecreta());
        if (jogoForca.isJogadorVenceu()) {
            System.out.println("Parabéns, " + nomeJogador + "! Você venceu!");
        } else {
            System.out.println("Que pena, " + nomeJogador + ". Você perdeu.");
        }
        System.out.println(" Tempo de jogo: " + duracaoSegundos + " segundos.");
    }

    private static void guardarEstatisticasIndividual(long duracaoSegundos) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("src/estatisticas_" + nomeJogador + ".txt", true))) {
            writer.println("Jogo #" + jogosNestaSessao);
            writer.println("Palavra: " + jogoForca.getPalavraSecreta());
            writer.println("Resultado: " + (jogoForca.isJogadorVenceu() ? "VITÓRIA" : "DERROTA"));
            writer.println("Letras tentadas: " + jogoForca.getLetrasTentadas());
            writer.println("Tentativas restantes: " + jogoForca.getTentativasRestantes());
            writer.println("Corretas: " + jogoForca.getNumeroLetrasCorretas());
            writer.println("Incorretas: " + jogoForca.getNumeroLetrasIncorretas());
            writer.println("Total tentativas: " + jogoForca.getNumeroTotalTentativas());
            writer.println("Tempo de jogo (s): " + duracaoSegundos);
            writer.println("-----------------------------");
        } catch (IOException e) {
            System.out.println("Erro ao guardar estatísticas individuais: " + e.getMessage());
        }
    }

    private static void guardarEstatisticasGerais(long duracaoSegundos) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CAMINHO_ESTATISTICAS_GERAIS, true))) {
            writer.println("Jogador: " + nomeJogador);
            writer.println("Palavra: " + jogoForca.getPalavraSecreta());
            writer.println("Resultado: " + (jogoForca.isJogadorVenceu() ? "VITÓRIA" : "DERROTA"));
            writer.println("Letras corretas: " + jogoForca.getNumeroLetrasCorretas());
            writer.println("Letras incorretas: " + jogoForca.getNumeroLetrasIncorretas());
            writer.println("Total de letras tentadas: " + jogoForca.getNumeroTotalTentativas());
            writer.println("Tempo de jogo (s): " + duracaoSegundos);
            writer.println("-----------------------------");
        } catch (IOException e) {
            System.out.println("Erro ao guardar estatísticas gerais: " + e.getMessage());
        }
    }

    private static void adicionarNovaPalavra() {
        System.out.print("Digite a nova palavra a adicionar: ");
        String novaPalavra = scanner.nextLine().trim().toLowerCase();

        if (novaPalavra.isEmpty() || !novaPalavra.matches("[a-zA-Z]+")) {
            System.out.println("Palavra inválida. Use apenas letras.");
            return;
        }

        GestorPalavras.adicionarPalavra(novaPalavra);
    }
}
