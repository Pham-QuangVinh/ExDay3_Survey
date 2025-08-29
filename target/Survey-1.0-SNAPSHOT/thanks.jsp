<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Thank you</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css">
</head>
<body class="survey">
<div class="form-card">
    <img class="logo"
         src="${pageContext.request.contextPath}/images/murachlogo.jpg"
         alt="Murach Logo"><br>

    <h1>Thank you!</h1>

    <p>
        Thanks, <strong>${user.firstName} ${user.lastName}</strong>! We
        appreciate your time completing the survey.
    </p>

    <h2>About your information:</h2>
    <ul>
        <li>Name: ${user.firstName} ${user.lastName}</li>
        <li>Email: ${user.email}</li>
        <li>Date of Birth: ${user.dateOfBirth}</li>
    </ul>

    <h2>How did you hear about us?</h2>
    <p>${user.ref}</p>

    <h2>Announcements:</h2>
    <ul>
        <li>Wants offers: ${user.wantOffers ? 'Yes' : 'No'}</li>
        <li>Wants email announcements: ${user.wantEmail ? 'Yes' : 'No'}</li>

    </ul>

    <h2>Preferred contact method:</h2>
    <p>${user.contact}</p>

    <p>
        <a href="${pageContext.request.contextPath}/index.html">Back to
            form</a>
    </p>
</div>
</body>
</html>