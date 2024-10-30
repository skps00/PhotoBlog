<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Photo Blog</title>
</head>
<body>
<h2>Photo history of <c:out value="${username}"/></h2>
<c:choose>
    <c:when test="${empty photos}">No photos now.</c:when>
    <c:otherwise>
        <table>
            <tr><th>Photo link</th><th>Author</th><th>Description</th><th>Upload Timestamp</th></tr>
            <c:forEach var="photo" items="${photos}">
                <tr>
                    <td><a href="<c:url value="/photo/view/${photo.id}" />">Link</a></td>
                    <td><a href="<c:url value="/profile/${photo.customerName}" />" >${photo.customerName}</a></td>
                    <td>${photo.description}</td>
                    <td><fmt:formatDate value="${photo.createTime}"  pattern="EEE, d MMM yyyy HH:mm:ss Z"/></td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>
<br/><br/>
<a href="<c:url value="/user" />">Return to list user</a>
</body>
</html>