<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
<h1>Login Page</h1>
<#if loginError??>
    <p class="error">Invalid username or password!</p>
</#if>
<#if logoutMessage??>
    <p class="message">You have been logged out.</p>
</#if>
<#if registrationSuccess??>
    <p class="message">Registration successful! You can now log in.</p>
</#if>
<form action="/login" method="post">
    <div>
        <label for="username">Username:</label>
        <input type="text" id="username" name="username"/>
    </div>
    <div>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password"/>
    </div>
    <div>
        <input type="submit" value="Log In"/>
    </div>
</form>
<p>New user? <a href="/register">Register here</a></p>
</body>
</html>