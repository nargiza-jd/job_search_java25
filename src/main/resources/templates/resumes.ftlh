<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Resumes</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="container">
    <h1>Your Resumes</h1>
    <#if resumes?? && (resumes?size > 0)>
        <p>You have ${resumes?size} resumes saved.</p>
        <ul>
            <#list resumes as resume>
                <li>
                    <h3>${resume.title}</h3>
                    <p><strong>Experience:</strong> ${resume.experience} years</p>
                    <p><strong>Skills:</strong>
                        <#list resume.skills as skill>
                            <span class="skill-tag">${skill}</span>
                        </#list>
                    </p>
                    <a href="/resumes/${resume.id}">View Details</a>
                </li>
            </#list>
        </ul>
    <#else>
        <p>YУ вас пока нет сохраненных резюме. <a href="/resumes/add">Создайте его сейчас</a>.</p>
    </#if>
</div>
</body>
</html