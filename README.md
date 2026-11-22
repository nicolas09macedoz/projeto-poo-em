# 🖨️ Sistema de Controle de Impressora Elgin
---

## 📘 Sobre o Projeto

Desenvolvido por Matheus Teixeira, Matheus Klayver, Nicolas Henrique e Yago Jesus para a disciplina de Programação Orientada a Objetos, este projeto consiste em um sistema em Java voltado ao controle de uma impressora térmica Elgin. Por meio da biblioteca JNA, a aplicação estabelece comunicação com a DLL do equipamento para executar diversas funcionalidades de impressão, colocando em prática os conceitos da linguagem e a integração com dispositivos externos.

---

## 🧰 Tecnologias Utilizadas

- **Java 17**
- **IntelliJ IDEA**
- **JNA 5.15.0** (comunicação com a DLL)
- **DLL E1_Impressora01** (SDK da Elgin)
- **Impressora Elgin i9**

---

## 📁 Estrutura do Projeto

*(Liste suas pastas e classes e diga o que cada uma faz.)*

```
/src                      → Código-fonte
Main.java                → Inicia o programa
(suas classes aqui)      → Impressora/Informações da mesma, Usuario/Guarda as informações de login, Main/Rodar o codigo
/Bibliotecas E1 Impressora → DLLs da Elgin (x64 e x86)
/libs Java                → jna-5.15.0.jar
```

---

## ⚙️ Instalação e Execução



### 1. Baixar o projeto
1. Acesse o repositório no GitHub.
2. Clique em **Code → Download ZIP**.
3. Extraia o arquivo.

### 2. Abrir no IntelliJ IDEA
1. **File → Open** e escolha a pasta extraída.
2. Em **File → Project Structure → Libraries**, confirme que o `jna-5.15.0.jar` está adicionado.
3. Conecte e ligue a impressora.
4. Abra `Main.java` e clique em ▶️ **Run**.

---

## 🔐 Login

Usuario:admin
Senha:1234
Tentativas:3

---

## 📋 Funcionalidades do Menu

| Opção | O que faz |
|---|---|
| 1 | *Configurar conexão padrão* |
| 2 | *Abre a conexão com a impressora* |
| 3 | *Fecha a conexão com a impressora* |
| 4 | *Imprimir texto* |
| 5 | *Imprimir o QR Code* |
| 6 | *Imprimir código de barras* |
| 7 | *Imprimir xML SAT* |
| 8 | *Imprimir XML de cancelamento SAT* |
| 9 | *Abrir a gaveta* |
| 10 | *Sinal Sonoro* |
| 11 | *Cortar papel* |
| 0 | *Sair* |

---

## 🧪 Testes Realizados

*Teste de login com usuário e senha corretos. Teste de login com senha incorreta. Teste do limite de tentativas de login. Teste de comunicação com a impressora. Teste das funções de impressão disponíveis no sistema.*

---

## 📚 Documentação Adicional

- Manual da impressora Elgin esta na pasta docs do projeto
- Documentação do SDK E1_Impressora (https://github.com/ElginDeveloperCommunity/Impressoras)

---

## 👥 Autores

- Matheus Klayver
- Matheus Teixeira
- Nicolas Henrique
- Yago Jesus
