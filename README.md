
## Gestão de Benefícios
- [📌 Introdução](#-introdução)
- [⚙️ Configuração de ambiente](#️-configuração-de-ambiente)
- [📦 Instalar o projeto](#-instalar-o-projeto)

## 📌 Introdução

O Gestão de Benefícios é um sistema robusto desenvolvido para controlar, manipular e realizar a transferência de valores entre diferentes tipos de benefícios. A plataforma foi desenhada para ser operada de forma centralizada pelo departamento de Recursos Humanos (RH), facilitando a distribuição e o ajuste de auxílios para os colaboradores (funcionários). Sua arquitetura flexível expande os limites do cenário corporativo tradicional, tornando o sistema perfeitamente adaptável para:Iniciativa Privada e Empresas Públicas: Gestão integrada de pacotes de benefícios e saldos de colaboradores.Governo e Esfera Pública: Controle centralizado, auditoria e distribuição de auxílios e benefícios sociais diretamente para a população.

## ⚙️ Configuração de Ambiente

Para configurar o ambiente do projeto Desafio precisa:
 - Download do [maven versão 3.9.12](https://maven.apache.org/docs/3.9.12/release-notes.html), deszipar numa pasta e depois copie o endereço:
    - Na variavel de ambiente do Windows(10,11) e crie uma variavel:
    ``` 
      Nome de variavel:  M2_HOME;
      Diretório: cole o endereço do maven;
      Adicione na path: %M2_HOME%/bin.
    ```
    -  Download do [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html), instalar e na pasta do java(C:\Program Files\Java\jdk-17) copie o endereço:
    ```
      Nome de variavel:  JAVA_HOME;
      Diretório: cole o endereço do Java;
      Adicione na path: %JAVA_HOME%/bin.
   ```

   - Download do [node v24.15.0 LTS](https://nodejs.org/en/download)
   

## 📦 Instalar o projeto

Depois de ir no repositório [desafio bip](https://github.com/DarleyGC2016/desafio_bip) do 😸Github:
   - Após criar uma pasta dentro dessa pasta abra o termminal e copie esse comando:

        ```
        git clone https://github.com/DarleyGC2016/desafio_bip.git
      ```
   - Abra a pasta do projeto e no terminal no vscode, use esse comando:
     - Este comando é backend:
     
     ```
        mvn clean package
     ```
     - No [frontend](frontend/beneficio-web) use esse comando:

    ```
     npm install
    ```

  [backend](backend-module\README.md)
  
