<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<style>
	p {text-indent:50px;color: #d04a07;}
	h4 {color: #d04a07;}
</style>
<div class="clearfix" id="home_content">
	<div class="col-xs-12">
		<div id="content">
		<div class="row">
			<div class="form-div">
			<h3><spring:message code="eip.etp.header" text="Error Page : EXCEPTION"/></h3>
			
				<div class="form-elements">
					<p><spring:message code="eip.etp.msg" text=""/></p>
				</div>
				
					<c:set var="myMap" value="${command}"/>
				<div class="form-elements">
					<table class="gridtable" >
						<tr>
							<td colspan="2"><h4><spring:message code="eip.ftp.tranDetails" text="Transaction Details"/></h4></td>
						</tr>
						<tr>
							<td><label for=""><spring:message code="eip.stp.error" text="Error"/> :</label></td>
							<td><span>${myMap.get('exception')}</span></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		</div>
	</div>
</div>
