🖨️ Sistema de Controle de Impressora Elgin

📘- Sobre o Projeto
Este projeto foi desenvolvido por Matheus Teixeira, Matheus Klayver, Nicolas Henrique, Yago Jesus com o objetivo de criar um sistema em Java para controlar uma impressora térmica Elgin. O sistema utiliza a biblioteca JNA para realizar a comunicação com a DLL da impressora, permitindo executar diferentes funções de impressão. O projeto foi desenvolvido para a disciplina de Programação Orientada a Objetos, aplicando conceitos de Java e integração com dispositivos externos.

-🧰 Tecnologias Utilizadas
Java – Linguagem utilizada no desenvolvimento.
IntelliJ IDEA – IDE utilizada para programar.
JNA 5.15.0 – Biblioteca utilizada para comunicação com a DLL.
DLL E1_Impressora01 – Biblioteca de comunicação da Elgin.
Impressora Elgin i8/i9 – Impressora utilizada para os testes.

-📁 Estrutura do Projeto
/src Main.java → Inicia o programa. Login.java → Realiza o controle de login. Menu.java → Exibe as opções do sistema. Impressora.java → Responsável pela comunicação com a impressora.

/Bibliotecas E1 Impressora → DLLs utilizadas pela impressora. /libs Java → Biblioteca jna-5.15.0.jar.

-⚙️ Instalação e Execução
Baixar o projeto

Acesse o repositório no GitHub.

Clique em Code → Download ZIP.

Extraia os arquivos em uma pasta.

Abrir no IntelliJ IDEA

Abra o IntelliJ IDEA.

Clique em File → Open e selecione a pasta do projeto.

Verifique se o "jna-5.15.0.jar" está adicionado nas bibliotecas do projeto.

Coloque as DLLs da Elgin na pasta correta.

Conecte e ligue a impressora.

Abra a classe "Main.java" e execute o programa.

-🔐 Login
O sistema possui uma tela de login para controlar o acesso ao menu principal.
Usuário: admin
Senha: 1234
Tentativas permitidas: 3

-📋 Funcionalidades do Menu
Opção| O que faz 1| Realiza a impressão de texto. 2| Imprime informações formatadas. 3| Realiza outras funções disponíveis na impressora. 4| Encerra o programa.

-🧪 Testes Realizados
Teste de login com usuário e senha corretos.
Teste de login com senha incorreta.
Teste do limite de tentativas de login.
Teste de comunicação com a impressora.
Teste das funções de impressão disponíveis no sistema.


-📚 Documentação Adicional
"Manual da impressora na pasta docs do projeto"
"Documentação do SDK E1" (https://github.com/ElginDeveloperCommunity/Impressoras)



-👥 Autores
Matheus Klayver
Matheus Teixeira
Nicolas Henrique
Yago Jesus