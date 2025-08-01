<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Создать резюме</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<#import "/spring.ftl" as spring/>

<div class="container">
    <h1>Создать новое резюме</h1>

    <#if errorMessage??>
        <p class="error">${errorMessage}</p>
    </#if>

    <form action="/resumes/new" method="post">
        <label for="name">Название резюме:</label>
        <input type="text" id="name" name="name" value="${(resumeCreateDto.name)!}" required>
        <@spring.bind "resumeCreateDto.name">
            <#if spring.status.error><span class="error">${spring.status.errorMessage}</span></#if>
        </@spring.bind>
        <br>

        <label for="salary">Желаемая зарплата (KGS):</label>
        <input type="number" id="salary" name="salary" value="${(resumeCreateDto.salary)!}" min="0">
        <@spring.bind "resumeCreateDto.salary">
            <#if spring.status.error><span class="error">${spring.status.errorMessage}</span></#if>
        </@spring.bind>
        <br>

        <label for="categoryId">Категория:</label>
        <select id="categoryId" name="categoryId" required>
            <option value="">Выберите категорию</option>
            <#if categories??>
                <#list categories as cat>
                    <option value="${(cat.id)!}" <#if (resumeCreateDto.categoryId!) == cat.id>selected</#if>>
                        ${(cat.name)!}
                    </option>
                </#list>
            </#if>
        </select>
        <@spring.bind "resumeCreateDto.categoryId">
            <#if spring.status.error><span class="error">${spring.status.errorMessage}</span></#if>
        </@spring.bind>
        <br>

        <button type="submit">Создать резюме</button>
    </form>
    <p><a href="/resumes/my">Назад к моим резюме</a></p>
</div>
</body>
</html>
