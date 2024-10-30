<!DOCTYPE html>
<html>
<head>
  <title>Photo Blog</title>
</head>
<body>
<c:url var="logoutUrl" value="/logout"/>
<security:authorize access="hasAnyRole('USER', 'ADMIN')">
  <form action="${logoutUrl}" method="post">
    <input type="submit" value="Logout" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  </form>
  <a href="<c:url value="/profile/" />">View my profile</a><br/>
</security:authorize>
<security:authorize access="!hasAnyRole('USER', 'ADMIN')">
  <a href="<c:url value="/login"/>">Login</a><br/>
  <a href="<c:url value="/register" />">Register</a><br/>
</security:authorize>
<h2>Photo Blog</h2>
<security:authorize access="hasRole('ADMIN')">
  <a href="<c:url value="/user" />">Manage User Accounts</a><br /><br />
</security:authorize>
<security:authorize access="hasAnyRole('USER', 'ADMIN')">
<a href="<c:url value="/photo/upload" />">Upload your photos</a><br/><br/>
</security:authorize>
<c:choose>
  <c:when test="${fn:length(photoList) == 0}">
    <i>There are no photos in the system.</i>
  </c:when>
  <c:otherwise>
    <c:forEach items="${photoList}" var="entry">
      <a href="<c:url value="/photo/view/${entry.id}" />"><img src="<c:url value="/photo/download/${entry.id}" /> "/></a>
      <security:authorize access="hasRole('ADMIN')">
        [<a href="<c:url value="/photo/delete/${entry.id}" />">Delete</a>]
      </security:authorize>
      <br/>
    </c:forEach>
  </c:otherwise>
</c:choose>
</body>
</html>
