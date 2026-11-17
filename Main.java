import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Usuario usuario = new Usuario("admin", "1234");
        Impressora impressora = new Impressora();
        boolean autenticado = false;

        for (int tentativa = 1; tentativa <= 3; tentativa++) {
            System.out.print("Usuário: ");
            String login = scanner.nextLine();

            System.out.print("Senha: ");
            String senha = scanner.nextLine();

            if (usuario.autenticar(login, senha)) {
                System.out.println("Acesso autorizado.");
                autenticado = true;
                break;
            }

            int restantes = 3 - tentativa;
            System.out.println("Dados incorretos. Tentativas restantes: " + restantes);
        }

        if (!autenticado) {
            System.out.println("Acesso bloqueado.");
            return;
        }

        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n=== MENU DA IMPRESSORA ===");
            System.out.println("1. Configurar conexão padrão (USB - Elgin i9)");
            System.out.println("2. Abrir conexão");
            System.out.println("3. Fechar conexão");
            System.out.println("4. Imprimir texto");
            System.out.println("5. Imprimir QR Code");
            System.out.println("6. Imprimir código de barras");
            System.out.println("7. Imprimir XML SAT");
            System.out.println("8. Imprimir XML de cancelamento SAT");
            System.out.println("9. Abrir gaveta");
            System.out.println("10. Sinal sonoro");
            System.out.println("11. Cortar papel");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
                continue;
            }

            switch (opcao) {
                case 0:
                    System.out.println("Programa encerrado.");
                    break;

                case 1:
                    impressora.configurar();
                    break;

                case 2:
                    impressora.abrirConexao();
                    break;

                case 3:
                    impressora.fecharConexao();
                    break;

                case 4:
                    if (!impressora.isConexaoAberta()) {
                        System.out.println("Conexão fechada. Abra a conexão antes.");
                        break;
                    }

                    System.out.print("Texto: ");
                    String texto = scanner.nextLine();
                    impressora.imprimirTexto(texto, 1, 4, 0);
                    break;

                case 5:
                    if (!impressora.isConexaoAberta()) {
                        System.out.println("Conexão fechada. Abra a conexão antes.");
                        break;
                    }

                    System.out.print("Conteúdo do QR Code: ");
                    String dadosQRCode = scanner.nextLine();
                    impressora.imprimirQRCode(dadosQRCode, 6, 4);
                    break;

                case 6:
                    if (!impressora.isConexaoAberta()) {
                        System.out.println("Conexão fechada. Abra a conexão antes.");
                        break;
                    }

                    System.out.print("Dados do código de barras: ");
                    String dadosCodigo = scanner.nextLine();
                    impressora.imprimirCodigoBarras(8, dadosCodigo, 100, 2, 3);
                    break;

                case 7:
                    if (!impressora.isConexaoAberta()) {
                        System.out.println("Conexão fechada. Abra a conexão antes.");
                        break;
                    }
                    impressora.imprimirXMLSAT("XMLSAT.xml", 0);
                    break;

                case 8:
                    if (!impressora.isConexaoAberta()) {
                        System.out.println("Conexão fechada. Abra a conexão antes.");
                        break;
                    }

                    try {
                        String assinatura = obterAssinaturaQRCode("CANC_SAT.xml");
                        if (assinatura.isEmpty()) {
                            System.out.println("Assinatura QRCode não encontrada em CANC_SAT.xml.");
                        } else {
                            impressora.imprimirXMLCancelamentoSAT(
                                    "CANC_SAT.xml", assinatura, 0
                            );
                        }
                    } catch (IOException e) {
                        System.out.println("Não foi possível ler CANC_SAT.xml: " + e.getMessage());
                    }
                    break;

                case 9:
                case 10:
                    if (opcao == 9) {
                        impressora.abrirGaveta();
                    } else {
                        impressora.sinalSonoro();
                    }
                    break;

                case 11:
                    impressora.cortarPapel();
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static String obterAssinaturaQRCode(String caminhoArquivo) throws IOException {
        String xml = new String(
                Files.readAllBytes(Paths.get(caminhoArquivo)),
                StandardCharsets.UTF_8
        );
        String tagInicio = "<assinaturaQRCODE>";
        String tagFim = "</assinaturaQRCODE>";

        int inicio = xml.indexOf(tagInicio);
        if (inicio == -1) {
            return "";
        }

        inicio += tagInicio.length();
        int fim = xml.indexOf(tagFim, inicio);
        if (fim == -1) {
            return "";
        }

        return xml.substring(inicio, fim).replaceAll("\\s", "");
    }
}