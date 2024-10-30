<!DOCTYPE html>
<html>
<head>
    <title>Photo Blog</title>
</head>
<body>
<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post">
    <input type="submit" value="Logout" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<h2>Upload Photos</h2>
<form:form method="POST" enctype="multipart/form-data" modelAttribute="photoForm">
    <form:label path="description">Description</form:label><br/>
    <form:textarea path="description" rows="5" cols="30"/><br/><br/>
    <b>Attachments</b><br/>
    <input type="file" name="photos" multiple="multiple" accept="image/png, image/jpeg"/><br/><br/>
    <input type="submit" value="Submit"/>
</form:form>
<a href="<c:url value="/photo" />">Return to list photos</a>
</body>
</html>
