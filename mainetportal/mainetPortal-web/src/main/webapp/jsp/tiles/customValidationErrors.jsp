<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
			
			<c:if test="${fn:length(command.errors) gt 0}">
				<script>
					function closeErrBox()
					{
						$('.error-div').remove();
					}
				</script>
			   <div class="error-div">
			   
			  
				   	<div class="closeme">
						<img alt="Close" title="Close" src="css/images/close.png" onclick="closeErrBox()" width="32"/>
					</div>
				   	<ul>
				   		<li id="command.errors" class="error-msg">
				   			<c:forEach items="${command.errors}" var="error">
			        			<spring:message text="${error}"/>
			        			<br/>
			      			</c:forEach>
			      		</li>
					</ul>
			   </div>
			 </c:if> 