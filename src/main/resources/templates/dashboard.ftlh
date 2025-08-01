<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Панель управления</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<h1>Добро пожаловать на панель управления!</h1>

<h2>Привет, ${username}!</h2>



<form action="/logout" method="post">
    <input type="submit" value="Выйти"/>
</form>

<br>
<a href="/vacancies">Просмотреть вакансии</a>
<br>
<a href="/resumes">Просмотреть резюме</a>

</body>
</html>