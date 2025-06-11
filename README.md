# Wallet Service

Сервис для управления кошельками с поддержкой операций пополнения и снятия средств.

## Требования

- Java 17
- Docker и Docker Compose
- Gradle

## Запуск приложения

1. Собрать приложение:
```bash
./gradlew build
```

2. Запустить с помощью Docker Compose:
```bash
docker-compose up -d
```

Приложение будет доступно по адресу: http://localhost:8080

## API Endpoints

### Выполнить операцию с кошельком
```
POST /api/v1/wallet
Content-Type: application/json

{
    "walletId": "uuid",
    "operationType": "DEPOSIT|WITHDRAW",
    "amount": 1000
}
```

### Получить информацию о кошельке
```
GET /api/v1/wallets/{walletId}
```

## Swagger UI

Документация API доступна по адресу: http://localhost:8080/swagger-ui.html

## Настройка

Основные параметры можно настроить через переменные окружения:

- `DB_USERNAME` - имя пользователя базы данных (по умолчанию: postgres)
- `DB_PASSWORD` - пароль базы данных (по умолчанию: postgres)
- `LOG_LEVEL` - уровень логирования (по умолчанию: INFO)
