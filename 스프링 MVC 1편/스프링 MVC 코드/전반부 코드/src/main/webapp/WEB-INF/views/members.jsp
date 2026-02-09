<%@ page contentType="text/html;charset=UTF-8" language="java" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <html>
 <head>
    <meta charset="UTF-8">
    <title>Title</title>
 </head>
 <body>
 <a href="/index.html">메인</a>
 <table>
    <thead>
    <th>id</th>
    <th>userName</th>
    <th>age</th>
    </thead>
    <tbody>

    <!-- JSTL 문법 -->

    <c:choose>
      <c:when test="${empty members}">
        <p>아직 아무것도 없다 등록해라</p>
      </c:when>
      <c:otherwise>
         <c:forEach var="item" items="${members}">
             <tr>
                 <td>${item.id}</td>
                 <td>${item.userName}</td>
                 <td>${item.age}</td>
             </tr>
         </c:forEach>
      </c:otherwise>
    </c:choose>
    </tbody>
 </table>
 </body>
 </html>