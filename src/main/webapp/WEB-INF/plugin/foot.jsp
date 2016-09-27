<%--
  Created by IntelliJ IDEA.
  User: XuGuang
  Date: 2016/9/27
  Time: 15:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<div id="footer">
    <div class="container">
        <p class="text-muted text-center">&copy; YiviS 2016</p>
    </div>
</div>
<script type="text/javascript">
    /*$(window).on('load', function () {
     $('.selectpicker').selectpicker();
     });*/
    function showModle(title,url){
        if(title=="请假流程定义"){
            $.get(url,function(data){
                $('.modal-body').text(data);
            });
        }else if(title=="请假流程图"){
            $('.modal-body').html('<img width="100%"alt="'+title+'" src="'+url+'">');
        }
        $('.modal-title').html(title);
        $('#pdfModal').modal({keyboard: false});
    }
</script>
</body>
</html>

