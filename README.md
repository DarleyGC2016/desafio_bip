
## Gestão de Benefícios

- [📌 Introdução](#-introdução)
- [🏗️ Desrição do desafio](#️-descrição-do-desafio)
- [🛠️ Instalações para o desafio](#️-instalações-para-o-desafio)
- [⚙️ Configuração de ambiente](#️-configuração-de-ambiente)
- [☕ Modulos do projeto](#-modulos-do-projeto)
s

## 📌 Introdução

O Gestão de Benefícios é um sistema robusto desenvolvido para controlar, manipular e realizar a transferência de valores entre diferentes tipos de benefícios. A plataforma foi desenhada para ser operada de forma centralizada pelo departamento de Recursos Humanos (RH), facilitando a distribuição e o ajuste de auxílios para os colaboradores (funcionários). Sua arquitetura flexível expande os limites do cenário corporativo tradicional, tornando o sistema perfeitamente adaptável para:Iniciativa Privada e Empresas Públicas: Gestão integrada de pacotes de benefícios e saldos de colaboradores.Governo e Esfera Pública: Controle centralizado, auditoria e distribuição de auxílios e benefícios sociais diretamente para a população.


## 🏗️ Descrição do Desafio

Essa é descrição do desafio [Clique aqui](docs/README.md).


## ⚙️ Configuração de Ambiente

Para configurar o ambiente no Windoows(10,11) para o Desafio precisa:
 - Para o **Maven** copie o endereço da pasta, onde foi dezipado  e Crie uma variavel:
    ``` 
      Nome de variavel:  M2_HOME;
      Diretório: cole o endereço do maven;
      Adicione na path: %M2_HOME%/bin.
    ```
 - Para o **Java** copie este endereço(C:\Program Files\Java\jdk-17) e crie uma variavel de ambiente:
    ```
      Nome de variavel:  JAVA_HOME;
      Diretório: cole o endereço do Java;
      Adicione na path: %JAVA_HOME%/bin.
   ```
No repositório deste desafioo no **Github**, faça um clone na pasta que você criou e use comando:
```
git clone https://github.com/DarleyGC2016/desafio_bip.git
```

## 🛠️ Instalações para o Desafio

Antes de clonar o projeto(Desafio) do repositorio
 - Download do [maven versão 3.9.12](https://maven.apache.org/docs/3.9.12/release-notes.html), deszipar numa pasta;
 - Download do [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html), instale e o Java;
 - Download do [node v24.15.0 LTS](https://nodejs.org/en/download) e instalar;
 - Depois de instalar o node. Instale o Angular CLI, use esse comando:
    ```
    npm install -g @angular/cli@21.2.7 
    ```

Após que clonar o desafio:
   - Abra a pasta do projeto e no terminal no vscode, use esse comando:
     - Este comando é para o backend:
     
     ```
        mvn clean package
     ```

     - No [frontend](frontend/beneficio-web), no terminal use esse comando:

     ```
      cd .\frontend\beneficio-web\ ou cd frontend\beneficio-web\'  
     ```
     depois:

     ```
     npm install
     ```

## ☕ Modulos do projeto

Neste projeto existes dois modulos um [backend-module](backend-module/README.md) e outro é o [ejb-module](ejb-module/README.md)

```
  <modules>
    <module>ejb-module</module>
    <module>backend-module</module>
  </modules>
```
  
