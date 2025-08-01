<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Вход</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>

<div class="container">
    <h1>Вход</h1>

    <#if errorMessage??>
        <p class="error">${errorMessage}</p>
    </#if>

    <form action="/login" method="post">
        <label for="email">Email:</label>
        <input type="email" id="email" name="username" required>
        <br>

        <label for="password">Пароль:</label>
        <input type="password" id="password" name="password" required>
        <br>

        <button type="submit">Войти</button>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
    </form>
    <br>
    <p>Нет аккаунта? <a href="/register">Зарегистрироваться</a></p>
</div>
</body>
</html>
