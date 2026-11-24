import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class Impressora {
    private static final int TIPO = 1;
    private static final String MODELO = "i9";
    private static final String CONEXAO = "USB";
    private static final int PARAMETRO = 0;
    private boolean conexaoAberta = false;

    private static final String ASS_QRCODE_CANCELAMENTO = "Q5DLkpdRijIRGY6YSSNsTWK1TztHL1vD0V1Jc4spo/CEUqICEb9SFy82ym8EhBRZjbh3btsZhF+sjHqEMR159i4agru9x6KsepK/q0E2e5xlU5cv3m1woYfgHyOkWDNcSdMsS6bBh2Bpq6s89yJ9Q6qh/J8YHi306ce9Tqb/drKvN2XdE5noRSS32TAWuaQEVd7u+TrvXlOQsE3fHR1D5f1saUwQLPSdIv01NF6Ny7jZwjCwv1uNDgGZONJdlTJ6p0ccqnZvuE70aHOI09elpjEO6Cd+orI7XHHrFCwhFhAcbalc+ZfO5b/+vkyAHS6CYVFCDtYR9Hi5qgdk31v23w==";

    public boolean verificarConexao() {
        if (!conexaoAberta) {
            System.out.println("Conexão fechada. Abra a conexão antes.");
        }
        return conexaoAberta;
    }

    private boolean conferir(int retorno, String operacao) {
        if (retorno == 0) {
            System.out.println(operacao + ": sucesso.");
            return true;
        }
        System.out.println("Erro em " + operacao + ". Código: " + retorno);
        return false;
    }

    public void configurarConexao() {
        System.out.println("Conexão USB configurada para impressora Elgin i9.");
    }

    public void abrirConexao() {
        if (conexaoAberta) {
            System.out.println("A conexão já está aberta.");
            return;
        }
        try {
            int retorno = ImpressoraDLL.INSTANCE.AbreConexaoImpressora(
                    TIPO, MODELO, CONEXAO, PARAMETRO
            );
            conexaoAberta = conferir(retorno, "Abrir conexão");
        } catch (LinkageError e) {
            System.out.println("Não foi possível carregar a DLL: " + e.getMessage());
        }
    }

    public void fecharConexao() {
        if (!conexaoAberta) {
            System.out.println("A conexão já está fechada.");
            return;
        }
        int retorno = ImpressoraDLL.INSTANCE.FechaConexaoImpressora();
        if (conferir(retorno, "Fechar conexão")) {
            conexaoAberta = false;
        }
    }

    public void imprimirTexto(String texto, int copias) {
        for (int i = 1; i <= copias; i++) {
            int retorno = ImpressoraDLL.INSTANCE.ImpressaoTexto(texto + "\n", 1, 0, 0);
            if (retorno < 0) {
                System.out.println("Erro ao imprimir a cópia " + i + ". Código: " + retorno);
                return;
            }
            finalizarImpressao();
        }
        System.out.println(copias + " cópia(s) impressa(s).");
    }

    public void imprimirQRCode(String dados, int tamanho) {
        int retorno = ImpressoraDLL.INSTANCE.ImpressaoQRCode(dados, tamanho, 2);
        if (conferir(retorno, "QR Code")) {
            finalizarImpressao();
        }
    }

    public void imprimirCodigoBarras(int tipoCodigo, String dados) {
        if (tipoCodigo == 8 && !dados.startsWith("{")) {
            dados = "{A" + dados;
        }
        int retorno = ImpressoraDLL.INSTANCE.ImpressaoCodigoBarras(
                tipoCodigo, dados, 100, 2, 3
        );
        if (conferir(retorno, "Código de barras")) {
            finalizarImpressao();
        }
    }

    public void imprimirXmlSat(String arquivo) {
        try {
            String conteudo = Files.readString(Path.of(arquivo));
            int retorno = ImpressoraDLL.INSTANCE.ImprimeXMLSAT(conteudo, 0);
            if (conferir(retorno, "Imprimir XML SAT")) {
                finalizarImpressao();
            }
        } catch (NoSuchFileException e) {
            System.out.println("Arquivo não encontrado: " + arquivo);
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    public void imprimirXmlCancelamento(String arquivo) {
        try {
            String xml = Files.readString(Path.of(arquivo));
            int retorno = ImpressoraDLL.INSTANCE.ImprimeXMLCancelamentoSAT(
                    xml, ASS_QRCODE_CANCELAMENTO, 0
            );
            if (conferir(retorno, "Imprimir cancelamento SAT")) {
                finalizarImpressao();
            }
        } catch (NoSuchFileException e) {
            System.out.println("Arquivo não encontrado: " + arquivo);
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    public void abrirGaveta() {
        if (!verificarConexao()) {
            return;
        }
        try {
            int retorno = ImpressoraDLL.INSTANCE.AbreGaveta(0, 5, 10);
            conferir(retorno, "Abrir gaveta");
        } catch (LinkageError e) {
            System.out.println("Não foi possível carregar a DLL: " + e.getMessage());
        }
    }

    public void sinalSonoro(int quantidade) {
        int retorno = ImpressoraDLL.INSTANCE.SinalSonoro(quantidade, 50, 5);
        conferir(retorno, "Sinal sonoro");
    }

    public void avancarPapel(int linhas) {
        int retorno = ImpressoraDLL.INSTANCE.AvancaPapel(linhas);
        conferir(retorno, "Avançar papel");
    }

    public void verificarPapel() {
        int retorno = ImpressoraDLL.INSTANCE.StatusImpressora(3);
        switch (retorno) {
            case 5 -> System.out.println("Papel OK.");
            case 6 -> System.out.println("Papel acabando.");
            case 7 -> System.out.println("Sem papel!");
            default -> System.out.println("Não foi possível ler o status. Código: " + retorno);
        }
    }

    private void finalizarImpressao() {
        int retornoAvanco = ImpressoraDLL.INSTANCE.AvancaPapel(1);
        if (retornoAvanco != 0) {
            System.out.println("Erro ao avançar o papel. Código: " + retornoAvanco);
        }

        int retornoCorte = ImpressoraDLL.INSTANCE.Corte(5);
        if (retornoCorte == 0) {
            System.out.println("Papel cortado.");
        } else {
            System.out.println("Erro ao cortar o papel. Código: " + retornoCorte);
        }
    }
}
