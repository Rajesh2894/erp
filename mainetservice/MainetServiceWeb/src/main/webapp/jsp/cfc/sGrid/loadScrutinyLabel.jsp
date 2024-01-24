<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<% response.setContentType("text/html; charset=utf-8");%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script  type="text/javascript">

$(document).ready(function()
{document.loadLabel.submit();});
</script>
<div>
    <form action="ScrutinyLabelView.html?setViewDataFromModule" name="loadLabel" method="post">
        <input type="hidden" value="${command.applicationId}" name="appId"/>
        <input type="hidden" value="${command.actionUrl}" name="actionUrl"/>
        <input type="hidden" value="${command.tempLabelId}" name="labelId"/>
        <input type="hidden" value="${command.decisionFlag}" name="flag"/>
        <input type="hidden" value="<%= request.getParameter("SecurityToken") %>" name="SecurityToken"/>
    </form>
</div>