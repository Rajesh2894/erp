<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<% response.setContentType("text/html; charset=utf-8");%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script  type="text/javascript">
$( document ).ready(function()
{	
		document.slabel.submit();	    		
});

</script>
<div>
    <form action="${command.actionUrl}"  id="slabel" name="slabel" method="post">
    </form>
</div>