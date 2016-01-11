<%-- 
    Document   : layout
    Created on : 16 Dec, 2015, 12:35:39 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="/images/RS.ico"> 
        <title><tiles:getAsString name="title"/></title>
        <script type="text/javascript" src="/scripts/jquery-2.1.4.js"></script>
                <script type="text/javascript" src="/scripts/sdk.js"></script>
          <!--<link rel="stylesheet" type="text/css" href="/css/jquery.mobile-1.4.5.css" />-->
        <link rel="stylesheet" type="text/css" href="/css/jquery.mobile-1.4.5.min.css" />
        
        
        
     
        
        
        <title>JSP Page</title>
    </head>
    <body style="background:  #e1e1e8;">
        <table  id="layoutTab" width="100%"  border="0"  cellspacing="0" cellpadding="0">
            <!--_BEGIN_OF_TILES_BLOCK_-->
            <%
                if (!request.getParameterMap().containsKey("__popUp__")) {
            %>

            <tr>
                <td colspan="1" class="ui-link ui-btn ui-btn-active">
                    <tiles:insertAttribute name="header" />
                </td>
            </tr>
            <%}%>
            <!--_END_OF_TILES_BLOCK_-->
            <tr valign="top">
                <!--_BEGIN_OF_TILES_BLOCK_-->
                <!--                <td height="" width="222px" >
                <%--<tiles:insertAttribute name="menu" />--%>
                <%--<tiles:insertAttribute name="menu_side" />--%>
            </td>-->
                <!--_END_OF_TILES_BLOCK_-->
                <td valign="top"  align="center" id="height">
                    <tiles:insertAttribute name="body" />
                </td>
            </tr>
            <%
                if (!request.getParameterMap().containsKey("__popUp__")) {
            %>
            <tr>
                <td colspan="1" align="center" style="vertical-align: middle;">
                    <div  style="background-color: rgb(235, 240, 243);width: 90%;text-align: center;margin: 0px;padding: 0px;padding-top: 1px;padding-bottom: 1px;"> <tiles:insertAttribute name="footer" /></div>
                </td>
            </tr>
            <%}%>
        </table>
    </body>
</html>
