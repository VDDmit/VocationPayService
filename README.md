**Приложение "Калькулятор отпускных".**

Примеры запросов для тестирования:

    curl "http://localhost:8080/calculate?averageSalary=70000&vacationDays=5&vacationDates=2025-05-01,2025-05-02,2025-05-06" | jq
    
    curl -s "http://localhost:8080/calculate?averageSalary=70000&vacationDays=5" | jq

Мой ключ лежит в .env, ваш можно получить на https://calendarific.com

Поле для вставки в .env:

    CALENDARIFIC_API_KEY=