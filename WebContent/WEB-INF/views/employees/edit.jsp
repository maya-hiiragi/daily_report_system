<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>id：<c:out value="${employees.id}"/>>の従業員情報　編集ページ</h2>
        <form method="post" action="<c:url value='/employees/update?id=${employees.id}'/>">
            <c:import url="../layout/_form.jsp"></c:import>
        </form>
        <p><a href="#" onclick="delConfirm()">この従業員情報を削除する</a></p>
        <p><a href="<c:url value='/employees/index'/>">一覧に戻る</a></p>

        <script>
        function delConfirm(){
        	if(confirm("このデータを削除してもよろしいですか？")){
        		document.forms[1].submit();
        	}
        }
        </script>
        <form method="post" action="<c:url value='/employees/delete?id=${employees.id}'/>">
            <input type="hidden" name="_token" value="<c:out value='${_token}'/>"/>
        </form>
    </c:param>
</c:import>