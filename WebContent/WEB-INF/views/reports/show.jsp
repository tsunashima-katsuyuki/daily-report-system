<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${report != null}">
                <c:if test="${flush != null}">
                    <div id="flush_success">
                       <c:out value="${flush}"></c:out>
                    </div>
                </c:if>
                <h2>日報 詳細ページ</h2>

                <table id="report_details">
                    <tbody>
                        <tr>
                            <th>氏名</th>
                            <td><c:out value="${report.employee.name}" /></td>
                        </tr>
                        <tr>
                            <th>日付</th>
                            <td><fmt:formatDate value='${report.report_date}' pattern='yyyy-MM-dd' /></td>
                        </tr>
                        <tr>
                            <th>内容</th>
                            <td class="report_content">
                                <c:out value="${report.content}" />
                            </td>
                        </tr>
                        <tr>
                            <th>登録日時</th>
                            <td>
                                <fmt:formatDate value="${report.created_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                        <tr>
                            <th>更新日時</th>
                            <td>
                                <fmt:formatDate value="${report.updated_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                        <tr>
                            <th>いいね！の数</th>
                            <td>
                                <c:out value="${likes_count}" />
                            </td>
                        </tr>
                    </tbody>
                </table>
                <c:choose>
                    <c:when test="${sessionScope.login_employee.id == report.employee.id}">
                        <p><a href="<c:url value='/reports/edit?id=${report.id}' />">この日報を編集する</a></p>
                    </c:when>
                    <c:when test="${sessionScope.like == null}">
                        <form method="POST" action="<c:url value='/likes?id=${report.id}' />">
                            <input type="hidden" name="_token" value="${_token}" />
                            <button type="submit" id="like">いいね！</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <form method="POST" action="<c:url value='/likes?like_id=${sessionScope.like.id}' />">
                            <input type="hidden" name="_token" value="${_token}" />
                            <button type="submit" id="like">いいね！を解除する</button>
                        </form>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <p><a href="<c:url value='/reports/index' />">一覧に戻る</a></p>
    </c:param>
</c:import>