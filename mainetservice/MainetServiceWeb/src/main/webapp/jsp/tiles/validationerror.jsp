<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<spring:hasBindErrors name="command">

	<script type="text/javascript">
		function closeErrBox()
		{
			$('.error-div').hide();
		}
	</script>
   <!--Alert Start Here-->
              <div class="alert alert-danger alert-dismissible" role="alert" id="validationerrordiv">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	   	<c:if test="${errors.errorCount gt 0}">
	   		<ul id="validationerror_errorslist"> 
	   			<c:forEach items="${errors.allErrors}" var="error">
					<li id="command.errors" class="error-msg">
					<i class="fa fa-exclamation-circle"></i> &nbsp;	<spring:message code="${error.code}" arguments="${error.arguments}" text="${error.defaultMessage}"/>
					</li>					
      			</c:forEach>
      		</ul>
	  	</c:if> 
	  	<script>
	  	$('html,body').animate({ scrollTop: 0 }, 'slow');
	  	$('.required-control').next().children().addClass('mandColorClass');
	  	</script>	
		
   </div>
</spring:hasBindErrors>