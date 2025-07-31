<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<h1>Регистрация</h1>

<#if errorMessage??>
    <p style="color: red;">${errorMessage}</p>
</#if>

<form action="/register" method="post">
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" value="${(userRegistrationDto.email)!}" required>
    <br>
    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password" required>
    <br>
    <label for="accountType">Тип аккаунта:</label>
    <select id="accountType" name="accountType">
        <option value="APPLICANT" <#if (userRegistrationDto.accountType!) == "APPLICANT">selected</#if>>Соискатель</option>
        <option value="EMPLOYER" <#if (userRegistrationDto.accountType!) == "EMPLOYER">selected</#if>>Работодатель</option>
    </select>
    <br>
    <button type="submit">Зарегистрироваться</button>
</form>

<p>Уже есть аккаунт? <a href="/login">Войти</a></p>
</body>
</html>