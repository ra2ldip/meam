<%-- 
    Document   : menuPage
    Created on : Jan 6, 2016, 12:23:15 PM
    Author     : ratul
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <link rel="stylesheet" href="/css/ui.jqgrid.css" type="text/css">
            <title>JSP Page</title>
            <script type="text/javascript">
                $(function () {
                    $("#menuDiv").jqGrid({
                        //                           url: "/bbuGridView",
                        data: "${menus}",
                        datatype: "local",
                        cache: false,
                        colNames: ["Item Code", "Details Id", "Description", ],
                        colModel: [
                            {name: 'name', sortable: false, editable: true, width: 8, align: 'center', edittype: 'text', editoptions: {size: 30}},
                            {name: 'id', sortable: false, editable: true, width: 5, align: 'center', edittype: 'text', editoptions: {defaultValue: '0'}, key: true, hidden: true},
                            {name: 'url', index: 'description', sortable: false, align: 'left', editable: true, width: 25, edittype: 'textarea', editoptions: {rows: "3", cols: 50}},
                        ],
                    })
                })
            </script>
        </head>
        <body>
            <div id="menuDiv" style="background:#EEEEEE;"></div>
        </body>
    </html>
