<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Список вакансий</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<h1>Доступные вакансии</h1>

<#if vacancies?has_content > 0>
    <ul>
        <#list vacancies as vacancy>
            <li>
                <h3>${vacancy.title!}</h3>
                <p>Компания: ${vacancy.companyName!}</p>
                <p>Категория: <#if vacancy.category??>${vacancy.category.name!}<#else>Не указана</#if></p>
                <p>Описание: ${vacancy.description!}</p>
                <p>Зарплата: ${vacancy.salary!}</p>
                <a href="/vacancies/${vacancy.id!}">Подробнее</a>
            </li>
        </#list>
    </ul>
<#else>
    <p>На данный момент вакансий нет.</p>
</#if>

<a href="/dashboard">Вернуться на панель управления</a>
</body>
</html>