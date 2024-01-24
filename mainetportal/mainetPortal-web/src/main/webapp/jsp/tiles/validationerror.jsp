<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<spring:hasBindErrors name="command">

	<script>
		function closeErrBox() {
			$('.error-div').hide();
		}
	</script>
	<!--Alert Start Here-->
	<div class="alert alert-danger alert-dismissible" role="alert" id="validationerrordiv">
		<button type="button" class="close" data-dismiss="alert"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<c:if test="${errors.errorCount gt 0}">
			<ul>
				<c:forEach items="${errors.allErrors}" var="error">
					<li id="command.errors" class="error-msg"><i
						class="fa fa-exclamation-circle"></i>&nbsp;${error.defaultMessage}</li>
				</c:forEach>
			</ul>
		</c:if>
		<script>
			$('html,body').animate({
				scrollTop : 0
			}, 'slow');
			$('.required-control').next().children().addClass('mandColorClass');
		</script>

	</div>
</spring:hasBindErrors>

<%-- 
<table class="innertable" width="100%">
	<script>
		function closeErrBox() {
			$('.error-div').hide();
		}
	</script>
	<!--Alert Start Here-->
	<c:if test="${not empty actionResponse.errorList}">
		<div class="alert alert-danger alert-dismissible" role="alert">
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<ul>
				<c:forEach items="${actionResponse.errorList}" var="error">
					<li id="command.errors" class="error-msg"><i
						class="fa fa-exclamation-circle"></i> &nbsp;${error.code}</li>
				</c:forEach>
			</ul>
	</c:if>
	<script>
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		$('.required-control').next().children().addClass('mandColorClass');
	</script>
	</div>
	<tr>
		<td></td>
	</tr>
</table>

<table class="innertable" width="100%">
	<script>
		function closeErrBox() {
			$('.error-div').hide();
		}
	</script>
	<c:if test="${not empty successMessage}">
		<div class="alert alert-success  alert-dismissible" role="alert">
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<ul>
				<li id="command.errors" class="success-msg"><i
					class="fa fa-exclamation-circle"></i> &nbsp;${successMessage}</li>
			</ul>
	</c:if>
	<script>
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		$('.required-control').next().children().addClass('mandColorClass');
	</script>
	</div>
	<tr>
		<td></td>
	</tr>
</table> --%>
