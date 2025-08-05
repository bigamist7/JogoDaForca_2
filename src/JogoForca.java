import java.util.*;

public class JogoForca {
    private String palavraSecreta;
    private Set<Character> letrasTentadas = new HashSet<>();
    private int tentativasRestantes = 6;

    public JogoForca() {
        palavraSecreta = GestorPalavras.obterPalavraAleatoria();
    }

    public boolean jogoTerminado() {
        return isJogadorVenceu() || tentativasRestantes <= 0;
    }

    public boolean isJogadorVenceu() {
        for (char c : palavraSecreta.toCharArray()) {
            if (!letrasTentadas.contains(c)) {
                return false;
            }
        }
        return true;
    }

    public void tentarLetra(char letra) {
        if (letrasTentadas.contains(letra)) {
            System.out.println("Você já tentou essa letra.");
            return;
        }

        letrasTentadas.add(letra);

        if (!palavraSecreta.contains(String.valueOf(letra))) {
            tentativasRestantes--;
            System.out.println("Letra incorreta!");
        } else {
            System.out.println("Boa! Letra correta.");
        }
    }

    public String getPalavraEscondida() {
        StringBuilder sb = new StringBuilder();
        for (char c : palavraSecreta.toCharArray()) {
            if (letrasTentadas.contains(c)) {
                sb.append(c).append(" ");
            } else {
                sb.append("_ ");
            }
        }
        return sb.toString().trim();
    }

    public String getPalavraSecreta() {
        return palavraSecreta;
    }

    public int getTentativasRestantes() {
        return tentativasRestantes;
    }

    public String getLetrasTentadas() {
        List<Character> lista = new ArrayList<>(letrasTentadas);
        Collections.sort(lista);
        StringBuilder sb = new StringBuilder();
        for (char c : lista) {
            sb.append(c).append(" ");
        }
        return sb.toString().trim();
    }

    public int getNumeroLetrasCorretas() {
        int count = 0;
        for (char c : letrasTentadas) {
            if (palavraSecreta.contains(String.valueOf(c))) {
                count++;
            }
        }
        return count;
    }

    public int getNumeroLetrasIncorretas() {
        int count = 0;
        for (char c : letrasTentadas) {
            if (!palavraSecreta.contains(String.valueOf(c))) {
                count++;
            }
        }
        return count;
    }

    public int getNumeroTotalTentativas() {
        return letrasTentadas.size();
    }
}