<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script src="../js/jquery-1.8.3.min.js"></script>
<body>
<script>
    function imgChange(a) {
        a.src="code?time="+new Date();
    }
    $.ajax({
        url:"/getImageVerifyCode",
        async:true,
        type:"GET",
        dataType:"json",
        success:function (data) {
            alert(data);
            $("#smallImg").attr("src","data:image/png;base64,"+data.smallImage);
            $("#bigImg").attr("src","data:image/png;base64,"+data.bigImage);
        }
    });
</script>
<h2>Hello World!</h2>
     <a href="/down?fileName=考试安排.xls">考试安排.xls</a><br>
     <a href="/downJpg?fileName=美女.jpg">美女.jpg</a><br>
     <form action="/upload" method="post" enctype="multipart/form-data">
         <input type="file" name="file">
         <input type="submit" value="上传">
     </form>
     <br>
    <img id="vimg" src="/code" onclick="imgChange(this)"/><<br>

    <img id="smallImg" src=""/>
    <img id="bigImg" src=""/>
</body>
</html>
