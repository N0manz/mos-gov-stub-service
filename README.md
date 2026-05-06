# payment-stub-service

Сервис заглушка для генерации случайного платежа.

## Базовый URL
http://localhost:8081/api/v1

## API

### GET /payment
Возвращает случайный платеж (с задержкой 200 мс).

Пример ответа:
```json
{
  "id": "a3f1c2d4-1234-4abc-9def-000000000001",
  "fromAccount": "ACC-042731",
  "toAccount": "ACC-198203",
  "amount": 67.42,
  "currency": "USD",
  "status": "COMPLETED",
  "description": "Online subscription",
  "createdAt": "2024-05-01T12:00:00Z"
}