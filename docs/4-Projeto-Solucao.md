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

O diagrama que representa as classes da aplicação é bem simples. Teremos os usuários, que farão uso do aplicativo, e estes terão acesso às atividades geradas por IA.

Será necessário apenas realizar uma verificação de nível, garantindo que os usuários avancem de forma condizente com o conhecimento que já possuem.

![image](https://github.com/user-attachments/assets/2ae611b8-4761-4005-b115-9f05abb868a4)



## Modelo ER

### 4.3. Modelo de dados

Projetamos nossos dados para atender às necessidades da aplicação. No nosso caso, não foi necessário criar muitas tabelas nem estabelecer relacionamentos complexos, já que a complexidade da aplicação está na forma como a dinâmica de ensino será implementada.

#### 4.3.1 Modelo ER

![image](https://github.com/user-attachments/assets/502c0f34-e87e-4301-8be5-b92b0705a409)


#### 4.3.2 Esquema Relacional

![image](https://github.com/user-attachments/assets/725039bb-7a32-4a86-9612-7ad014d21012)



#### 4.3.3 Modelo Físico

A parte de criação das tabelas para o gerenciamento de dados da aplicação é feita com a ajuda de um ORM (Mapeamento Objeto-Relacional). Dessa forma, os desenvolvedores não precisam se preocupar em escrever comandos SQL diretamente, podendo realizar essa tarefa por meio do código da aplicação. Isso permite manter o foco no desenvolvimento das funcionalidades.

O ORM utilizado em nosso projeto foi o SQLAlchemy, uma biblioteca voltada para a linguagem Python.

##### Backend

![image](https://github.com/user-attachments/assets/7d47b402-1ada-4fc4-8731-31e636f76d3b)

No exemplo acima, estamos criando a tabela de usuários e especificando as informações que devem ser armazenadas.

##### Banco de dados 

![image](https://github.com/user-attachments/assets/310ff64e-953f-4eed-8676-6a78f0ede60f)

No banco de dados, conseguimos visualizar as informações conforme foram definidas no backend.

##### Criação Automática das Tabelas

Para criar as tabelas, é necessário apenas executar um único comando e a criação é feita automaticamente.

![image](https://github.com/user-attachments/assets/6e9b7269-da46-4c2b-8842-bf001a06f3fb)


### 4.4. Tecnologias

Para a construção do FluAI, foram escolhidas tecnologias modernas e adequadas ao escopo do projeto, garantindo desempenho, escalabilidade e uma boa experiência para o usuário final.


| **Dimensão**   | **Tecnologia**  |
| ---            | ---             |
| SGBD           | Firebase E Postgre           |
| Front end      | Kotlin   |
| Back end       | Pyhton|
| Deploy         | Github Pages    |
| IA             | Llama     |
