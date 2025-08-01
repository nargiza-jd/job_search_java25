<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>All Vacancies</title>
</head>
<body>
<h1>All Vacancies</h1>

<#if vacancies?? && vacancies?size > 0>
    <ul>
        <#list vacancies as vacancy>
            <li>
                <h3><a href="/vacancies/${vacancy.id}">${vacancy.title}</a></h3>
                <p>Company: ${vacancy.company}</p>
                <p>Location: ${vacancy.location}</p>
                <p>Description: ${vacancy.description}</p>
                <hr>
            </li>
        </#list>
    </ul>
<#else>
    <p>No vacancies found.</p>
</#if>

<p><a href="/">Go to Home Page</a></p>
</body>
</html>
