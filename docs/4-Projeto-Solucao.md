## 4. Projeto da Solução

<span style="color:red">Pré-requisitos: <a href="03-Modelagem do Processo de Negocio.md"> Modelagem do Processo de Negocio</a></span>

## 4.1. Arquitetura da solução
A arquitetura do FluAI foi desenhada para garantir escalabilidade, performance e uma experiência fluida ao usuário. A solução segue uma abordagem modular, dividida em três camadas principais: aplicativo móvel (frontend), servidor de aplicação (backend) e bancos de dados (armazenamento).

#### Componentes da Arquitetura
Aplicativo Móvel (Frontend – Kotlin)
Desenvolvido em Kotlin, o aplicativo é responsável por toda a interação com o usuário. Ele oferece uma interface moderna, responsiva e intuitiva, permitindo que o usuário realize o teste de nivelamento, acesse os exercícios personalizados, visualize seu progresso e receba feedback em tempo real.

#### Servidor de Aplicação (Backend – Python)
O backend, desenvolvido em Python, é responsável pelo processamento da lógica de negócio, análise de desempenho dos usuários e adaptação dos conteúdos com base nos resultados. Ele também integra as bibliotecas de Inteligência Artificial para ajustar os exercícios e enviar feedbacks personalizados ao app.

#### Banco de Dados

Firebase: Utilizado para autenticação de usuários.

PostgreSQL: Gerencia dados relacionais como histórico de desempenho, respostas dos testes e progresso individual, com alta confiabilidade e segurança.

🔁Fluxo de Funcionamento
- O usuário acessa o app e realiza o teste de nivelamento.

- As respostas são enviadas ao backend em Python, que processa os dados e consulta o modelo de IA.

- O backend retorna os exercícios adaptados com base no desempenho do usuário.

- As informações são armazenadas.

- O app exibe o conteúdo personalizado.
 
![image](https://github.com/user-attachments/assets/ac068762-39ce-4fa2-a5cc-af50764db57d)

 

### 4.2. Protótipos de telas

Com o objetivo de garantir uma experiência de usuário intuitiva, envolvente e personalizada, foram desenvolvidos protótipos de interface para o aplicativo FluAI. Esses protótipos representam as principais telas do sistema e foram criados com base nos requisitos funcionais, histórias de usuário e nas boas práticas de design de aplicativos.

As telas foram pensadas para serem fáceis de usar, visualmente agradáveis e acessíveis, criando um ambiente leve e motivador para quem está aprendendo inglês. Para deixar tudo mais interativo e interessante, usamos elementos de gamificação e feedback visual, que ajudam o usuário a se manter engajado e entender melhor seu progresso no app.

### Login e Cadastro
![image](https://github.com/user-attachments/assets/8a5b7c93-f020-48e5-a6db-cf3361b1c171)

### Esqueceu/Redefinir senha
![image](https://github.com/user-attachments/assets/5c32af54-a29c-4a82-8024-9d2431ca9d5f)

### Conta
![image](https://github.com/user-attachments/assets/769fe812-0293-493b-b341-dfcef094e5fb)

### Telas do APP
![image](https://github.com/user-attachments/assets/725cd3af-69e7-4d88-8563-dc167ba70eee)
![image](https://github.com/user-attachments/assets/f47a8600-ff0a-42c4-811b-7f4f7bdde4f4)
![image](https://github.com/user-attachments/assets/af356cbf-d84b-4293-90cd-708bb86db90f)




## Diagrama de Classes

O diagrama de classes ilustra graficamente como será a estrutura do software, e como cada uma das classes da sua estrutura estarão interligadas. Essas classes servem de modelo para materializar os objetos que executarão na memória.

As referências abaixo irão auxiliá-lo na geração do artefato “Diagrama de Classes”.

> - [Diagramas de Classes - Documentação da IBM](https://www.ibm.com/docs/pt-br/rational-soft-arch/9.6.1?topic=diagrams-class)
> - [O que é um diagrama de classe UML? | Lucidchart](https://www.lucidchart.com/pages/pt/o-que-e-diagrama-de-classe-uml)

## Modelo ER

O Modelo ER representa através de um diagrama como as entidades (coisas, objetos) se relacionam entre si na aplicação interativa.]

As referências abaixo irão auxiliá-lo na geração do artefato “Modelo ER”.

> - [Como fazer um diagrama entidade relacionamento | Lucidchart](https://www.lucidchart.com/pages/pt/como-fazer-um-diagrama-entidade-relacionamento)


### 4.3. Modelo de dados

O desenvolvimento da solução proposta requer a existência de bases de dados que permitam efetuar os cadastros de dados e controles associados aos processos identificados, assim como recuperações.
Utilizando a notação do DER (Diagrama Entidade e Relacionamento), elaborem um modelo, na ferramenta visual indicada na disciplina, que contemple todas as entidades e atributos associados às atividades dos processos identificados. Deve ser gerado um único DER que suporte todos os processos escolhidos, visando, assim, uma base de dados integrada. O modelo deve contemplar, também, o controle de acesso de usuários (partes interessadas dos processos) de acordo com os papéis definidos nos modelos do processo de negócio.
_Apresente o modelo de dados por meio de um modelo relacional que contemple todos os conceitos e atributos apresentados na modelagem dos processos._

#### 4.3.1 Modelo ER

O Modelo ER representa através de um diagrama como as entidades (coisas, objetos) se relacionam entre si na aplicação interativa.]

As referências abaixo irão auxiliá-lo na geração do artefato “Modelo ER”.

> - [Como fazer um diagrama entidade relacionamento | Lucidchart](https://www.lucidchart.com/pages/pt/como-fazer-um-diagrama-entidade-relacionamento)

#### 4.3.2 Esquema Relacional

O Esquema Relacional corresponde à representação dos dados em tabelas juntamente com as restrições de integridade e chave primária.
 
As referências abaixo irão auxiliá-lo na geração do artefato “Esquema Relacional”.

> - [Criando um modelo relacional - Documentação da IBM](https://www.ibm.com/docs/pt-br/cognos-analytics/10.2.2?topic=designer-creating-relational-model)

![Exemplo de um modelo relacional](images/modeloRelacional.png "Exemplo de Modelo Relacional.")
---


#### 4.3.3 Modelo Físico

Insira aqui o script de criação das tabelas do banco de dados.

> **OBS:** Se o aluno utilizar BD NoSQL, ele derá incluir o script aqui também. 

Veja um exemplo:

<code>

 -- Criação da tabela Médico
CREATE TABLE Medico (
    MedCodigo INTEGER PRIMARY KEY,
    MedNome VARCHAR(100)
);


-- Criação da tabela Paciente
CREATE TABLE Paciente (
    PacCodigo INTEGER PRIMARY KEY,
    PacNome VARCHAR(100)
);

-- Criação da tabela Consulta
CREATE TABLE Consulta (
    ConCodigo INTEGER PRIMARY KEY,
    MedCodigo INTEGER,
    PacCodigo INTEGER,
    Data DATE,
    FOREIGN KEY (MedCodigo) REFERENCES Medico(MedCodigo),
    FOREIGN KEY (PacCodigo) REFERENCES Paciente(PacCodigo)
);

-- Criação da tabela Medicamento
CREATE TABLE Medicamento (
    MdcCodigo INTEGER PRIMARY KEY,
    MdcNome VARCHAR(100)
);

-- Criação da tabela Prescricao
CREATE TABLE Prescricao (
    ConCodigo INTEGER,
    MdcCodigo INTEGER,
    Posologia VARCHAR(200),
    PRIMARY KEY (ConCodigo, MdcCodigo),
    FOREIGN KEY (ConCodigo) REFERENCES Consulta(ConCodigo),
    FOREIGN KEY (MdcCodigo) REFERENCES Medicamento(MdcCodigo)
);

</code>

Este script deverá ser incluído em um arquivo .sql na pasta src\bd.




### 4.4. Tecnologias

_Descreva qual(is) tecnologias você vai usar para resolver o seu problema, ou seja, implementar a sua solução. Liste todas as tecnologias envolvidas, linguagens a serem utilizadas, serviços web, frameworks, bibliotecas, IDEs de desenvolvimento, e ferramentas._

Apresente também uma figura explicando como as tecnologias estão relacionadas ou como uma interação do usuário com o sistema vai ser conduzida, por onde ela passa até retornar uma resposta ao usuário.


| **Dimensão**   | **Tecnologia**  |
| ---            | ---             |
| SGBD           | Firebase E Postgre           |
| Front end      | Kotlin   |
| Back end       | Pyhton|
| Deploy         | Github Pages    |
| IA             | Llama     |
