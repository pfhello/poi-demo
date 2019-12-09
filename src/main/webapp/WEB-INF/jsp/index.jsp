<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<h2>Hello World!</h2>
     <a href="/down?fileName=考试安排.xls">考试安排.xls</a><br>
     <a href="/downJpg?fileName=美女.jpg">美女.jpg</a><br>
     <form action="/upload" method="post" enctype="multipart/form-data">
         <input type="file" name="file">
         <input type="submit" value="上传">
     </form>
</body>
</html>
