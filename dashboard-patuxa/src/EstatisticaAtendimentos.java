import java.util.Arrays;

/**
 * PATUXA — Classe responsável pelos cálculos estatísticos
 * (média, moda e mediana) do número de atendimentos por dia.
 */
public class EstatisticaAtendimentos {

    private final int[] atendimentosPorDia;

    public EstatisticaAtendimentos(int[] atendimentosPorDia) {
        if (atendimentosPorDia == null || atendimentosPorDia.length == 0) {
            throw new IllegalArgumentException("Informe ao menos um dia de atendimentos.");
        }
        this.atendimentosPorDia = atendimentosPorDia.clone(); // protege os dados originais
    }

    /** Dados na ordem original (cópia, para não expor o vetor interno). */
    public int[] getAtendimentosPorDia() {
        return atendimentosPorDia.clone();
    }

    /** Dados em ordem crescente. */
    public int[] getOrdenados() {
        int[] ordenados = atendimentosPorDia.clone();
        Arrays.sort(ordenados);
        return ordenados;
    }

    /** Quantidade de dias analisados. */
    public int getQuantidadeDias() {
        return atendimentosPorDia.length;
    }

    /** Soma de todos os atendimentos. */
    public int total() {
        int soma = 0;
        for (int valor : atendimentosPorDia) {
            soma += valor;
        }
        return soma;
    }

    /** Média aritmética: soma dos valores dividida pela quantidade. */
    public double media() {
        return (double) total() / atendimentosPorDia.length;
    }

    /**
     * Mediana: valor central dos dados ordenados.
     * Se a quantidade for par, é a média dos dois valores centrais.
     */
    public double mediana() {
        int[] ordenados = getOrdenados();
        int meio = ordenados.length / 2;
        if (ordenados.length % 2 == 0) {
            return (ordenados[meio - 1] + ordenados[meio]) / 2.0;
        }
        return ordenados[meio];
    }

    /** Quantas vezes um valor aparece nos dados. */
    public int frequenciaDe(int alvo) {
        int contador = 0;
        for (int valor : atendimentosPorDia) {
            if (valor == alvo) {
                contador++;
            }
        }
        return contador;
    }

    /** Maior número de vezes que um mesmo valor se repete. */
    public int maiorFrequencia() {
        int maior = 0;
        for (int valor : atendimentosPorDia) {
            int vezes = frequenciaDe(valor);
            if (vezes > maior) {
                maior = vezes;
            }
        }
        return maior;
    }

    /**
     * Moda: valor(es) que mais se repete(m), em ordem crescente.
     * Retorna um vetor vazio se nenhum valor se repetir (amodal).
     */
    public int[] moda() {
        int maior = maiorFrequencia();
        if (maior == 1) {
            return new int[0]; // ninguém se repete: não há moda
        }

        // Usa os dados ordenados para pular números repetidos com facilidade
        int[] ordenados = getOrdenados();

        // 1ª volta: descobre QUANTAS modas existem (para criar o vetor do tamanho certo)
        int quantidade = 0;
        for (int i = 0; i < ordenados.length; i++) {
            boolean jaContado = i > 0 && ordenados[i] == ordenados[i - 1];
            if (!jaContado && frequenciaDe(ordenados[i]) == maior) {
                quantidade++;
            }
        }

        // 2ª volta: guarda as modas no vetor
        int[] modas = new int[quantidade];
        int posicao = 0;
        for (int i = 0; i < ordenados.length; i++) {
            boolean jaContado = i > 0 && ordenados[i] == ordenados[i - 1];
            if (!jaContado && frequenciaDe(ordenados[i]) == maior) {
                modas[posicao] = ordenados[i];
                posicao++;
            }
        }
        return modas;
    }
}
