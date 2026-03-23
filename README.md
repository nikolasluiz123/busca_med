# BuscaMed - Guia de Arquitetura e Desenvolvimento

Este guia descreve a arquitetura do aplicativo, os padrões adotados e as diretrizes para evolução do código.

O projeto segue os princípios da **Clean Architecture** combinados com **MVVM (Model-View-ViewModel)** na camada de apresentação.

A base está dividida em três camadas principais:

- Data
- Domain
- Presentation

# 1. Camada de Dados (Data Layer)

Responsável por gerenciar a origem dos dados (banco local, Firebase/Firestore ou APIs externas).

Objetivo:
- Fornecer dados prontos para a camada de domínio
- Ocultar detalhes de implementação (SDKs, bibliotecas, etc.)

## 1.1 DataSources vs Services

A interação com fontes externas é dividida em duas abstrações:

### DataSources

Responsáveis por operações de entidades (CRUD).

Características:
- Representam acesso direto à fonte de dados
- Seguem contratos bem definidos
- Podem ter múltiplas implementações (local, remoto, etc.)

Diretriz:
- Para novas fontes (ex: API REST), criar uma implementação específica, idealmente se for uma API REST podemos nomear `RemoteDataSource`.

### Services

Utilizados quando a operação não é simplesmente CRUD, é algo mais sobre a execução de um processamento, algo parecido com enviar uma Imagem e receber informações sobre ela extraídas por uma API terceira ou própria.

Características:
- Executam processos específicos
- Representam integrações externas com lógica própria
- Exemplo típico: autenticação

## 1.2 Documents e Serialização

Os dados que trafegam entre o app e o banco são representados por estruturas específicas da camada de dados.

### Conversão para Map

Existe um mecanismo que converte objetos em `Map<String, Any?>`.

Objetivo:
- Facilitar inserções e atualizações parciais
- Padronizar comunicação com o banco

### Alternativa com Serialização Tipada

Uso de serialização com anotações (ex: `@Serializable`):

Vantagens:
- Mapeamento explícito
- Maior controle sobre o formato
- Independência de biblioteca

## 1.3 Repositories

Atuam como camada intermediária entre Data e Domain.

### Responsabilidades

- Decidir a origem dos dados (local, remoto ou ambos)
- Orquestrar múltiplas fontes
- Mapear dados da camada de dados para o domínio

### Não Responsabilidades

- Não conter regras de negócio

# 2. Camada de Domínio (Domain Layer)

Camada central do sistema.

Características:
- Contém regras de negócio
- Independente de frameworks e infraestrutura


## 2.1 Models

Representam os dados na perspectiva do domínio.

Características:
- Estruturas puras
- Sem dependência de frameworks
- Contêm apenas o necessário para regras de negócio

Observação:
- Podem ser diferentes dos modelos da camada de dados

## 2.2 Inversão de Dependência

As interfaces são definidas no domínio e implementadas na camada de dados.

### Objetivo

- O domínio define contratos
- A infraestrutura implementa esses contratos

### Benefícios

- Baixo acoplamento
- Facilidade de testes
- Flexibilidade para trocar tecnologias

## 2.3 Use Cases (Casos de Uso)

Responsáveis pela lógica de negócio.

### Princípio da Responsabilidade Única (SRP)

- Cada caso de uso executa apenas uma ação

Evitar:
- Classes grandes com múltiplas responsabilidades

### Padrão de Invocação

Os casos de uso podem ser invocados como funções.

Exemplo: `useCase(param)`

## 2.4 Validações vs Exceptions

Diretriz clara para tratamento de erros.

### Sistema de Validação

Utilizado para erros esperados de regra de negócio.

Exemplos:
- Dados inválidos
- Campos incorretos

Características:
- Retorno controlado
- Não interrompe o fluxo com exceções

### Exceptions (Uso Restrito)

Utilizadas apenas para falhas externas ou inesperadas.

Exemplos:
- Falha de rede
- Erro de serviço externo

### Regra Geral

- Regra de negócio → validação
- Falha de sistema → exception

# 3. Camada de Apresentação (Presentation Layer)

Responsável pela interação com o usuário.

Tecnologias:
- Jetpack Compose
- ViewModels com gerenciamento de estado

## 3.1 Componentes e Composição

Diretriz:
- Priorizar reutilização

### Boas Práticas

- Verificar componentes existentes antes de criar novos
- Extrair componentes reutilizáveis
- Centralizar componentes comuns

## 3.2 States Reutilizáveis

Abstração de estados comuns para reduzir boilerplate.

### Campos de Texto

Encapsulam:
- Valor
- Callback de alteração
- Estado de erro

Benefício:
- ViewModel mais limpo
- Padronização de comportamento

### Estados de Diálogo

Centralizam:
- Exibição de erros
- Confirmações

## 3.3 Callbacks Funcionais

Utilizados para comunicação entre UI e lógica.

### Estrutura

Callbacks estruturados com:
- Sucesso
- Falha

A estrutura dos callbacks pode e deve variar conforme a necessidade.

### Objetivo

- Desacoplar UI do processamento
- Permitir respostas assíncronas

## 3.4 Tratamento de Erros no ViewModel

Centralização do tratamento de falhas.

### Funcionalidades

- Interceptação global de exceções
- Conversão de erros em feedback visual
- Monitoramento de validações

## 3.5 Tematização (Theme)

Centralização de estilos visuais.

### Estrutura

- Definição de cores
- Suporte a tema claro e escuro

### Diretriz

- Alterações centralizadas devem refletir em todo o app

# 4. Testes Automatizados

Baseados em dois pilares:

- Testes Unitários
- Testes de UI

## 4.1 Testes Unitários

Focados em comportamento isolado.

### Padrão

Nome dos testes: `shouldDoSomethingWhenCondition`

Estrutura:
- Setup
- Act
- Assert

### Tecnologias

- Framework de testes
- Biblioteca de mocks
- Ferramentas para controle de corrotinas

## 4.2 Testes de UI

Focados na interface e comportamento visual.

### Objetivos

- Validar renderização
- Validar interação

### Tecnologias

- Framework de teste de UI
- Execução sem emulador completo

### Boas Práticas

#### Uso de Test Tags

Evitar:
- Busca por texto

Motivo:
- Fragilidade com múltiplos idiomas

### Diretriz

- Todo componente interativo deve possuir identificador estável
- Padronizar identificadores por tela
