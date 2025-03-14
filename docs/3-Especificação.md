# 3. Especificações do Projeto
<span style="color:red">Pré-requisitos: <a href="2-Planejamento-Projeto.md"> Planejamento do Projeto do Software (Cronograma) </a></span>

## 3.1 Classificação dos Requisitos Funcionais x Requisitos não Funcionais
### Requisitos Funcionais

| ID     | Descrição do Requisito                                  | Prioridade |
|--------|---------------------------------------------------------|------------|
| RF-001 | Permitir que o usuário realize um teste de nivelamento  | ALTA       |
| RF-002 | Adaptar os exercícios conforme o desempenho do usuário  | ALTA       |
| RF-003 | Oferecer feedback personalizado após cada atividade     | MÉDIA      |
| RF-004 | Implementar exercícios interativos e gamificados        | MÉDIA      |
| RF-005 | Permitir acesso ao histórico de progresso               | BAIXA      |

### Requisitos Não Funcionais

| ID      | Descrição do Requisito                                   | Prioridade |
|---------|----------------------------------------------------------|------------|
| RNF-001 | O sistema deve ser responsivo em dispositivos móveis      | ALTA       |
| RNF-002 | As respostas devem ser processadas em no máximo 2 segundos| MÉDIA      |
| RNF-003 | O sistema deve funcionar em Android e iOS                 | ALTA       |

## Restrições

| ID  | Restrição                                            |
|-----|-----------------------------------------------------|
| 01  | O aplicativo deve ser desenvolvido em Python com Streamlit |
| 02  | Deve integrar APIs de IA para personalizar os exercícios  |
| 03  | O sistema deve armazenar os dados no Google Drive         |

## 3.2 Histórias de Usuários

### a) Histórias de Usuários

| EU COMO...              | QUERO/PRECISO ...                    | PARA ...                                  |
|-------------------------|--------------------------------------|--------------------------------------------|
| Iniciante em inglês     | Realizar um teste para saber meu nível| Começar a estudar de acordo com meu nível  |
| Usuário intermediário   | Receber exercícios adaptados ao meu desempenho | Melhorar meu vocabulário e gramática      |
| Usuário avançado        | Obter feedback detalhado nas respostas| Identificar e corrigir erros específicos   |

## Tarefas Técnicas (Tasks)

### História de Usuário: 
                     Como iniciante, quero realizar um teste de nivelamento para começar a estudar de acordo com meu nível.

### Tarefas Técnicas:
- Criar a interface do teste de nivelamento
- Implementar a lógica de avaliação e classificação
- Armazenar os resultados no banco de dados

### História de Usuário:
                     Como usuário intermediário, quero receber exercícios adaptados ao meu desempenho para melhorar meu vocabulário e gramática.

### Tarefas Técnicas:
- Desenvolver o sistema adaptativo de exercícios
- Integrar IA para ajustar os conteúdos automaticamente
- Garantir a atualização contínua do progresso

### História de Usuário:
                     Como usuário avançado, quero obter feedback detalhado nas respostas para identificar e corrigir erros específicos.

### Tarefas Técnicas:
- Implementar feedback automático e personalizado
- Adaptar mensagens com base nos erros mais comuns
- Armazenar feedbacks no histórico de progresso





