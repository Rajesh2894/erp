<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- End JSP Necessary Tags -->
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/council/councilAgendaMaster.js"></script>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content animated slideInDown">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<!-- End Main Page Heading -->
		<!-- Start Content here -->
		<c:if
			test="${command.saveMode eq 'ADD' || command.saveMode eq 'EDIT' }">
			<div class="widget-header">
				<h2>
					<spring:message code="council.agenda.create" />
				</h2>
				<apptags:helpDoc url="CouncilAgendaMaster.html" />
			</div>
		</c:if>

		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<form:form action="CouncilAgendaMaster.html"
				cssClass="form-horizontal" id="addCouncilAgendaMaster"
				name="CouncilAgendaMaster">

				<form:hidden path="couAgendaMasterDto.proposalIds" id="proposalIds" />
				<form:hidden path="agendaProposalDtoList[0].proposalNo"
					id="presentProposalNo" />

				<form:hidden path="couAgendaMasterDto.agendaId" id="agendaId" />
				<form:hidden path="proposalDtoList" id="proposallist" />
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">

					<h4 class="panel-title">
						<a data-toggle="collapse" class="" href="#child-level"><spring:message
								code="council.agenda.master.proposal.details" /></a>
					</h4>
					<div id="child-level" class="collapse in">
						<div class="panel-body">
							<div id="initialDataTable">
								<table class="table table-bordered margin-bottom-10"
									id="initialTable">
									<thead>
										<tr>
											<th class="text-center"><spring:message
													code="council.srno" text="Sr. No" /></th>
											<th class="text-center"><spring:message
													code="council.proposal.no" text="Proposal Number" /></th>
											<th class="text-center"><spring:message
													code="council.member.department" text="Department" /></th>
											<th class="text-center"><spring:message
													code="council.proposal.details" text="Details of Proposal" /></th>
											<th class="text-center"><spring:message
													code="council.proposal.amount" text="Amount" /></th>
											<%-- <th class="text-center"><spring:message
													code="council.agenda.status" text="Status" /></th> --%>
											<th class="text-center"><spring:message
													code="council.action" text="Action" /></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${command.proposalDtoList}" var="proposal"
											varStatus="status">
											<tr>
												<td class="text-center">${status.count}</td>
												<td class="text-center">${proposal.proposalNo}</td>
												<td class="text-center">${proposal.proposalDeptName}</td>
												<td class="text-center">${proposal.proposalDetails}</td>
												<td class="text-center">${proposal.proposalAmt}</td>
												<%-- <td class="text-center">${proposal.proposalStatus}</td> --%>
												<td class="text-center">
													<button type="button" class="btn btn-blue-2"
														onclick="appendToNextTable(${proposal.proposalId},'ADD')">
														<i class="fa fa-plus" aria-hidden="true"></i>
													</button>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>


					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" class="collapsed"
								data-target="#experience"><spring:message
									code="council.agenda.details" /></a>
						</h4>
					</div>
					<div id="experience" class="collapse in">
						<div class="panel-body">

							<div class="form-group">
								<label class="control-label col-sm-2 required-control"
									for="Year"><spring:message code="council.committeeType"
										text="Committee Type"></spring:message></label>
								<c:set var="baseLookupCode" value="CPT" />
								<div class="col-sm-4">
									<c:choose>
										<c:when test="${command.saveMode eq 'ADD'}">
											<form:select path="couAgendaMasterDto.committeeTypeId"
												cssClass="form-control" hasId="true" isMandatory="true" id="committeeTypeId"
												disabled="${command.saveMode eq 'VIEW' || command.saveMode eq 'EDIT'}" onchange="checkCommitteeDissolve(this)">
												<form:option value="">
													<spring:message code='council.dropdown.select' />
												</form:option>
												<c:forEach items="${command.committeeTypeFilterList}" var="look">
													<c:choose>
														<c:when test="${userSession.languageId eq 1}">
															<form:option value="${look.lookUpId}" code="${look.otherField}">${look.descLangFirst}</form:option>
														</c:when>
														<c:otherwise>
															<form:option value="${look.lookUpId}" code="${look.otherField}">${look.descLangSecond}</form:option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</form:select>
										</c:when>
										<c:otherwise>
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="couAgendaMasterDto.committeeTypeId"
												cssClass="form-control" hasId="true"
												selectOptionLabelCode="applicantinfo.label.select"
												isMandatory="true" showOnlyLabel="Committee Type"
												disabled="${command.saveMode eq 'VIEW' || command.saveMode eq 'EDIT'}" />
										</c:otherwise>
									</c:choose>
								</div>
								<c:choose>
									<c:when
										test="${command.saveMode eq 'ADD' || command.saveMode eq 'EDIT'}">
										<apptags:date fieldclass="datepicker" labelCode="council.agenda.date"
											datePath="couAgendaMasterDto.agendaDate" isMandatory="true"></apptags:date>
									</c:when>
									<c:otherwise>
										<apptags:date fieldclass="datepicker" labelCode="Agenda Date"
											datePath="couAgendaMasterDto.agendaDate" readonly="true"
											isDisabled="true"></apptags:date>
									</c:otherwise>
								</c:choose>
							</div>


							<c:if test="${command.saveMode eq 'VIEW'}">
								<div class="form-group ">
									<apptags:input labelCode="Agenda Number"
										path="couAgendaMasterDto.agendaNo" isDisabled="true"
										cssClass="form-control"></apptags:input>
								</div>
							</c:if>

							<table class="table table-bordered margin-bottom-10"
								id="finalTable">
								<thead>
									<tr>
										<th class="text-center"><spring:message
												code="council.srno" text="Sr. No" /></th>
										<th class="text-center"><spring:message
												code="council.proposal.no" text="Proposal Number" /></th>
										<th class="text-center"><spring:message
												code="council.member.department" text="Department" /></th>
										<th class="text-center"><spring:message
												code="council.proposal.details" text="Details of Proposal" /></th>
										<th class="text-center"><spring:message
												code="council.proposal.amount" text="Amount" /></th>
										<c:if test="${command.saveMode != 'VIEW' }">
											<th class="text-center"><spring:message
													code="council.action" text="Action" /></th>
										</c:if>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${command.agendaProposalDtoList}"
										var="proposal" varStatus="status">
										<tr>
											<td class="text-center">${status.count}</td>
											<td class="text-center">${proposal.proposalNo}</td>
											<td class="text-center">${proposal.proposalDeptName}</td>
											<td class="text-center">${proposal.proposalDetails}</td>
											<td class="text-center">${proposal.proposalAmt}</td>
											<c:if test="${command.saveMode != 'VIEW' }">
												<td class="text-center">
													<button type="button" class="btn btn-danger"
														onclick="appendToNextTable(${proposal.proposalId},'DELETE')">
														<i class="fa fa-trash" aria-hidden="true"></i>
													</button>
												</td>
											</c:if>

										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					<div class="text-center">
						<c:if
							test="${command.saveMode eq 'ADD' || command.saveMode eq 'EDIT'}">
							<button type="button" onClick="createAgenda(this);"
								class="btn btn-blue-2" title="Create">
								<i class="fa fa-pencil padding-right-5" aria-hidden="true"></i>
								<spring:message code="council.button.submit" text="Submit" />
							</button>
						</c:if>

						<button type="button" class="button-input btn btn-danger"
							name="button-Cancel" value="Cancel" style=""
							onclick="backAgendaMasterForm();" id="button-Cancel">
							<i class="fa fa-chevron-circle-left padding-right-5"></i>
							<spring:message code="council.button.back" text="Back" />
						</button>
					</div>
				</div>
			</form:form>
		</div>



		<!-- End Content here -->
	</div>
	<!-- End Widget  here -->
</div>
<script type="text/javascript">


/*initialization of variables  */
var finalList = [],initialList = [], list=[];
var proposalIds =[];

		
		if(mode == 'VIEW'){
	       $("#initialDataTable").hide();        
	    }else{
			<c:forEach var="agendaProposal" items="${command.agendaProposalDtoList}" varStatus="i">
               var id = '${agendaProposal.proposalId}';
               proposalIds.push(id);
	     	</c:forEach>
            $("#proposalIds").val(proposalIds);
        }
		
		/* here hide the table based on view mode  */
		var mode = '${command.saveMode}';
		if(mode == 'VIEW'){
			$("#initialDataTable").hide();	
		}
		
		<c:forEach var="proposal" items="${command.proposalDtoList}" varStatus="i">
		var srNo = '${i.index+1}';
		var proposalNo = '${proposal.proposalNo}';
		var department = '${proposal.proposalDeptName}';
		//D#117116 multi line in string not support by doing below it can work multi line
		var	details = `${proposal.proposalDetails}`;
		var amount = '${proposal.proposalAmt}';
		var status = '${proposal.proposalStatus}';
		var id = '${proposal.proposalId}';
		var proposalDate = '${proposal.proposalDate}';
		
		
		var proposalObj= {
				id:id,
				proposalNo:proposalNo,
				department:department,
				details:details,
				amount: amount,
				status:status,
				proposalDate:proposalDate
		}
		/* make list of object for set data in initialTable  */
		list.push(proposalObj);
		</c:forEach>
		
		/* push data (agendaProposalList) in finalList array  only in case of edit operation*/
		var editMode = '${command.saveMode}';
		if(editMode == 'EDIT'){
			<c:forEach var="proposal" items="${command.agendaProposalDtoList}" varStatus="i">
			
			var agendaProposalObj= {
					id:'${proposal.proposalId}',
					proposalNo:'${proposal.proposalNo}',
					department:'${proposal.proposalDeptName}',
					details:'${proposal.proposalDetails}',
					amount: '${proposal.proposalAmt}',
					status: '${proposal.proposalStatus}'
			}
			/* make list of object for set data in finalTable  */
			finalList.push(agendaProposalObj);
			</c:forEach>		
		}
		
		/* assign list to initialList array */
		initialList = list;
		//setTable();
		
		function setTable(){
			if(finalList.length==0 && initialList.length==0){
				return false;
			}
			
			/* reinitialize dataTable issue fix using below code */
			if ( $.fn.DataTable.isDataTable('#initialTable') ) {
				  $('#initialTable').DataTable().destroy();
				}
			
			$("#initialTable tbody").empty();
			
			/* this is only for arrange in order */
			initialList = initialList.sort(function(a,b){
			    return a.id-b.id;
			});
			
			for (i = 0; i < initialList.length; i++) {
				let proposal = initialList[i];
			    $("#initialTable tbody").append('<tr>'
			    		+'<td class="text-center">'+ (i+1) 
			            +'</td>'
			    		+'<td class="text-center">'+proposal.proposalNo 
			            +'</td>'
			            +'<td class="text-center">' +proposal.department
			            +'</td>'
			            +'<td class="text-center">' +proposal.details
			            +'</td>'
			            +'<td class="text-center">' +proposal.amount
			            +'</td>'
			            /* +'<td class="text-center">' +proposal.status
			            +'</td>' */
			            +'<td class="text-center"><button type="button" class="btn btn-blue-2" onclick="appendToNextTable(\''
						+ proposal.id
						+ '\',\'ADD\')"><i class="fa fa-plus" aria-hidden="true"></i></button>'
			            +'</td>'
			            +'</tr>');
			}
			
			/* initialize dataTable */ 
			$("#initialTable").dataTable({
				"oLanguage" : {
					"sSearch" : ""
				},
				"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
				"iDisplayLength" : 5,
				"bInfo" : true,
				"lengthChange" : true
			});
			
			/* reinitialize dataTable issue fix using below code */
			if ( $.fn.DataTable.isDataTable('#finalTable') ) {
				  $('#finalTable').DataTable().destroy();
				}
			$("#finalTable tbody").empty();
			var biggerDate = '';
			for (i = 0; i < finalList.length; i++) {
				let proposal = finalList[i];
			    $("#finalTable tbody").append('<tr>'
			    		+'<td class="text-center">'+ (i+1) 
			            +'</td>'
			    		+'<td class="text-center">'+proposal.proposalNo 
			            +'</td>'
			            +'<td class="text-center">' +proposal.department
			            +'</td>'
			            +'<td class="text-center">' +proposal.details
			            +'</td>'
			            +'<td class="text-center">' +proposal.amount
			            +'</td>'
			            +'<td class="text-center"><button type="button" class="btn btn-danger" onclick="appendToNextTable(\''
						+ proposal.id
						+ '\',\'DELETE\')"><i class="fa fa-trash" aria-hidden="true"></i></button>'
			            +'</td>'
			            +'</tr>');
			    
			    let proposalDate = proposal.proposalDate;
			  //here datepicker validation based on finalList array
				if(proposalDate>=biggerDate){
					biggerDate= proposalDate;
				}
			}
			
			//convert date from yyyy-mm-dd to dd/mm/yy		
			    biggerDate = biggerDate.replace(/(\d{4})-(\d{1,2})-(\d{1,2})/, function(match,y,m,d) { 
			        return d + '/' + m + '/' + y;  
			    });
			
			
			$('#agendaDate').datepicker('destroy').datepicker({
				dateFormat : 'dd/mm/yy',
	            minDate: biggerDate,
	            changeMonth : true,
	    		changeYear : true
	           });
			/* initialize dataTable */ 
			$("#finalTable").dataTable({
				"oLanguage" : {
					"sSearch" : ""
				},
				"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
				"iDisplayLength" : 5,
				"bInfo" : true,
				"lengthChange" : true
			});
			
			/* get proposalIds from finalList by iterating list */
		    proposalIds =[];
			for(let i=0; i<finalList.length; i++){
				proposalIds.push(finalList[i].id);
			}
			/* set proposalIds on jsp */
			$("#proposalIds").val(proposalIds);
			
			
			
			
			
		}
			
		/* Logic for set row from initialTable to FinalTable and vice versa */
		function appendToNextTable(id, operation) {
			switch (operation) {
		        case "ADD":
		        	/* getting index position of that row using id passing */
		            //var indexNo = initialList.findIndex(o => o.id == id);
		        	var indexNo =  initialList.findIndex(function(obj){
					    return obj.id == id;
					});
		        	
		            if (indexNo != -1) {
		                var rowData = initialList[indexNo];
		                finalList.push(rowData);
		                initialList.splice(indexNo, 1);
		                setTable();
		            }
		            break;
		
		        case "DELETE":
		            //var indexNo = finalList.findIndex(o => o.id == id);
		            var indexNo =  finalList.findIndex(function(obj){
					    return obj.id == id;
					});
		            if (indexNo != -1) {
		                var rowData = finalList[indexNo];
		                initialList.push(rowData);
		                finalList.splice(indexNo, 1);
		                setTable();
		            }
		            break;
		    }
			
		}
</script>

<!-- End of Content -->
