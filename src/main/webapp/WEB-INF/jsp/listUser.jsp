<!DOCTYPE html>
<html>
<head><title>Photo Blog User Management</title></head>
<body>
<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post">
    <input type="submit" value="Log out" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<br /><br />
<a href="<c:url value="/photo" />">Return to list photos</a>
<h2>Users</h2>
<a href="<c:url value="/user/create" />">Create a User</a><br /><br />
<c:choose>
    <c:when test="${fn:length(ticketUsers) == 0}">
        <i>There are no users in the system.</i>
    </c:when>
    <c:otherwise>
        <table>
            <tr><th>Username</th><th>Password</th> <th>Email</th><th>Telephone</th>
               <th>Description</th><th>Roles</th><th>History</th><th>Action</th></tr>
            <c:forEach items="${ticketUsers}" var="user"><tr>
                <td>${user.username}</td>
                <td>${fn:substringAfter(user.password, '{noop}')}</td>
                <td><c:out value="${user.email}" /></td>
                <td><c:out value="${user.tel}" /></td>
                <td><c:out value="${user.description}" /></td>
                <td>
                    <c:forEach items="${user.roles}" var="role" varStatus="status">
                        <c:if test="${!status.first}">, </c:if>
                        ${role.role}
                    </c:forEach>
                </td>
                <td>[<a href="<c:url value="/history/photo/${user.username}" />">Photos</a>] [<a href="<c:url value="/history/comment/${user.username}" />">Comments</a>]</td>
                <td>[<a href="<c:url value="/user/edit/${user.username}" />">Edit</a>] [<a href="<c:url value="/user/delete/${user.username}" />">Delete</a>]</td>
            </tr></c:forEach>
        </table>
    </c:otherwise>
</c:choose>
</body></html>