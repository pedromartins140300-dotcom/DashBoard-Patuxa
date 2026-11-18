import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * PATUXA — Executa o cálculo estatístico dos atendimentos do mês,
 * envia o resultado para o dashboard HTML (arquivo estatistica_java.js)
 * e abre o dashboard no navegador.
 *
 * Para atualizar: altere os números de "atendimentosDoMes" e execute de novo.
 */
public class Main {

    public static void main(String[] args) {
        // Atendimentos por dia (dia 1 ao dia 30 do mês) — total de 742
        int[] atendimentosDoMes = {
            24, 26, 25, 18, 22, 24, 29,
            24, 31, 28, 16, 20, 24, 27,
            24, 30, 28, 15, 22, 25, 28,
            24, 31, 29, 17, 24, 26, 26,
            24, 31
        };

        EstatisticaAtendimentos estatistica = new EstatisticaAtendimentos(atendimentosDoMes);

        // 1) Resultado no console
        System.out.println("==============================================");
        System.out.println(" PATUXA — Estatística dos atendimentos do mês");
        System.out.println("==============================================");
        System.out.println("Dias analisados: " + estatistica.getQuantidadeDias());
        System.out.println("Total de atendimentos: " + estatistica.total());
        System.out.println("Dados (por dia): " + Arrays.toString(estatistica.getAtendimentosPorDia()));
        System.out.println("Dados ordenados: " + Arrays.toString(estatistica.getOrdenados()));
        System.out.println("----------------------------------------------");

        System.out.printf("Média:   %.2f atendimentos por dia%n", estatistica.media());
        System.out.printf("Mediana: %.1f atendimentos por dia%n", estatistica.mediana());

        int[] modas = estatistica.moda();
        if (modas.length == 0) {
            System.out.println("Moda:    não há (nenhum valor se repete)");
        } else {
            System.out.println("Moda:    " + Arrays.toString(modas) + " atendimentos por dia (aparece "
                    + estatistica.frequenciaDe(modas[0]) + " vezes)");
        }
        System.out.println("==============================================");

        // 2) Resultado no dashboard HTML (o JavaScript do dashboard exibe)
        Path html = ExportadorHTML.localizarHTML();
        if (html == null) {
            System.out.println("Não encontrei o arquivo " + ExportadorHTML.NOME_HTML + ".");
            System.out.println("Coloque o HTML na mesma pasta dos arquivos .java e execute de novo.");
            return;
        }
        try {
            Path js = ExportadorHTML.exportar(estatistica, html);
            System.out.println("Resultados gravados em: " + js);
            ExportadorHTML.abrirNoNavegador(html);
        } catch (IOException ex) {
            System.out.println("Erro ao gravar os resultados para o HTML: " + ex.getMessage());
        }
    }
}
