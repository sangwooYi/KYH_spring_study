 <%@ page contentType="text/html;charset=UTF-8" language="java" %>
 <html>
 <head>
    <meta charset="UTF-8">
    <title>Title</title>
 </head>
 <body>

 <!- 상대경로 현재 경로에서 마지막 / 부분만 바뀐다, 보통은 절대경로로 하는게 더 좋음. -->
 <form action="save" method="post">
    userName: <input type="text" name="userName" />
    age:      <input type="text" name="age" />
    <button type="submit">전송</button>
 </form>
 </body>
 </html>