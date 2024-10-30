<!DOCTYPE html>
<html>
<head><title>Photo Blog</title></head>
<body>
<security:authorize access="hasAnyRole('USER', 'ADMIN')">
<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post">
  <input type="submit" value="Log out"/>
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
</security:authorize>
<h2>Create a User</h2>
<form:form method="POST" modelAttribute="user">
  <form:label path="username">Username</form:label><br/>
  <form:input type="text" path="username"/><br/><br/>
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
    <form:checkbox path="roles" value="ROLE_USER"/>ROLE_USER
    <form:checkbox path="roles" value="ROLE_ADMIN"/>ROLE_ADMIN
    <br/>
  </security:authorize>
  <br/>
  <input type="submit" value="Create User"/>
</form:form>
</body>
</html>