<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en" dir="ltr">
<head>
<meta charset="UTF-8">
<title>Copyright</title>
<link rel="shortcut icon" href="assets/img/favicon.ico">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
</head>
 <c:forEach items="${command.getAllhtml('Copyright')}" var="Allhtml">
		${Allhtml}
	</c:forEach> 

 
