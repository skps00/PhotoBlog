<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
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
<security:authorize access="hasAnyRole('USER', 'ADMIN')">
  <a href="<c:url value="/photo/upload" />">Upload your photos</a><br/>
</security:authorize>
<h1>Profile Page of <c:out value="${user.username}" /></h1>

<br/>
Description: <c:out value="${user.description}"/><br/>
<security:authorize access="hasRole('ADMIN') or ( hasRole('USER') and
                principal.username=='${user.username}')">
  [<a href="<c:url value="/profile/edit/${user.username}" />">Edit</a>]<br/>
</security:authorize>
<br/>

<c:choose>
  <c:when test="${fn:length(photos) == 0}">
    <i>There are no photos by ${user.username} in the system.</i>
  </c:when>
  <c:otherwise>
    <h2>Photos by <c:out value="${user.username}" /></h2>
    <c:forEach items="${photos}" var="entry">
      <a href="<c:url value="/photo/view/${entry.id}" />"><img src="<c:url value="/photo/download/${entry.id}" /> "/></a>
      Uploaded on <fmt:formatDate value="${entry.createTime}"
                                  pattern="EEE, d MMM yyyy HH:mm:ss Z"/>
      <security:authorize access="hasRole('ADMIN')">
        [<a href="<c:url value="/photo/delete/${entry.id}" />">Delete</a>]
      </security:authorize>
      <br/><br/>
    </c:forEach>
  </c:otherwise>
</c:choose>
<br/><a href="<c:url value="/photo" />">Return to list photos</a>
</body>
</html>
