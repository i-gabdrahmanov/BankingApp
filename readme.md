## Приложение SberBank

Данное приложение имитирует базовую функциональность бэкенда базового банковскго 
приложения

### Методы API

- api/person/register - регистрация нового пользователя
- api/person/{id} - возврат данных по пользователю
- api/person/{id}/put - обновлене сущности пользователя
- api/person/{id}/delete - обновлене сущности пользователя


- api/account/{id} - получение информации о банковском аккаунте
- api/account/{personId}/create - создание банковского аккаунта, его привязка к пользователю
- api/account/{accountId}/sendTo - отправка средств на другой банковский аккаунт
- api/account/{accountId}/fillMoney - поплнение средств
  
Starting: docker compose up --build
