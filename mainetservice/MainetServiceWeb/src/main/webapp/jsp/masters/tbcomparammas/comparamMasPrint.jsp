<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript">
	function printdiv(printpage) {
		var headstr = "<html><head><title></title><link href='assets/css/style-responsive.css' rel='stylesheet' type='text/css' /></head><body>";
		var footstr = "</body>";
		var newstr = document.all.item(printpage).innerHTML;
		var oldstr = document.body.innerHTML;
		document.body.innerHTML = headstr + newstr + footstr;
		window.print();
		document.body.innerHTML = oldstr;
		return false;
	}
	
    $(document).ready(function() {
    	$(".deptName").html(deptName);
    });
 
	
</script>
<style>
.sectionSeperator {
	border-bottom: 1px solid #123456;
	border-top: 1px solid #123456;
}
</style>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong>Prefix Details</strong>
			</h2>

		</div>
  
		<div class="widget-content padding">
			<div class="col-sm-12" id="prefix">
				<div class="col-sm-12">
				<h3 class="text-center">${userSession.organisation.ONlsOrgname}</h3>
				<h4 class="text-center deptName"></h4>
			  </div>
      
      
<caption  text-align:center>Non-Hierarchical Prefix</caption>		
<div class="table-responsive">	
<table class="table table-bordered">
	<tr>
	<th>Prefix Type</th>
	<th>Prefix</th>
	<th>Description</th>
	<th>Status</th>
	<th>Is Editable</th>
	<th>Load At Start-up</th>
	<th>Prefix Detail</th>
	<!-- <th>Description</th>
	<th>Description (Regional)</th>
	<th>Value</th>
	<th>Other Value</th>
	<th>Default</th> -->
	</tr>
	<c:forEach items="${prefixList}" var="ndata" 
							    varStatus="loop">
							    
   <c:if test="${ndata.cpmType eq 'N'}">					    
	<tr>
	
	<td>${ndata.cpmType}</td>
	<td>${ndata.cpmPrefix}</td>
	<td>${ndata.cpmDesc}</td>
	<td>${ndata.cpmStatus}</td>
	<td>${ndata.cpmEditValue}</td>
	<td>${ndata.loadAtStartup}</td>
	<td>
	<table class="table table-bordered">
	<tr>
	<th>Description</th>
	<th>Description (Regional)</th>
	<th>Value</th>
	<th>Other Value</th>
	<th>Default</th>
	</tr>

	<c:forEach items="${ndata.comparamDetList}" var="nd" 
							    varStatus="loop"> 
	<tr>
	<td>${nd.cpdDesc}</td>
	<td>${nd.cpdDescMar}</td>
	<td>${nd.cpdValue}</td>
	<td>${nd.cpdOthers}</td>
	<td>${nd.cpdDefault}</td>
	</tr>
	
	
   </c:forEach>
	
	</table>
	
	</td>
	<%-- <td></td>
	<td></td>
	<td>${data.cpmTypeValue}</td>
	<td></td>
	<td></td> --%>
	
	</tr>
	</c:if>
	</c:forEach>
</table>
</div>

			</div>
			<div class="clear"></div>
			<div class="text-center hidden-print padding-20">
				<button onclick="printdiv('prefix')"
					class="btn btn-primary hidden-print">
					<i class="fa fa-print"></i>
					<spring:message code="" text="Print" />
				</button>
				<apptags:backButton url="ComparamMaster.html"></apptags:backButton>
			</div>

		</div>

	</div>

</div>
