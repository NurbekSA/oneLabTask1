# Investment Management System


## Основные возможности
- CRUD-операции для всех сущностей.
- Инициализация базы данных тестовыми данными.
- Встроенная база данных H2 для тестирования и демонстрации.
- Пример использования Spring AOP

## Practice 3. The transaction is in the Investment Service create method
### Method Description: `create`
The `create` method processes a new investment record. It performs the following steps:
1. **Investor Validation**: Retrieves the investor associated with the investment. If the investor is not found, it returns a `BAD_REQUEST` response.
2. **Checks**: 
   - Verifies if the investor has passed verification. If not, it returns a `BAD_REQUEST` response indicating the investor has not been verified.
   - Checks if the investor has linked cards. If none are found, it returns a `BAD_REQUEST` response.
3. **Investment Initialization**: Sets various properties of the investment, such as marking it as unpaid and inactive, and sets the investment date to the current timestamp.
4. **Save Investment**: Saves the investment to the database.
5. **Logging**: Logs a success message indicating the investment was saved.
6. **Response**: Returns an `OK` response with the created investment object upon successful processing.


## Description of the project


### консоль h2 доступен по url: http://localhost:8080/h2-console. Url бд - url: jdbc:h2:mem:testdb

## Структура проекта

### 1. **Модели**
   - **`AdminModel`**: модель для администраторов системы. Хранит информацию об имени пользователя, пароле, email, роли и статусе активности.
   - **`BusinessModel`**: модель для бизнесов, содержащая информацию о названии, BIN, адресе, типе платежной системы, отрасли и директоре.
   - **`CardModel`**: модель для карт, привязанных к инвесторам, включает номер карты, имя держателя и дату истечения срока действия.
   - **`InvestmentModel`**: модель для инвестиций, связанная с инвестором и заказом. Хранит информацию о сумме, дате и статусе инвестиции.
   - **`InvestorModel`**: модель для инвесторов, содержащая ИИН, ФИО, номер телефона, email, адрес и тип инвестора (физическое или юридическое лицо).
   - **`OrderModel`**: модель для заказов, связанных с инвестором, включает информацию о типе инвестиции, целевой и фактической сумме, валюте, сроке погашения, статусе и залоге.

### 2. **Репозитории**
   - **`AdminRepo`**, **`BusinessRepo`**, **`CardRepo`**, **`InvestmentRepo`**, **`InvestorRepo`**, **`OrderRepo`**: используются для выполнения CRUD-операций для соответствующих моделей.
   - Реализованы методы поиска по ключевым полям, такие как `findByUsername`, `findByIin`, `findByInvestor`, и другие.


### 4. **Аспекты (AOP)**
   - **`InvestorAspect`**: реализует обработку событий для поиска инвесторов, включая:
     - **`@Before`**: выводит сообщение о начале поиска.
     - **`@AfterReturning`**: выводит информацию об инвестициях, связанных с найденным инвестором.
     - **`@AfterThrowing`**: обрабатывает исключения, возникшие во время поиска.
     - **`@Around`**: измеряет время выполнения метода и обрабатывает исключения.

### 5. **Загрузчик данных (`DataLoader`)**
   - Используется для инициализации базы данных тестовыми данными при запуске приложения.
   - Создает и сохраняет данные для всех сущностей, включая администраторов, бизнесы, карты, инвесторов, заказы и инвестиции.
