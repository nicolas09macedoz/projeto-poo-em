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
            System.out.println("1. Configurar conexão");
            System.out.println("2. Abrir conexão");
            System.out.println("3. Fechar conexão");
            System.out.println("4. Imprimir texto");
            System.out.println("5. Imprimir QR Code");
            System.out.println("6. Imprimir código de barras");
            System.out.println("7. Imprimir XML SAT");
            System.out.println("8. Imprimir XML de cancelamento SAT");
            System.out.println("9. Abrir gaveta");
            System.out.println("10. Sinal sonoro");
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
                    try {
                        System.out.print("Tipo (1 USB, 2 serial, 3 rede, 4 Bluetooth): ");
                        int tipo = Integer.parseInt(scanner.nextLine());

                        System.out.print("Modelo (i8 ou i9): ");
                        String modelo = scanner.nextLine();

                        System.out.print("Conexão (USB, COM2 ou IP): ");
                        String conexao = scanner.nextLine();

                        System.out.print("Parâmetro: ");
                        int parametro = Integer.parseInt(scanner.nextLine());

                        impressora.configurar(tipo, modelo, conexao, parametro);
                    } catch (NumberFormatException e) {
                        System.out.println("Tipo e parâmetro precisam ser números.");
                    }
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

                    try {
                        System.out.print("Texto: ");
                        String dados = scanner.nextLine();

                        System.out.print("Posição (0 esquerda, 1 centro, 2 direita): ");
                        int posicao = Integer.parseInt(scanner.nextLine());

                        System.out.print("Estilo (0, 1, 2, 4, 8 ou soma deles): ");
                        int estilo = Integer.parseInt(scanner.nextLine());

                        System.out.print("Tamanho (0 a 7): ");
                        int tamanho = Integer.parseInt(scanner.nextLine());

                        impressora.imprimirTexto(dados, posicao, estilo, tamanho);
                    } catch (NumberFormatException e) {
                        System.out.println("Posição, estilo e tamanho precisam ser números.");
                    }
                    break;

                case 5:
                    if (!impressora.isConexaoAberta()) {
                        System.out.println("Conexão fechada. Abra a conexão antes.");
                        break;
                    }

                    try {
                        System.out.print("Conteúdo do QR Code: ");
                        String dados = scanner.nextLine();

                        System.out.print("Tamanho (1 a 6): ");
                        int tamanho = Integer.parseInt(scanner.nextLine());

                        System.out.print("Nível de correção (1 a 4): ");
                        int nivelCorrecao = Integer.parseInt(scanner.nextLine());

                        impressora.imprimirQRCode(dados, tamanho, nivelCorrecao);
                    } catch (NumberFormatException e) {
                        System.out.println("Tamanho e nível de correção precisam ser números.");
                    }
                    break;

                case 6:
                    if (!impressora.isConexaoAberta()) {
                        System.out.println("Conexão fechada. Abra a conexão antes.");
                        break;
                    }

                    try {
                        System.out.print("Tipo (0 UPC-A, 2 EAN13, 3 EAN8, 4 CODE39, 8 CODE128): ");
                        int tipoCodigo = Integer.parseInt(scanner.nextLine());
                        while (tipoCodigo != 0 && tipoCodigo != 2 && tipoCodigo != 3
                                && tipoCodigo != 4 && tipoCodigo != 8) {
                            System.out.println("Tipo inválido. Digite 0, 2, 3, 4 ou 8.");
                            System.out.print("Tipo (0 UPC-A, 2 EAN13, 3 EAN8, 4 CODE39, 8 CODE128): ");
                            tipoCodigo = Integer.parseInt(scanner.nextLine());
                        }

                        System.out.print("Dados do código de barras: ");
                        String dadosCodigo = scanner.nextLine();

                        System.out.print("Altura (1 a 255): ");
                        int altura = Integer.parseInt(scanner.nextLine());
                        while (altura < 1 || altura > 255) {
                            System.out.println("Altura inválida. Digite um valor entre 1 e 255.");
                            System.out.print("Altura (1 a 255): ");
                            altura = Integer.parseInt(scanner.nextLine());
                        }

                        System.out.print("Largura (1 a 6): ");
                        int largura = Integer.parseInt(scanner.nextLine());
                        while (largura < 1 || largura > 6) {
                            System.out.println("Largura inválida. Digite um valor entre 1 e 6.");
                            System.out.print("Largura (1 a 6): ");
                            largura = Integer.parseInt(scanner.nextLine());
                        }

                        System.out.print("HRI (1 acima, 2 abaixo, 3 ambos, 4 nenhum): ");
                        int hri = Integer.parseInt(scanner.nextLine());
                        while (hri < 1 || hri > 4) {
                            System.out.println("HRI inválido. Digite 1, 2, 3 ou 4.");
                            System.out.print("HRI (1 acima, 2 abaixo, 3 ambos, 4 nenhum): ");
                            hri = Integer.parseInt(scanner.nextLine());
                        }

                        impressora.imprimirCodigoBarras(
                                tipoCodigo, dadosCodigo, altura, largura, hri
                        );
                    } catch (NumberFormatException e) {
                        System.out.println("Tipo, altura, largura e HRI precisam ser números.");
                    }
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