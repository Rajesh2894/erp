<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en" dir="ltr">
 <c:forEach items="${command.getAllhtml('Terms & Conditions')}" var="Allhtml">
		${Allhtml}
	</c:forEach>
</html>