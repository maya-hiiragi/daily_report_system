<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"/>
            </div>
        </c:if>
        <c:if test="${errors != null}">
            <div id="flush_error">
                <c:forEach var="error" items="${errors}">
                    ・<c:out value="${error}"/><br>
                </c:forEach>
            </div>
        </c:if>
        <h2>ログイン</h2>
        <form method="post" action="<c:url value='/login'/>">
            <label for="code">社員番号</label><br>
            <input type="text" name="code" value="<c:out value='${code}'/>"/><br>
            <br>
            <label for="password">パスワード</label><br>
            <input type="password"  name="password" value="<c:out value='${password}'/>"/><br>
            <br>
            <input type="hidden" name="_token" value="<c:out value='${_token}'/>"/>
            <button type="submit">ログイン</button>
        </form>
    </c:param>
</c:import>