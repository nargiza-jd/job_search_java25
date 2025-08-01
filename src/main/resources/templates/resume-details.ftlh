<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Детали резюме: ${resume.name!}</title>
    <link rel="stylesheet" href="/css/style.css">
    <style>
        body { font-family: sans-serif; margin: 20px; background-color: #f4f4f4; }
        .container { background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); max-width: 800px; margin: 20px auto; }
        h1 { color: #333; text-align: center; }
        h2 { color: #555; border-bottom: 1px solid #ddd; padding-bottom: 5px; margin-top: 20px; }
        p { margin-bottom: 10px; }
        strong { color: #007bff; }
        ul { list-style-type: none; padding: 0; }
        li { background-color: #f9f9f9; border: 1px solid #eee; padding: 10px; margin-bottom: 10px; border-radius: 4px; }
        .back-link { display: block; text-align: center; margin-top: 30px; }
        .back-link a { color: #007bff; text-decoration: none; font-weight: bold; }
        .back-link a:hover { text-decoration: underline; }
    </style>
</head>
<body>
<div class="container">
    <h1>Детали резюме: ${(resume.name)!}</h1>

    <#if resume??>
        <p><strong>Название:</strong> ${(resume.name)!}</p>
        <p><strong>Зарплата:</strong> ${(resume.salary)!} KGS</p>
        <p><strong>Категория ID:</strong> ${(resume.categoryId)!}</p>
        <p><strong>ID соискателя:</strong> ${(resume.applicantId)!}</p>

        <h2>Образование</h2>
        <#if resume.educationList?? && resume.educationList?size > 0>
            <ul>
                <#list resume.educationList as edu>
                    <li>
                        <strong>Учебное заведение:</strong> ${(edu.institution)!}<br>
                        <strong>Степень:</strong> ${(edu.degree)!}<br>
                        <strong>Год окончания:</strong> ${(edu.graduationYear)!}
                    </li>
                </#list>
            </ul>
        <#else>
            <p>Нет данных об образовании.</p>
        </#if>

        <h2>Опыт работы</h2>
        <#if resume.workExperienceList?? && resume.workExperienceList?size > 0>
            <ul>
                <#list resume.workExperienceList as work>
                    <li>
                        <strong>Компания:</strong> ${(work.companyName)!}<br>
                        <strong>Должность:</strong> ${(work.jobTitle)!}<br>
                        <strong>Начало:</strong> ${(work.startDate)!}<br>
                        <strong>Конец:</strong> <#if work.endDate??>${(work.endDate)!}<#else>По настоящее время</#if><br>
                        <strong>Описание:</strong> ${(work.description)!}
                    </li>
                </#list>
            </ul>
        <#else>
            <p>Нет данных об опыте работы.</p>
        </#if>
    <#else>
        <p>Резюме не найдено.</p>
    </#if>

    <div class="back-link">
        <a href="/resumes/my">Вернуться к моим резюме</a>
    </div>
</div>
</body>
</html>