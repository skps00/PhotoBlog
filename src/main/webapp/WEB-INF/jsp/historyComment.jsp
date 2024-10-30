
<!DOCTYPE html>
<html>
<head>
    <title>Photo Blog</title>
</head>
<body>
<h2>Comment history of <c:out value="${username}"/></h2>
<c:choose>
    <c:when test="${empty comments}">No comment now.</c:when>
    <c:otherwise>
        <table>
            <tr><th>Photo link</th><th>Comment</th><th>Timestamp</th></tr>
            <c:forEach var="comment" items="${comments}">
                <tr>
                    <td><a href="<c:url value="/photo/view/${comment.photoId}" />">Link</a></td>
                    <td>${comment.content}</td>
                    <td><fmt:formatDate value="${comment.createTime}"  pattern="EEE, d MMM yyyy HH:mm:ss Z"/></td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>
<br/><br/>
<a href="<c:url value="/user" />">Return to list user</a>
</body>
</html>