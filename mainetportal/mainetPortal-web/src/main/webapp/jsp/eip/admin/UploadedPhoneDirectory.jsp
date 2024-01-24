<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script>

	$(document).ready(function() {
		var optionValues =[];
		$('#designation-filter option').each(function(){
		   if($.inArray(this.value, optionValues) >-1){
		      $(this).remove()
		   }else{
		      optionValues.push(this.value);
		   }
		});	
		
    function addRemoveClass(theRows) {
        theRows.removeClass("odd even");
        theRows.filter(":odd").addClass("odd");
        theRows.filter(":even").addClass("even");
    }
    var rows = $("#product-table tbody tr");
    addRemoveClass(rows);
    $(document).on('change', '#designation-filter', function(){
    	
        var selected = this.value;;
        if (selected != "All") {
        	$("select[name=product-table_length]").val("-1");
        	$("select[name=product-table_length]").change();
        	$("select[name=product-table_length]").hide();
        	rows.filter("[position='" + selected + "']").show();            
            rows.not("[position='" + selected + "']").hide();
            var visibleRows = rows.filter("[position=" + selected + "]");
            addRemoveClass(visibleRows);
        } else {
        	$("select[name=product-table_length]").val("50");
        	$("select[name=product-table_length]").show();
        	rows.show();
            addRemoveClass(rows);
        }
    });
    
    $("#product-table").dataTable({
		"language": { "search": "" }, 
		"pagingType": "full_numbers",
		"lengthMenu": [[ 50,100, -1], [ 50,100, "All"]],
		"aaSorting": []
		});
});
</script>


<div class="container-fluid dashboard-page">
	<div class="col-sm-12" id="nischay">
		<div class="widget">
			<h1 class="grid-heading">
				<spring:message code="lbl.phone.directory" text="Phone Directory" />
			</h1>
			<div class="widget-content padding">
				<div class="col-sm-12">
					<c:if test="${empty command.uploadedPath}">
						<div class="col-sm-6 col-sm-offset-3">
							<span class="error-msg">Phone Directory not found!</span>
						</div>
					</c:if>

					<c:if test="${not empty command.uploadedPath}">
						<div class="col-sm-12 col-xs-12 col-md-12 col-lg-12 text-right">
							
							<apptags:filedownload filename="${command.attFname}"
								filePath="${command.uploadedPath}"
								actionUrl="PhoneDirectory.html?Download"></apptags:filedownload>
						</div>
					</c:if>
					
					<div>
						<div class="from-group">
						<div class="col-sm-4">
						<select class="form-control" id="designation-filter">
						<option selected>All</option>
						<c:forEach items="${phoneDirectory.parseData}" var="search">
						<c:if test="${search.division ne null}">
						<option>${search.division}</option>
						</c:if>
						</c:forEach>
						</select>
						</div>
						</div>
						<div class="clearfix"></div>
						<table class="table table-bordered" id="product-table">
							<thead>
								<tr>
									<th><spring:message code="lbl.phone.directory.sr.no"
											text="Sr.No."></spring:message></th>
									<th><spring:message code="lbl.phone.directory.dept"
											text="Department"></spring:message></th>
									<th><spring:message code="lbl.phone.directory.name"
											text="Name"></spring:message></th>
									<th><spring:message code="lbl.phone.directory.division"
											text="Division/Office"></spring:message></th>
									<th><spring:message code="lbl.phone.directory.designation"
											text="Designation"></spring:message></th>
									<th><spring:message code="lbl.phone.directory.office.no"
											text="Officce No."></spring:message></th>
									<th><spring:message code="lbl.phone.directory.fax.no"
											text="Fax No."></spring:message></th>
									<th><spring:message code="lbl.phone.directory.mobile.no"
											text="Mobile No."></spring:message></th>
									<th><spring:message code="lbl.phone.directory.email"
											text="Email-ID"></spring:message></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${phoneDirectory.parseData}" var="directory">
									<tr position="${directory.division}">
										<td>${directory.srNo}</td>
										<td>${directory.department}</td>
										<td>${directory.name}</td>
										<td>${directory.division}</td>
										<td>${directory.designation}</td>
										<td>${directory.telephone}</td>
										<td>${directory.faxNo}</td>
										<td>${directory.mobile}</td>
										<td>${directory.emailId}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

   
    