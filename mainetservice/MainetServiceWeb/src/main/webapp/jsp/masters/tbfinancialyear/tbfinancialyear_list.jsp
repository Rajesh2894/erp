
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>   
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script src="js/mainet/ui/i18n/grid.locale-en.js" type="text/javascript"></script>
<script src="js/mainet/jquery.jqGrid.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/masters/tbfinancialyear/tbfinancialyearList.js"></script>  
<div class="financialyeardiv">
<apptags:breadcrumb></apptags:breadcrumb>

<c:set var="defaultOrg" value="${userSession.getCurrent().getOrganisation().getDefaultStatus()}"/>
  <div class="content">
		<div class="widget">
		<div class="widget-header">
          <h2 title="Financial Year">
          <spring:message code="finyear.form.caption" text="Financial Year Master"/>
          </h2>
       <apptags:helpDoc url="Financialyear.html" helpDocRefURL="Financialyear.html"></apptags:helpDoc>
        </div>
		 <div class="widget-content padding">		
				<form:form method="post" action="Financialyear.html" name="finyear" id="finyear" class="form" modelAttribute="tbFinancialyear">
					
					<input type="hidden" value="${userSession.getCurrent().getEmployee().getDesignation().getDsgname()}" id="desgName"/>
					<input type="hidden" value="N" id="editPermission"/>
			<c:if test="${userSession.getCurrent().organisation.defaultStatus eq 'Y'}">
				<div class="text-center padding-top-10 clear">
						<span class="otherlink"> <a href="javascript:void(0);"
							class="btn btn-success createData" id="addButton">
							<i class="fa fa-plus-circle"></i> <spring:message code='master.addButton'/>
						</a>
						</span>
				</div>	
			</c:if>
				 <div class="clear padding-top-10">				
						<table id="finYeargrid"></table>
						<div id="pagered"></div> 
					</div>
		</form:form>
	</div>
</div>
</div>
</div>