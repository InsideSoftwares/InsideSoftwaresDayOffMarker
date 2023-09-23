# Day Off Marker

O objetivo do sistema é gerenciar os dias úteis e não úteis de uma empresa, levando em consideração feriados nacionais, municipais e recessos nas cidades em que a empresa atua.
Isso permite consultas e validações mais rápidas sobre os dias e possibilita processos automatizados, como geração de faturas e rotinas da empresa.

O sistema tem um foco maior em empresas de telecomunicações, as quais possuem diversos processos para o seu funcionamento, como geração de cobranças de clientes, agendamento de atendimento e instalação nos clientes, além da suspensão de clientes inadimplentes.
Todos esses processos dependem de datas específicas para serem realizados, a fim de reduzir impactos na empresa.

* Versão disponivel: 1.0.0-SNAPSHOT
* [License](LICENSE.MD)

## Bibliotecas, Framework e Projetos utilizado para implementação do sistema
* [Spring Boot](https://spring.io/projects/spring-boot)
  * Versão: 3.1.4
* [Java](https://www.java.com/pt-BR/)
  * Versão: 21 ou superior
* [Liquibase](https://www.liquibase.org/)
  * Versão: 4.23.0
* [Jdempotent](https://github.com/Trendyol/Jdempotent)
  * Versão: 3.0.0 (Em desenvolvimento)
* [InsideSoftwaresCommons](https://github.com/InsideSoftwares/InsideSoftwaresCommons)
  * Versão: 1.0.0 (Em desenvolvimento)
* [InsideSoftwaresSecurityCommons](https://github.com/InsideSoftwares/InsideSoftwaresSecurityCommons)
  * Versão: 1.0.0 (Em desenvolvimento)

## Bancos suportados

* Mysql: 8.0.34 ou superior
* Postgres: 15.4 ou superior

## Build do projeto

* Realizar o clone do projeto
* Na pasta do clone rodar o seguinte comando ``` mvn clean install ```

### Build com profile

O projeto possui os seguintes profiles para banco de dados
* analyze (Para executar a analise de codigo)
* release (Para executar a criação de imagem docker latest)

Ex:

``` mvn clean install -P analyze ```

### Será necessarios os seguintes projetos buildados na sua maquina:
* [InsideSoftwaresCommons](https://github.com/InsideSoftwares/InsideSoftwaresCommons)
* [InsideSoftwaresSecurityCommons](https://github.com/InsideSoftwares/InsideSoftwaresSecurityCommons)
* [Jdempotent](https://github.com/SawCunha/Jdempotent/tree/feature/spring_boot_3)


## Arquivos Extras

### Documentação
A pasta: `etc/documentation` possui todos os arquivos utilizados para documentar a arquitetura, database,
APIs.

### Arquivos de Configuração
O Projeto possui alguns arquivos de configuração para execução do *'Day Off Marker'* e do *'Job'*.

Eles estão disponibilizados na pasta ``etc/resources``

### Infra

A pasta ``etc/infra`` possui os arquivos para configurar a infraestrutura de teste e homologação do sistema.

#### Provedor de acessos - Keycloak
O arquivo base para importar o Realm no keycloak para obter todas as permissões e grupos, para utilizar o sistema.

Eles estão disponibilizados na pasta ``etc/infra/keycloak``, com o nome: ``DayOffMarker_Realm.json``

#### Gateway - APISIX
Possui os arquivos para configuração basica do ``Apache APISIX``.

Eles estão disponibilizados na pasta ``etc/infra/apisix``, os seguintes arquivos:
* ``Upstream_eureka.yml``
* ``DayOffMarker.yml``
* ``DayOffMarkerSwagger.yml``

#### Gateway - Spring Cloud Gateway
Possui os arquivos para configuração basica do ``Spring Cloud Gateway``.

Eles estão disponibilizados na pasta ``etc/infra/gateway``, os seguintes arquivos:
* ``Inside_CloudGateway.yml``

### Scripts

Na pasta: ``etc/scripts`` possui os arquivos utilizados para realizar a build do sistema e gerar os arquivos necessarios
para utiliza-lo.

### Testes
Na pasta: ``etc/test`` possui os arquivos utilizados para testar o sistema.
* ``JMeter_Test_Carga.jmx`` possui configuração para realizar alguns testes de carga no sistema.

## Environment personalizaveis - Day Off Marker - Application

* `DAY_OFF_MARKER_PORT`
    * Define a porta com qual o serviço vai esta acessível
    * Default: `8080`
* `DAY_OFF_MARKER_PROFILES`
    * Define os Profiles para executar o serviço
    * Default: `native`
* `CLOUD_CONFIG_URI`
    * Define a URI para logar no sistema: InsideSoftwaresCloudConfig
    * Default: `http://localhost:8888`
* `CLOUD_CONFIG_NAME`
    * Define o usuario para logar no sistema: InsideSoftwaresCloudConfig
    * Default: `ADMIN`
* `CLOUD_CONFIG_PASSWORD`
    * Define o usuario para senha no sistema: InsideSoftwaresCloudConfig
    * Default: `ADMIN`
* `CLOUD_ADMIN_URL`
    * Define a URI para logar no sistema: InsideSoftwaresCloudAdmin
    * Default: `http://localhost:8889/admin`
* `CLOUD_EUREKA_URL`
    * Define a URI para logar no sistema: InsideSoftwaresCloudAdmin
    * Default: `http://localhost:8889/eureka`
* `LOGBACK_LOG_DIR`
    * Define local em que será salvo os logs do serviço

## Environment personalizaveis - Day Off Marker - Job

* `DAY_OFF_MARKER_JOB_PROFILES`
    * Define os Profiles para executar o serviço
    * Default: `native`
* `CLOUD_CONFIG_URI`
    * Define a URI para logar no sistema: InsideSoftwaresCloudConfig
    * Default: `http://localhost:8888`
* `CLOUD_CONFIG_NAME`
    * Define o usuario para logar no sistema: InsideSoftwaresCloudConfig
    * Default: `ADMIN`
* `CLOUD_CONFIG_PASSWORD`
    * Define o usuario para senha no sistema: InsideSoftwaresCloudConfig
    * Default: `ADMIN`
* `CLOUD_ADMIN_URL`
    * Define a URI para logar no sistema: InsideSoftwaresCloudAdmin
    * Default: `http://localhost:8889/admin`
* `CLOUD_EUREKA_URL`
    * Define a URI para logar no sistema: InsideSoftwaresCloudAdmin
    * Default: `http://localhost:8889/eureka`
* `LOGBACK_LOG_DIR`
    * Define local em que será salvo os logs do serviço
