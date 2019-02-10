<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"/>
            </div>
        </c:if>
        <h2>日報　一覧</h2>
        <table id="report_list">
            <tr>
                <th class="report_name">氏名</th>
                <th class="report_date">日付</th>
                <th class="report_title">タイトル</th>
                <th class="report_action">操作</th>
            </tr>
            <c:forEach var="report" items="${reports}" varStatus="status">
                <tr class="row${status % 2}">
	               <td class="report_name"><c:out value="${report.employee.name}"/></td>
	               <td class="report_date"><fmt:formatDate value="${report.report_date}" pattern="yyyy-MM-dd"/></td>
	               <td class="report_title"><c:out value="${report.title}"/></td>
	               <td class="report_action"><a href="<c:url value='/reports/show?id=${report.id}'/>">詳細を見る</a></td>
                </tr>
            </c:forEach>
        </table>
        <c:forEach var="p" begin="1" end="${((reports_count - 1) / 15) + 1}" step="1">
            <c:choose>
                <c:when test="${p == page}">
                    <c:out value="${p}"/>&nbsp;
                </c:when>
                <c:otherwise>
                    <a href="<c:url value='/reports/index?page=${p}'/>"><c:out value="${p}"/>&nbsp;</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <p><a href="<c:url value='/reports/new'/>">新規日報の登録</a></p>
    </c:param>
</c:import>