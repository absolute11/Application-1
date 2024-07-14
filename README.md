# App1 - User Service

## Описание

# Описание

Данный проект состоит из двух микросервисов: **App1** и **App2**.

### App1
UserService предоставляет CRUD операции для пользователей и ролей. Пользователи могут авторизоваться через GitHub OAuth2. В зависимости от роли (ROLE_ADMIN или ROLE_USER), пользователи могут выполнять различные действия.

### App2
App2 взаимодействует с App1 для получения информации о пользователях и ролях. Он перенаправляет запросы на App1 и используется для демонстрации межсервисного взаимодействия.

## Технологии

Проект использует следующие технологии:

- **Spring Boot**: основной фреймворк для создания микросервисов.
- **Spring Security**: для обеспечения безопасности и OAuth2 аутентификации.
- **OAuth2**: для авторизации через GitHub.
- **Spring Data JPA**: для работы с базой данных.
- **Flyway**: для миграций базы данных.
- **PostgreSQL**: база данных для хранения данных пользователей и ролей.
- **RestTemplate**: для межсервисного взаимодействия между UserService и App2.
- **JUnit**: для модульного тестирования.

## Требования

- Java 17
- Maven 3.6+
- PostgreSQL

## Сборка и запуск

1. Клонируйте репозиторий:
   ```bash
   git clone https://github.com/your-username/app1.git
   cd app1


## Настройте базу данных PostgreSQL
Создайте базу данных user_service и пользователя с правами доступа

## Настройте параметры подключения к базе данных в src/main/resources/application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/user_service
spring.datasource.username=postgres
spring.datasource.password=2441
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect


## Настройте параметры GitHub OAuth2 в src/main/resources/application.properties
spring.security.oauth2.client.registration.github.client-id=your-client-id
spring.security.oauth2.client.registration.github.client-secret=your-client-secret
spring.security.oauth2.client.registration.github.scope=user:email
spring.security.oauth2.client.registration.github.redirect-uri=http://localhost:8080/login/oauth2/code/github

## Сборка и запуск приложения:
mvn clean install
mvn spring-boot:run

## Использование
- **API Endpoints**
- **GET** /customers - Получить всех пользователей (только для ADMIN)
- **GET** /customers/{email} - Получить пользователя по email (для USER и ADMIN)
- **POST** /customers - Создать нового пользователя (только для ADMIN)
- **PUT** /customers/{email} - Обновить пользователя по email (только для ADMIN)
- **DELETE** /customers/{email} - Удалить пользователя по email (только для ADMIN)

## Контакты 
- **email** : den.gitelman@gmail.com
- **tg**: t.me/f0rtunaaz
