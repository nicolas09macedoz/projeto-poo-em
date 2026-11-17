import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Impressora {
    private int tipo;
    private String modelo;
    private String conexao;
    private int parametro;
    private boolean configurada;
    private boolean conexaoAberta;

    public void configurar() {
        if (conexaoAberta) {
            System.out.println("Feche a conexão antes de reconfigurar.");
            return;
        }

        tipo = 1;
        modelo = "i9";
        conexao = "USB";
        parametro = 0;
        configurada = true;

        System.out.println("Conexão USB configurada para impressora Elgin i9.");
    }

    public void abrirConexao() {
        if (!configurada) {
            System.out.println("Configure a conexão antes de abri-la.");
            return;
        }

        try {
            int retorno = ImpressoraDLL.INSTANCE.AbreConexaoImpressora(
                    tipo, modelo, conexao, parametro
            );

            if (retorno == 0) {
                conexaoAberta = true;
                System.out.println("Conexão aberta.");
            } else {
                conexaoAberta = false;
                System.out.println("Erro ao abrir conexão. Código: " + retorno);
            }
        } catch (UnsatisfiedLinkError e) {
            conexaoAberta = false;
            System.out.println("Não foi possível carregar a DLL: " + e.getMessage());
        }
    }

    public void fecharConexao() {
        if (!conexaoAberta) {
            System.out.println("A conexão já está fechada.");
            return;
        }

        try {
            int retorno = ImpressoraDLL.INSTANCE.FechaConexaoImpressora();

            if (retorno == 0) {
                conexaoAberta = false;
                System.out.println("Conexão fechada.");
            } else {
                System.out.println("Erro ao fechar conexão. Código: " + retorno);
            }
        } catch (UnsatisfiedLinkError e) {
            System.out.println("Não foi possível carregar a DLL: " + e.getMessage());
        }
    }

    public boolean isConexaoAberta() {
        return conexaoAberta;
    }

    public void imprimirTexto(String dados, int posicao, int estilo, int tamanho) {
        if (!conexaoAberta) {
            System.out.println("Conexão fechada. Abra a conexão antes.");
            return;
        }

        try {
            int retorno = ImpressoraDLL.INSTANCE.ImpressaoTexto(
                    dados, posicao, estilo, tamanho
            );

            if (retorno < 0) {
                System.out.println("Erro ao imprimir texto. Código: " + retorno);
            } else {
                System.out.println("Texto enviado. Dados enviados: " + retorno);
            }
        } catch (UnsatisfiedLinkError e) {
            System.out.println("Não foi possível carregar a DLL: " + e.getMessage());
        }
    }

    public void imprimirQRCode(String dados, int tamanho, int nivelCorrecao) {
        if (!conexaoAberta) {
            System.out.println("Conexão fechada. Abra a conexão antes.");
            return;
        }

        try {
            int retorno = ImpressoraDLL.INSTANCE.ImpressaoQRCode(
                    dados, tamanho, nivelCorrecao
            );

            if (retorno == 0) {
                System.out.println("QR Code enviado para impressão.");
            } else {
                System.out.println("Erro ao imprimir QR Code. Código: " + retorno);
            }
        } catch (UnsatisfiedLinkError e) {
            System.out.println("Não foi possível carregar a DLL: " + e.getMessage());
        }
    }

    public void imprimirCodigoBarras(
            int tipo, String dados, int altura, int largura, int hri
    ) {
        if (!conexaoAberta) {
            System.out.println("Conexão fechada. Abra a conexão antes.");
            return;
        }

        try {
            int retorno = ImpressoraDLL.INSTANCE.ImpressaoCodigoBarras(
                    tipo, dados, altura, largura, hri
            );

            if (retorno == 0) {
                System.out.println("Código de barras enviado para impressão.");
            } else {
                System.out.println("Erro ao imprimir código de barras. Código: " + retorno);
            }
        } catch (UnsatisfiedLinkError e) {
            System.out.println("Não foi possível carregar a DLL: " + e.getMessage());
        }
    }

    public void imprimirXMLSAT(String caminho, int parametro) {
        if (!conexaoAberta) {
            System.out.println("Conexão fechada. Abra a conexão antes.");
            return;
        }

        try {
            String dados = new String(
                    Files.readAllBytes(Paths.get(caminho)),
                    StandardCharsets.UTF_8
            );
            int retorno = ImpressoraDLL.INSTANCE.ImprimeXMLSAT(dados, parametro);

            if (retorno == 0) {
                System.out.println("XML SAT enviado para impressão.");
            } else {
                System.out.println("Erro ao imprimir XML SAT. Código: " + retorno);
            }
        } catch (IOException e) {
            System.out.println("Não foi possível ler o arquivo XML: " + e.getMessage());
        } catch (UnsatisfiedLinkError e) {
            System.out.println("Não foi possível carregar a DLL: " + e.getMessage());
        }
    }

    public void imprimirXMLCancelamentoSAT(
            String caminho, String assQRCode, int parametro
    ) {
        if (!conexaoAberta) {
            System.out.println("Conexão fechada. Abra a conexão antes.");
            return;
        }

        try {
            String dados = new String(
                    Files.readAllBytes(Paths.get(caminho)),
                    StandardCharsets.UTF_8
            );
            int retorno = ImpressoraDLL.INSTANCE.ImprimeXMLCancelamentoSAT(
                    dados, assQRCode, parametro
            );

            if (retorno == 0) {
                System.out.println("XML de cancelamento SAT enviado para impressão.");
            } else {
                System.out.println("Erro ao imprimir cancelamento SAT. Código: " + retorno);
            }
        } catch (IOException e) {
            System.out.println("Não foi possível ler o arquivo XML: " + e.getMessage());
        } catch (UnsatisfiedLinkError e) {
            System.out.println("Não foi possível carregar a DLL: " + e.getMessage());
        }
    }

    public void abrirGaveta() {
        if (!conexaoAberta) {
            System.out.println("Conexão fechada. Abra a conexão antes.");
            return;
        }

        try {
            // Pino 0 e tempos 1 são valores básicos aceitos pela biblioteca.
            int retorno = ImpressoraDLL.INSTANCE.AbreGaveta(0, 1, 1);

            if (retorno == 0) {
                System.out.println("Comando para abrir a gaveta enviado.");
            } else {
                System.out.println("Erro ao abrir a gaveta. Código: " + retorno);
            }
        } catch (UnsatisfiedLinkError e) {
            System.out.println("Não foi possível carregar a DLL: " + e.getMessage());
        }
    }

    public void sinalSonoro() {
        if (!conexaoAberta) {
            System.out.println("Conexão fechada. Abra a conexão antes.");
            return;
        }

        try {
            // 1 sinal com tempos válidos para a impressora.
            int retorno = ImpressoraDLL.INSTANCE.SinalSonoro(1, 1, 1);

            if (retorno == 0) {
                System.out.println("Sinal sonoro enviado.");
            } else {
                System.out.println("Erro ao emitir sinal sonoro. Código: " + retorno);
            }
        } catch (UnsatisfiedLinkError e) {
            System.out.println("Não foi possível carregar a DLL: " + e.getMessage());
        }
    }

    public void cortarPapel() {
        if (!conexaoAberta) {
            System.out.println("Conexão fechada. Abra a conexão antes.");
            return;
        }

        try {
            int retorno = ImpressoraDLL.INSTANCE.Corte(5);

            if (retorno == 0) {
                System.out.println("Papel cortado.");
            } else {
                System.out.println("Erro ao cortar o papel. Código: " + retorno);
            }
        } catch (UnsatisfiedLinkError e) {
            System.out.println("Não foi possível carregar a DLL: " + e.getMessage());
        }
    }

}