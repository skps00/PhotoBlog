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
<security:authorize access="hasRole('ADMIN')">
    [<a href="<c:url value="/photo/delete/${photo.id}" />">Delete</a>]<br/><br/>
</security:authorize>
<i>Author Name - <a href="<c:url value="/profile/${photo.customerName}" />"><c:out value="${photo.customerName}"/></a></i><br/>
<img src="<c:url value="/photo/download/${photo.id}" /> "/><br/>
Photo uploaded on <fmt:formatDate value="${photo.createTime}"
                                  pattern="EEE, d MMM yyyy HH:mm:ss Z"/><br/>
Description:<br/>
<c:out value="${photo.description}"/><br/><br/>

Comments:<br/>
<security:authorize access="hasAnyRole('USER', 'ADMIN')">
    <c:url var="commentUrl" value="/photo/comment/new/${photoId}"/>
    <form:form method="POST" modelAttribute="commentForm" action="${commentUrl}">
        <form:label path="content">Post a comment:</form:label><br/>
        <form:textarea path="content" rows="5" cols="30"/><br/><br/>
        <input type="submit" value="Submit"/>
    </form:form>
</security:authorize>
<c:choose>
    <c:when test="${empty comments}">No comment now.</c:when>
    <c:otherwise>
        <c:forEach items="${comments}" var="comment">
            <i><a href="<c:url value="/profile/${comment.username}" />">${comment.username}</a></i> commented on
            <fmt:formatDate value="${comment.createTime}"  pattern="EEE, d MMM yyyy HH:mm:ss Z"/>:<br/>
            <c:out value="${comment.content}"/>
            <security:authorize access="hasRole('ADMIN')">
                [<a href="<c:url value="/photo/comment/delete/${photo.id}/${comment.id}" />">Delete</a>]
            </security:authorize>
            <br/><br/>
        </c:forEach>
    </c:otherwise>
</c:choose>
<br/><br/>
<a href="<c:url value="/photo" />">Return to list photos</a>
</body>
</html>
