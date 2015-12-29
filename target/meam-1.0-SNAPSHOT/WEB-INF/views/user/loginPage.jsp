<%-- 
    Document   : loginPage
    Created on : 16 Dec, 2015, 12:40:03 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <script type="text/javascript">

        $(function () {
            $("#dialog").dialog({
                autoOpen: false,
                height: 350,
                width: 756,
                modal: true,
                buttons: {
                    "Save": function () {
                        ajaxCall();
                    },
                    Cancel: function () {
                        $(this).dialog("close");
                    },
                    "Reset": function () {
                        $('#rmks').val('');
                        $('#check').attr('checked', false);
                    }
                },
                close: function () {
                    $('#curRowId').val('');
                    $('#rmks').val('');
                    $('#check').attr('checked', false);
                }
            });
        });
        function open(){
            alert("OK")
            $("#dialog").dialog("open");
        }
    </script>
    <body>
        <div id="dialog" ></div>
        <h1 onclick="open()">Hello World!  RATUL</h1>
    </body>
</html>
