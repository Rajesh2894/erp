
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/mainet/validation.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="seo.title" text="SEO MASTER" />
			</h2>
			<apptags:helpDoc url="SEOMaster.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith" /> <i
					class="text-red-1">*</i> <spring:message code="water.ismandtry" />
				</span>
			</div>


				<form:form action="SEOMaster.html" class="form-horizontal" name="frmSEOMaster" id="frmSEOMaster" method="post">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div class="panel-group accordion-toggle" id="accordion_single_collapse">
					
					<div class="panel panel-default">
					
					<div class="panel-heading">
						<h4 class="table" id="">
							<a class="" data-toggle="collapse" data-parent="#accordion_single_collapse" href="#a1"><spring:message code="seo.detail" text="ADD SEARCH KEY WORD"/></a>
						</h4>
						</div>
 						
						<div id="a1" class="panel-collapse collapse in padding-10">
						<div class="form-group">
						<spring:message code="seo.placeholder" var="placeholder" />
							<form:label path="master.keyWord" cssClass="control-label col-sm-1 col-sm-offset-2"><spring:message code="seo.desc" text="DESCRIPTION"/></form:label>
							<div class="col-sm-7"><form:textarea path="master.description" cssClass="form-control" rows="3" placeholder="${placeholder}"/></div>
							<div class="col-sm-7 col-sm-offset-2"><span class="text-blue-2"><spring:message code="seo.note.desc" text="NOTE DESC"/></span></div>
						</div>	
						<div class="form-group">
						<spring:message code="seo.placeholder1" var="placeholder1" />
							<form:label path="master.keyWord" cssClass="control-label col-sm-1 col-sm-offset-2"><spring:message code="seo.key.word" text="KEYWORDS"/></form:label>
							<div class="col-sm-7"><form:textarea path="master.keyWord" cssClass="form-control" rows="3" placeholder="${placeholder}"/></div>
							<div class="col-sm-7 col-sm-offset-2"><span class="text-blue-2"><spring:message code="seo.note" text="NOTE"/></span></div>
						</div>
						
						</div>
					</div>
					</div>
					
					<div class="text-center">
							<apptags:submitButton entityLabelCode="seo.title" buttonLabel="bt.save" actionParam="saveform" cssClass="btn btn-success" successUrl="SEOMaster.html"></apptags:submitButton>
							<apptags:backButton url="CitizenHome.html" buttonLabel="bt.back"/>
					</div>
				</form:form>
			</div>
		</div>
	</div>
 