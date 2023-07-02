# Day Off Marker

Projeto por manter as configurações, tratamento e padrões da Autenticação e Validação de acesso de todos os projetos que importa.
* Versão disponivel: 1.0-SNAPSHOT

## Framework Utilizado

* [Spring Boot]('https://spring.io/projects/spring-boot')
    * Versão: 3.1.1
* [Java]('https://www.java.com/pt-BR/')
    * Versão: 17 ou superior

## Build do projeto

* Realizar o clone do projeto
* Na pasta do clone rodar o seguinte comando ``` mvn clean install ``` projeto ira buildar como defeault ele ira utilizar
Keycloak como provedor de acesso e o Postgresql como base de dados

### Build com profile

O projeto possui os seguintes profiles para banco de dados
* postgresql (Default)
* mysql

Para provedor de acessos
* keycloak (Default)
* azure

Ex:
``` mvn clean install -P azure,postgresql ```

## Framework utilizados

* [Jdempotent]('https://github.com/Trendyol/Jdempotent')
  * Versão: 3.0.0 (Em desenvolvimento)
