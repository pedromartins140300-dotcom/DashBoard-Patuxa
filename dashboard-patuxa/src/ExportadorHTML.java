import java.awt.Desktop;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * PATUXA — Liga o cálculo do Java ao dashboard HTML.
 *
 * 1. Grava os resultados calculados pela classe EstatisticaAtendimentos
 *    no arquivo "estatistica_java.js", ao lado do HTML.
 * 2. O HTML carrega esse arquivo e o JavaScript do dashboard mostra
 *    os números na aba "Estatística".
 * 3. Abre o HTML no navegador padrão.
 */
public class ExportadorHTML {

    public static final String NOME_HTML = "patuxa_dashboard_claude.html";
    public static final String NOME_JS = "estatistica_java.js";

    /**
     * Procura o HTML na pasta atual, em "src" e na pasta de cima
     * (cobre rodar pelo terminal e pelas IDEs mais comuns).
     */
    public static Path localizarHTML() {
        for (String pasta : new String[] {".", "src", ".."}) {
            Path caminho = Paths.get(pasta, NOME_HTML).toAbsolutePath().normalize();
            if (Files.exists(caminho)) {
                return caminho;
            }
        }
        return null;
    }

    /** Grava o arquivo de resultados ao lado do HTML e devolve o caminho dele. */
    public static Path exportar(EstatisticaAtendimentos estatistica, Path html) throws IOException {
        Path js = html.resolveSibling(NOME_JS);
        Files.write(js, gerarConteudo(estatistica).getBytes(StandardCharsets.UTF_8));
        return js;
    }

    /** Monta o conteúdo do arquivo .js com os dados e os resultados do Java. */
    private static String gerarConteudo(EstatisticaAtendimentos e) {
        String geradoEm = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        int[] modas = e.moda();
        int[] ordenados = e.getOrdenados();

        // Frequência de cada valor diferente, no formato [[valor, vezes], ...]
        String frequencia = "[";
        for (int i = 0; i < ordenados.length; i++) {
            if (i > 0 && ordenados[i] == ordenados[i - 1]) {
                continue; // esse valor já entrou na lista
            }
            if (!frequencia.equals("[")) {
                frequencia += ", ";
            }
            frequencia += "[" + ordenados[i] + ", " + e.frequenciaDe(ordenados[i]) + "]";
        }
        frequencia += "]";

        // Double.toString sempre usa ponto decimal, que é o formato que o JavaScript entende
        return "// Arquivo gerado automaticamente pela classe ExportadorHTML. Não edite à mão:\n"
             + "// altere os dados em Main.java e execute o programa de novo.\n"
             + "window.ESTATISTICA_JAVA = {\n"
             + "  geradoEm: \"" + geradoEm + "\",\n"
             + "  geradoEmMs: " + System.currentTimeMillis() + ",\n"
             + "  quantidade: " + e.getQuantidadeDias() + ",\n"
             + "  total: " + e.total() + ",\n"
             + "  media: " + Double.toString(e.media()) + ",\n"
             + "  mediana: " + Double.toString(e.mediana()) + ",\n"
             + "  moda: " + Arrays.toString(modas) + ",\n"
             + "  frequenciaModa: " + (modas.length == 0 ? 0 : e.frequenciaDe(modas[0])) + ",\n"
             + "  dados: " + Arrays.toString(e.getAtendimentosPorDia()) + ",\n"
             + "  ordenados: " + Arrays.toString(ordenados) + ",\n"
             + "  frequencia: " + frequencia + "\n"
             + "};\n";
    }

    /** Abre o HTML no navegador padrão; se não for possível, mostra o caminho. */
    public static void abrirNoNavegador(Path html) {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(html.toUri());
                System.out.println("Dashboard aberto no navegador.");
                return;
            }
        } catch (Exception ex) {
            // segue para a mensagem abaixo
        }
        System.out.println("Não foi possível abrir o navegador automaticamente.");
        System.out.println("Abra este arquivo manualmente: " + html);
    }
}
