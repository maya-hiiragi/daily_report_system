<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${employees != null}">
		        <h2>id：<c:out value="${employees.id}"/>の従業員情報　詳細ページ</h2>
		        <table>
		            <tr>
		                <th>社員番号</th>
		                <td><c:out value="${employees.code}"/></td>
		            </tr>
		            <tr>
		                <th>氏名</th>
		                <td><c:out value="${employees.name}"/></td>
		            </tr>
		            <tr>
		                <th>権限</th>
		                <td>
		                    <c:choose>
		                        <c:when test="${employees.admin_flag == 0}">
		                            <c:out value="一般"/>
		                        </c:when>
		                        <c:otherwise>
		                            <c:out value="管理者"/>
		                        </c:otherwise>
		                    </c:choose>
		                </td>
		            </tr>
		            <tr>
		                <th>登録日時</th>
		                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${employees.created_at}"/></td>
		            </tr>
		            <tr>
		                <th>更新日時</th>
		                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${employees.updated_at}"/></td>
		            </tr>
		        </table>
		        <p><a href="<c:url value='/employees/edit?id=${employees.id}'/>">この従業員情報を編集する</a></p>
	        </c:when>
	        <c:otherwise>
	           <h2>お探しのデータは見つかりませんでした。</h2>
	        </c:otherwise>
        </c:choose>
        <p><a href="<c:url value='/employees/index'/>">一覧に戻る</a></p>
    </c:param>
</c:import>