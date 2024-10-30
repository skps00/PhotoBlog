<!DOCTYPE html>
<html>
<head>
  <title>Photo Blog</title>
</head>
<body>
<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post">
  <input type="submit" value="Log out" />
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<h2>Edit User ${user.username}</h2>
<form:form method="POST" modelAttribute="userForm">
  <form:label path="password">Password</form:label><br/>
  <form:input type="text" path="password"/><br/><br/>
  <form:label path="email">Email</form:label><br/>
  <form:input type="text" path="email"/><br/><br/>
  <form:label path="tel">Telephone</form:label><br/>
  <form:input type="text" path="tel"/><br/><br/>
  <form:label path="description">Description</form:label><br/>
  <form:textarea path="description" rows="5" cols="30"/><br/><br/>
  <security:authorize access="hasRole('ADMIN')">
    <form:label path="roles">Roles</form:label><br/>
    <form:checkbox path="roles" value="ROLE_USER" />ROLE_USER
    <form:checkbox path="roles" value="ROLE_ADMIN"/>ROLE_ADMIN
    <br/>
  </security:authorize>
  <br/>
  <input type="submit" value="Edit User"/>
</form:form>
<a href="<c:url value="/user" />">Return to list user</a>
</body>
</html>

