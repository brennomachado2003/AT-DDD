# Freela Marketplace

Projeto de referência para um marketplace de contratação de freelancers construído com arquitetura de microsserviços em Java e Spring.

A aplicação representa um cenário em que clientes contratam freelancers para a execução de trabalhos. O núcleo do sistema é o gerenciamento dos contratos firmados entre as partes. A partir desse domínio, outros serviços mantêm informações relacionadas a notificações, reputação e auditoria.

## Visão geral

O sistema é composto por seis aplicações Spring Boot:

- `eureka-server`: registro e descoberta dos serviços.
- `api-gateway`: ponto de entrada HTTP da aplicação.
- `contrato-service`: gerenciamento dos contratos entre clientes e freelancers.
- `notificacao-service`: armazenamento das notificações relacionadas aos contratos.
- `reputacao-service`: manutenção de informações agregadas sobre os freelancers.
- `auditoria-service`: registro de eventos relevantes do sistema.

A infraestrutura local utiliza PostgreSQL e Apache Kafka.

```text
                         +-------------------+
                         |      Cliente      |
                         +---------+---------+
                                   |
                                   | HTTP
                                   v
                         +-------------------+
                         |    API Gateway    |
                         |       :8080       |
                         +---------+---------+
                                   |
                     Service Discovery / Eureka
                                   |
                +------------------+------------------+
                |                  |                  |
                v                  v                  v
       +----------------+  +----------------+  +----------------+
       | contrato       |  | notificacao    |  | reputacao      |
       | service :8081  |  | service :8082  |  | service :8083  |
       +----------------+  +----------------+  +----------------+
                |
                |                       +----------------+
                +---------------------->| auditoria      |
                                        | service :8084  |
                                        +----------------+

                          +-------------------+
                          |       Kafka       |
                          |       :9092       |
                          +-------------------+

                          +-------------------+
                          |    PostgreSQL     |
                          |       :5433       |
                          +-------------------+
```

## Domínio

O domínio principal está no `contrato-service`.

Um contrato representa o vínculo entre um cliente e um freelancer para a execução de um trabalho. Cada contrato possui:

- identificador;
- cliente;
- freelancer;
- título do trabalho;
- valor;
- status;
- data de criação.

Os estados disponíveis são:

```text
ATIVO
ENTREGA_REGISTRADA
CONCLUIDO
CANCELADO
```

O fluxo de negócio previsto pelo modelo é:

```text
ATIVO
  |
  v
ENTREGA_REGISTRADA
  |
  v
CONCLUIDO
```

Um contrato ativo também pode ser cancelado.

O `contrato-service` utiliza uma organização inspirada em Domain-Driven Design, separando domínio, aplicação e infraestrutura.

```text
contrato-service
└── src/main/java/br/com/freela/contrato
    ├── application
    ├── domain
    │   ├── event
    │   ├── model
    │   ├── repository
    │   └── shared
    └── infrastructure
        ├── persistence
        └── web
```

O Aggregate `Contrato` concentra as regras relacionadas às mudanças de estado e produz eventos de domínio. Atualmente existe o evento `ContratoCriado`, que contém as principais informações do contrato no momento da criação.

## Serviços

### contrato-service

Responsável pelo ciclo de vida dos contratos.

Porta:

```text
8081
```

Banco:

```text
contrato_db
```

Principais recursos HTTP:

```text
POST /api/contratos
GET  /api/contratos
GET  /api/contratos/{id}
```

Exemplo de criação de contrato:

```json
{
  "clienteId": "11111111-1111-1111-1111-111111111111",
  "freelancerId": "22222222-2222-2222-2222-222222222222",
  "titulo": "Construção de API de pagamentos",
  "valor": 3500.00
}
```

### notificacao-service

Mantém notificações relacionadas aos acontecimentos do marketplace.

Porta:

```text
8082
```

Banco:

```text
notificacao_db
```

As notificações armazenam informações como contrato, destinatário, tipo, mensagem e momento de criação.

### reputacao-service

Mantém informações agregadas sobre a atividade dos freelancers.

Porta:

```text
8083
```

Banco:

```text
reputacao_db
```

Para cada freelancer são mantidos dados como quantidade de contratos concluídos e valor total dos contratos registrados.

Endpoint disponível para consulta:

```text
GET /api/reputacoes
```

### auditoria-service

Responsável pelo armazenamento de registros associados aos eventos do sistema.

Porta:

```text
8084
```

Banco:

```text
auditoria_db
```

Cada registro de auditoria pode armazenar:

- `eventId`;
- `aggregateId`;
- tipo do evento;
- `correlationId`;
- payload original;
- horário de recebimento.

Endpoint disponível para consulta:

```text
GET /api/auditoria
```

## API Gateway

O `api-gateway` é o ponto de entrada HTTP para os microsserviços.

Porta:

```text
8080
```

As rotas configuradas são:

| Caminho | Serviço |
|---|---|
| `/api/contratos/**` | `contrato-service` |
| `/api/notificacoes/**` | `notificacao-service` |
| `/api/reputacoes/**` | `reputacao-service` |
| `/api/auditoria/**` | `auditoria-service` |

O Gateway utiliza Eureka para localizar as instâncias dos serviços.

Também existe suporte ao header:

```text
X-Correlation-Id
```

Quando o header não é enviado pelo cliente, o Gateway gera automaticamente um UUID e o encaminha para o serviço de destino.

## Eureka Server

O Eureka Server mantém o registro das aplicações disponíveis no ambiente.

Porta:

```text
8761
```

Interface web:

```text
http://localhost:8761
```

Os microsserviços utilizam, por padrão:

```text
http://localhost:8761/eureka/
```

como endereço do service registry.

## PostgreSQL

O ambiente utiliza uma única instância PostgreSQL com bancos separados para cada serviço.

```text
Host:     localhost
Porta:    5432
Usuário:  freela
Senha:    freela
```

Bancos criados durante a inicialização:

```text
contrato_db
notificacao_db
reputacao_db
auditoria_db
```

O script de criação dos bancos está em:

```text
infra/postgres/init-databases.sql
```

Os serviços utilizam Hibernate com `ddl-auto: update` para criação e atualização das tabelas locais.

## Apache Kafka

O Apache Kafka é executado em modo KRaft, sem ZooKeeper.

Para aplicações executadas diretamente na máquina:

```text
localhost:9092
```

Para aplicações executadas dentro da rede Docker:

```text
kafka:19092
```

O broker possui listeners separados para comunicação interna e externa.

O ambiente também inclui o Kafka UI.

```text
http://localhost:8090
```

Tópicos Kafka

```text
 contrato-criado
 contrato-entregue
 contrato-cancelado
 contrato-concluido
```

## Eventos

Contrato criado

```text
    Nome: ContratoCriado
    Tópico: contrato-criado
    Produtor: contrato-service
    Consumidores: notificacao-service, auditoria-service
    Campos obrigatórios: UUID eventId, Instant occurredAt, UUID contratoId, UUID clienteId, UUID freelancerId, String titulo, BigDecimal valor
    Chave: eventId
```

Contrato entregue

```text
    Nome: ContratoEntregue
    Tópico: contrato-entregue
    Produtor: contrato-service
    Consumidores: notificacao-service, auditoria-service
    Campos obrigatórios: UUID eventId, Instant occurredAt, UUID contratoId, UUID clienteId, UUID freelancerId, String status, String titulo, BigDecimal valor
    Chave: eventId
```

Contrato cancelado

```text
    Nome: ContratoCancelado
    Tópico: contrato-cancelado
    Produtor: contrato-service
    Consumidores:notificacao-service, auditoria-service
    Campos obrigatórios: UUID eventId, Instant occurredAt, UUID contratoId, UUID clienteId, UUID freelancerId, String titulo, BigDecimal valor
    Chave: eventId
```

Contrato concluido

```text
    Nome: ContratoConcluido
    Tópico: contrato-concluido
    Produtor: contrato-service
    Consumidores: notificacao-service, auditoria-service, reputacao-service
    Campos obrigatórios: UUID eventId, Instant occurredAt, UUID contratoId, UUID clienteId, UUID freelancerId, String titulo, BigDecimal valor
    Chave: eventId
```


## Estratégia de particionamento

Os eventos relacionados ao ciclo de vida dos contratos utilizam o `contratoId` como chave de particionamento no Kafka.

A chave utilizada nas publicações é obtida por meio de:

```java
contrato.contratoId().toString()
```

Essa chave permite que o Kafka determine a partição na qual cada mensagem será armazenada. Como os eventos de um mesmo contrato utilizam a mesma chave, eles são direcionados para a mesma partição, permitindo preservar a ordem dos eventos desse contrato.

### Chave de particionamento

A chave utilizada nos eventos é:

```text
contratoId
```

Exemplo:

```java
kafkaTemplate.send(
    contratoCriadoTopic,
    contrato.contratoId().toString(),
    mensagem
);
```

A mesma estratégia é utilizada para os eventos:

* `ContratoCriado`
* `ContratoEntregue`
* `ContratoCancelado`
* `ContratoConcluido`

### Particionamento

Os tópicos são configurados com **3 partições** e **1 réplica**:

```java
TopicBuilder
    .name("contrato-criado")
    .partitions(3)
    .replicas(1)
    .build();
```

A mesma configuração de particionamento é utilizada nos demais tópicos relacionados ao ciclo de vida dos contratos.

A utilização de múltiplas partições permite que eventos referentes a contratos diferentes sejam distribuídos entre partições distintas e processados concorrentemente.

### Concorrência

Os consumidores Kafka são configurados com concorrência compatível com a quantidade de partições:

```yaml
spring:
  kafka:
    listener:
      concurrency: 3
```

Dessa forma, as três partições podem ser processadas simultaneamente por consumidores pertencentes ao mesmo grupo.

Contratos diferentes podem ser processados em paralelo quando seus eventos forem direcionados para partições distintas.

Para eventos referentes ao mesmo contrato, a utilização do `contratoId` como chave garante que eles sejam direcionados para a mesma partição. Como o Kafka preserva a ordem dos registros dentro de uma partição, os eventos do mesmo contrato permanecem ordenados.

Exemplo:

```text
Contrato A → contratoId A → Partição 0
    ├── ContratoCriado
    ├── ContratoEntregue
    └── ContratoConcluido

Contrato B → contratoId B → Partição 1
    ├── ContratoCriado
    ├── ContratoEntregue
    └── ContratoConcluido

Contrato C → contratoId C → Partição 2
    ├── ContratoCriado
    ├── ContratoEntregue
    └── ContratoConcluido
```

Assim, a estratégia permite **processamento concorrente para contratos diferentes** e **preservação da ordem para eventos pertencentes ao mesmo contrato**.

## Processamento concorrente e ordenação

A solução utiliza o Kafka para permitir o processamento concorrente de eventos referentes a contratos distintos, mantendo a ordenação dos eventos pertencentes ao mesmo contrato.

A estratégia utiliza o `contratoId` como chave das mensagens Kafka. Dessa forma, todos os eventos relacionados ao mesmo contrato utilizam a mesma chave e são direcionados para a mesma partição.

Os tópicos possuem **3 partições**, permitindo que eventos de contratos diferentes sejam distribuídos entre partições distintas e processados de forma concorrente.

Exemplo:

```text
Contrato A → chave A → Partição 0
Contrato B → chave B → Partição 1
Contrato C → chave C → Partição 2
```

Os consumidores são configurados com **concorrência 3**, permitindo o processamento simultâneo das partições.

Para um mesmo contrato, os eventos permanecem na mesma partição:

```text
Contrato A
    │
    ├── ContratoCriado
    ├── ContratoEntregue
    └── ContratoConcluido
            │
            ↓
       mesma partição
            │
            ↓
      ordem preservada
```

Assim, uma sequência de eventos de um mesmo contrato, como:

```text
ContratoCriado
      ↓
ContratoEntregue
      ↓
ContratoConcluido
```

é processada respeitando a ordem dos registros dentro da partição.

Ao mesmo tempo, eventos pertencentes a contratos diferentes podem ser processados em paralelo por consumidores associados a partições distintas.

Essa estratégia atende ao requisito de permitir **processamento concorrente para contratos diferentes**, mantendo a **ordenação dos eventos para o mesmo contrato**.

## Tratamento de duplicidade e idempotência

As mensagens publicadas no Kafka possuem um `eventId` único, utilizado para identificar individualmente cada evento.

Os consumidores utilizam esse identificador para verificar se o evento já foi processado antes de executar novamente a operação de negócio.

Antes de processar uma mensagem, o consumidor consulta o registro de eventos processados utilizando o `eventId`:

```text
Mensagem Kafka
      │
      ↓
   eventId
      │
      ↓
Evento já processado?
   │           │
  Sim         Não
   │           │
   ↓           ↓
Ignora     Processa evento
              │
              ↓
       Registra eventId
```

Quando o `eventId` já está registrado, a mensagem é considerada uma duplicata e não é processada novamente.

Essa estratégia evita efeitos duplicados, como o registro repetido de uma auditoria, uma notificação ou uma atualização de reputação.

Exemplo:

```text
Evento:
eventId = 550e8400-e29b-41d4-a716-446655440000

Primeira entrega:
→ eventId não existe
→ evento é processado
→ eventId é registrado

Segunda entrega:
→ eventId já existe
→ evento é ignorado
```

A identificação do evento é independente da chave de particionamento. O `eventId` é utilizado para **idempotência e controle de duplicidade**, enquanto o `contratoId` é utilizado como **chave Kafka para particionamento e ordenação dos eventos do mesmo contrato**.


## Logs

Todos os serviços utilizam logs em nível `INFO` com um formato comum contendo data, nível, nome da aplicação, thread, logger e mensagem.

Exemplo:

```text
2026-09-14 14:42:18.431 INFO service=contrato-service thread=http-nio-8081-exec-1 logger=b.c.f.c.a.ContratoApplicationService - contrato.criacao.inicio clienteId=... freelancerId=...
```

O código registra pontos importantes do fluxo, incluindo:

```text
gateway.request.inicio
gateway.request.fim
http.contrato.criar
contrato.criacao.inicio
contrato.dominio.criado
contrato.persistence.save.inicio
contrato.persistence.save.sucesso
contrato.evento.pendente
contrato.criacao.sucesso
reputacao.atualizacao.inicio
reputacao.atualizacao.sucesso
auditoria.registro.inicio
auditoria.registro.sucesso
```

A presença do `correlationId` nas chamadas HTTP permite relacionar logs produzidos durante uma mesma requisição.

## Centralização dos logs

A solução utiliza o **Graylog** como plataforma centralizada para coleta e consulta dos logs gerados pelos microsserviços.

Cada serviço da aplicação possui configuração própria de logging, mas os logs são enviados para uma instância central do Graylog. Dessa forma, é possível consultar eventos de diferentes microsserviços em um único local, facilitando o acompanhamento de operações distribuídas e a identificação de falhas.

### Estratégia utilizada

A aplicação utiliza o **Logback** como mecanismo de logging e o `GelfUdpAppender` para enviar os registros ao Graylog utilizando o protocolo **GELF sobre UDP**.

A configuração utiliza um appender GELF:

```xml
<appender name="GELF" class="de.siegmar.logbackgelf.GelfUdpAppender">
    <graylogHost>${GRAYLOG_HOST}</graylogHost>
    <graylogPort>${GRAYLOG_PORT}</graylogPort>

    <encoder class="de.siegmar.logbackgelf.GelfEncoder">
        <originHost>${APP_NAME}</originHost>
        <includeMdcData>true</includeMdcData>
        <includeKeyValues>true</includeKeyValues>

        <staticField>
            service:${APP_NAME}
        </staticField>
    </encoder>
</appender>
```

Os logs são enviados de forma assíncrona através de um `AsyncAppender`:

```xml
<appender name="ASYNC_GELF"
          class="ch.qos.logback.classic.AsyncAppender">

    <appender-ref ref="GELF"/>
    <neverBlock>true</neverBlock>

</appender>
```

O appender assíncrono evita que o envio dos logs ao Graylog bloqueie o processamento principal da aplicação.

### Infraestrutura

O Graylog é executado como parte da infraestrutura Docker Compose juntamente com seus componentes de armazenamento e gerenciamento.

A aplicação disponibiliza o Graylog na porta:

```text
http://localhost:9000
```

Os serviços enviam os logs para a porta `12201`, utilizando GELF/UDP.

```text
┌──────────────────────┐
│ contrato-service     │
└──────────┬───────────┘
           │
           │ GELF/UDP
           ↓
┌──────────────────────┐
│ notificacao-service  │
└──────────┬───────────┘
           │
           │ GELF/UDP
           ↓
┌──────────────────────┐
│ reputacao-service    │
└──────────┬───────────┘
           │
           │ GELF/UDP
           ↓
┌──────────────────────┐
│ auditoria-service    │
└──────────┬───────────┘
           │
           ↓
      ┌─────────┐
      │ Graylog │
      └─────────┘
```

Cada registro contém informações que permitem identificar sua origem, incluindo o nome do serviço. Além disso, os logs podem conter identificadores utilizados na correlação das operações, como `contratoId`, `eventId`, `correlationId`, `traceId` e `spanId`.

Com essa estratégia, os logs de todos os microsserviços ficam centralizados em um único ambiente, permitindo realizar buscas por identificadores comuns e acompanhar o processamento de uma operação distribuída entre diferentes serviços.

### Configuração utilizada para tracing

A solução utiliza o **Zipkin** como servidor de tracing distribuído para acompanhar as operações realizadas entre os diferentes microsserviços.

O tracing está configurado com sampling de `100%`:

```yaml
management:
  tracing:
    sampling:
      probability: 1.0
```

Os identificadores `traceId` e `spanId` são disponibilizados nos logs, permitindo relacionar os registros de uma mesma operação distribuída:

```text
traceId = identificação da operação distribuída
spanId  = identificação de uma etapa da operação
```

O **Zipkin** é executado através do Docker Compose e disponibilizado na porta `9411`:

```text
http://localhost:9411
```

A arquitetura de tracing utiliza o seguinte fluxo:

```text
Cliente
   ↓
API Gateway
   ↓
contrato-service
   ↓
Kafka
   ├──→ notificacao-service
   ├──→ reputacao-service
   └──→ auditoria-service
             ↓
           Zipkin
```

Dessa forma, o Zipkin centraliza os traces gerados pelos serviços, permitindo visualizar a execução distribuída das operações e identificar os diferentes `spans` envolvidos em uma requisição.


## Infraestrutura local

Os serviços de infraestrutura estão definidos em:

```text
infra/docker-compose.yml
```

Para iniciar o ambiente:

```bash
cd infra
docker compose up -d
```

### Primeiro acesso ao Graylog

Após iniciar os containers, é necessário realizar o **primeiro acesso ao Graylog** através da interface web:

```text
http://localhost:9000
```

Esse primeiro acesso é necessário para que o Graylog conclua sua inicialização e disponibilize a estrutura necessária para o recebimento dos logs.

Após realizar o primeiro acesso ao Graylog, o container responsável pela configuração inicial (`graylog-init`) poderá concluir sua execução.

Para verificar o estado dos containers:

```bash
docker compose ps
```

O Graylog estará disponível em:

```text
http://localhost:9000
```

O Zipkin estará disponível em:

```text
http://localhost:9411
```

A interface do Kafka estará disponível em:

```text
http://localhost:8090
```

### Verificação dos containers

```bash
docker compose ps
```

### Encerramento da infraestrutura

Para encerrar os containers:

```bash
docker compose down
```

Os dados do PostgreSQL, MongoDB e Graylog são mantidos em volumes Docker.

Para encerrar os containers e remover os volumes persistidos:

```bash
docker compose down -v
```

> **Atenção:** o comando `docker compose down -v` remove os dados persistidos nos volumes, incluindo os dados dos bancos de dados e do Graylog.

## Execução das aplicações

A partir da raiz do projeto, cada módulo pode ser iniciado separadamente com Maven.

### Eureka Server

```bash
mvn -pl eureka-server spring-boot:run
```

### API Gateway

```bash
mvn -pl api-gateway spring-boot:run
```

### Contrato Service

```bash
mvn -pl contrato-service spring-boot:run
```

### Notificação Service

```bash
mvn -pl notificacao-service spring-boot:run
```

### Reputação Service

```bash
mvn -pl reputacao-service spring-boot:run
```

### Auditoria Service

```bash
mvn -pl auditoria-service spring-boot:run
```

## Portas

| Componente          |   Porta |
| ------------------- | ------: |
| API Gateway         |  `8080` |
| contrato-service    |  `8081` |
| notificacao-service |  `8082` |
| reputacao-service   |  `8083` |
| auditoria-service   |  `8084` |
| Eureka Server       |  `8761` |
| Kafka               |  `9092` |
| Kafka UI            |  `8090` |
| PostgreSQL          |  `5433` |
| Graylog             |  `9000` |
| Graylog GELF        | `12201` |
| Zipkin              |  `9411` |
| MongoDB             | `27017` |

### Endereços das interfaces

| Serviço  | Endereço                |
| -------- | ----------------------- |
| Eureka   | `http://localhost:8761` |
| Kafka UI | `http://localhost:8090` |
| Graylog  | `http://localhost:9000` |
| Zipkin   | `http://localhost:9411` |


## Teste básico

Com a infraestrutura e as aplicações em execução, um contrato pode ser criado pelo Gateway:

```bash
curl -i -X POST http://localhost:8080/api/contratos \
  -H 'Content-Type: application/json' \
  -H 'X-Correlation-Id: teste-contrato-001' \
  -d '{
    "clienteId": "11111111-1111-1111-1111-111111111111",
    "freelancerId": "22222222-2222-2222-2222-222222222222",
    "titulo": "Construção de API de pagamentos",
    "valor": 3500.00
  }'
```

Consulta dos contratos:

```bash
curl http://localhost:8080/api/contratos
```

Consulta de um contrato específico:

```bash
curl http://localhost:8080/api/contratos/{id}
```

## Tecnologias

```text
Java 21
Spring Boot 4.1
Spring Cloud
Spring Cloud Gateway
Netflix Eureka
Spring Data JPA
PostgreSQL 16
Apache Kafka 4
Docker Compose
Maven
```
