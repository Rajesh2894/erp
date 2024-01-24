<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/mainet/file-upload.js"></script>
<script src="js/mainet/validation.js"></script>
<script src="js/water/water-disconnection.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="" text="Solid WasteCustomer Info" /></h2>
			<apptags:helpDoc url="SolidWasteCustomerInfo.html"/>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith"/><i class="text-red-1">*</i><spring:message code="water.ismandtry"/></span>
			</div>
			<div class="panel-group accordion-toggle"
				id="accordion_single_collapse">

				<form:form action="SolidWasteCustomerInfo.html" class="form-horizontal" name="frmSolidWasteCustomerInfo" id="frmSolidWasteCustomerInfo" method="post">
					

					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					
					<div class="form-group">
						<apptags:input labelCode="Propert No(OLD/NEW)"  path="propertyNo" /></div>
						<div class="form-group">
						
						<apptags:input labelCode="Cunsumer No(OLD/NEW)" path="consumerNo" />
					</div>
					
					<div class="form-group">
						<apptags:input labelCode="Mobile No" path="mobileNo"/>
						<div class="col-sm-6">
							<button class="btn btn-success" onclick="search(this);"
								type="button">
								<i class="fa fa-search"></i>
								<spring:message code="bt.search" text="Search" />
							</button>
						</div>
					</div>

				</form:form>
			</div>
		</div>
	</div>
</div>
