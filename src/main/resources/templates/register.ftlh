<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<#import "/spring.ftl" as spring/>

<div class="container">
    <h1>Регистрация</h1>

    <#if errorMessage??>
        <p class="error">${errorMessage}</p>
    </#if>

    <form action="/register" method="post">
        <label for="name">Имя:</label>
        <input type="text" id="name" name="name" value="${(userRegistrationDto.name)!}" required>
        <@spring.bind "userRegistrationDto.name">
            <#if spring.status.error><span class="error">${spring.status.errorMessage}</span></#if>
        </@spring.bind>
        <br>

        <label for="surname">Фамилия:</label>
        <input type="text" id="surname" name="surname" value="${(userRegistrationDto.surname)!}" required>
        <@spring.bind "userRegistrationDto.surname">
            <#if spring.status.error><span class="error">${spring.status.errorMessage}</span></#if>
        </@spring.bind>
        <br>

        <label for="age">Возраст:</label>
        <input type="number" id="age" name="age" value="${(userRegistrationDto.age)!}" min="0">
        <@spring.bind "userRegistrationDto.age">
            <#if spring.status.error><span class="error">${spring.status.errorMessage}</span></#if>
        </@spring.bind>
        <br>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" value="${(userRegistrationDto.email)!}" required>
        <@spring.bind "userRegistrationDto.email">
            <#if spring.status.error><span class="error">${spring.status.errorMessage}</span></#if>
        </@spring.bind>
        <br>

        <label for="password">Пароль:</label>
        <input type="password" id="password" name="password" required>
        <@spring.bind "userRegistrationDto.password">
            <#if spring.status.error><span class="error">${spring.status.errorMessage}</span></#if>
        </@spring.bind>
        <br>

        <label for="accountType">Тип аккаунта:</label>
        <select id="accountType" name="accountType">
            <option value="applicant" <#if (userRegistrationDto.accountType!) == "applicant">selected</#if>>Соискатель</option>
            <option value="employer" <#if (userRegistrationDto.accountType!) == "employer">selected</#if>>Работодатель</option>
        </select>
        <@spring.bind "userRegistrationDto.accountType">
            <#if spring.status.error><span class="error">${spring.status.errorMessage}</span></#if>
        </@spring.bind>
        <br>

        <button type="submit">Зарегистрироваться</button>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</div>
</body>
</html>
