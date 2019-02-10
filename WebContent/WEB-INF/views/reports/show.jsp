<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>日報　詳細ページ</h2>
        <table>
            <tr>
                <th>氏名</th>
                <td><c:out value="${reports.employee.name}"/></td>
            </tr>
            <tr>
                <th>日付</th>
                <td><fmt:formatDate value="${reports.report_date}" pattern="yyyy-MM-dd"/></td>
            </tr>
            <tr>
                <th>タイトル</th>
                <td><c:out value="${reports.title}"/></td>
            </tr>
            <tr>
                <th>内容</th>
                <td><pre><c:out value="${reports.content}"/></pre></td>
            </tr>
            <tr>
                <th>登録日時</th>
                <td><fmt:formatDate value="${reports.created_at}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            </tr>
            <tr>
                <th>更新日時</th>
                <td><fmt:formatDate value="${reports.updated_at}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            </tr>
        </table>
        <c:if test="${reports.employee.code == sessionScope.login_employee.code}">
            <p><a href="<c:url value='/reports/edit?id=${reports.id}'/>">この日報を編集する</a></p>
        </c:if>
        <p><a href="<c:url value='/reports/index'/>">一覧に戻る</a></p>
    </c:param>
</c:import>