<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ page import="java.io.*,java.util.*"%>
<script src="js/mainet/validation.js"></script>
<script src="js/tableHeadFixer.js"></script>
<script src="js/account/bankAccountMaster.js"></script>
<script>


 $(document).ready(function() {	 

	 var dateFields = $('.lessthancurrdate');
		dateFields.each(function () {
			var fieldValue = $(this).val();
			if (fieldValue.length > 10) {
				$(this).val(fieldValue.substr(0, 10));
			}
		});
		
	 var cnt = $('#tbl1 tr').length - 1;

						if('${mode}'=='update')
						{
							   //$('#bankName').attr('disabled',true);
							   $(".bankName").prop('disabled', true).trigger("chosen:updated");
							   $('#bankBranch').attr('disabled',true);
						 }
						if('${mode}'=='create')
							{
                       
							var response =__doAjaxRequest('AccountReceiptEntry.html?SLIDate', 'GET', {}, false,'json');
							var disableBeforeDate = new Date(response[0], response[1], response[2]);
							var date = new Date();
							var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());	
							$("#openingDate0").datepicker({
								dateFormat: 'dd/mm/yy',		
								changeMonth: true,
								changeYear: true,
								minDate : disableBeforeDate,
								maxDate : today,
								
							});
							}
						
						$(".addClass").click(function(){
							debugger;
							var cnt = $('#tbl1 tr').length - 1;
							var countAccount=cnt-1;
						   if(($('#accountNo'+countAccount).val()!='') && ($('#accountName'+countAccount).val()!='') && ($('#cpdAccounttype'+countAccount).val()!='' && $('#cpdAccounttype'+countAccount).val() != 0) && ($('#functionId'+countAccount).val()!='' && $('#functionId'+countAccount).val() !=0) && ($('#primtHead'+countAccount).val()!='' && $('#primtHead'+countAccount).val() !=0))
				                {  
				                var srNo=cnt+1;
				                var mode='${mode}';
						    		var row =    '<td id="srNo"> '+srNo+'</td>  <td>'
													+ '<input id="accountNo'+cnt+'" name="listOfTbBankaccount['+cnt+'].baAccountNo" onchange="findduplicatecombinationexit('+cnt+')" class="form-control hasNumber mandColorClass" type="text" maxlength="20" ></input></td>'
												
													+ '<td><input  type="text" class="form-control mandColorClass hasCharacter"  name="listOfTbBankaccount['+cnt+'].baAccountName" id="accountName'+cnt+'"></input></td>'
													
													+'<td>'
                                                    + '<select name="listOfTbBankaccount['+cnt+'].cpdAccountType" id="cpdAccounttype'+cnt+'" class="form-control mandColorClass">'
													+ '<option value="0">'
													+ "Select Type"
													+ '</option>'
													+ '	<c:forEach items="${accountType}" var="lookUp">'
													<c:choose>
													<c:when test="${lookUp.defaultVal eq 'Y'}">
												    +'<option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}" selected="selected">${lookUp.lookUpDesc} </option>'
													</c:when>
												   <c:otherwise>
													+'<option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</option>'
													</c:otherwise>
													</c:choose>
												   + '</c:forEach>'
													+ '</select>'
													+ '</td>	'										                													
													+'<td>'
                                                    + '<select name="listOfTbBankaccount['+cnt+'].functionId" id="functionId'+cnt+'" class="form-control mandColorClass">'
													+ '<option value="">'
													+ "Select Function"
													+ '</option>'
													+ '	<c:forEach items="${listOfTbAcPrimaryMasterItems}" varStatus="status" var="pacHeadItem">'												
													+'<option value="${pacHeadItem.key}" code="${pacHeadItem.value}">${pacHeadItem.value} </option>'
													+ '</c:forEach>'													 
													+ '</select>'
													+ '</td> '
													
												    +'<td>'
                                                    + '<select name="listOfTbBankaccount['+cnt+'].pacHeadId" id="primtHead'+cnt+'" class="form-control mandColorClass">'
													+ '<option value="">'
													+ "Select Primary Head"
													+ '</option>'
													+ '	<c:forEach items="${listOfTbAcFunctionMasterItems}" varStatus="status" var="functionItem">'												
													+'<option value="${functionItem.key}" code="${functionItem.value}">${functionItem.value} </option>'
													+ '</c:forEach>'													 
													+ '</select>'
													+'  <input type="hidden" name="listOfTbBankaccount['+cnt+'].sacHeadDesc" id="secHead'+cnt+'" />'
													+ '</td> '
														
													+'<td>'
                                                    + '<select name="listOfTbBankaccount['+cnt+'].fieldId" id="fieldItem'+cnt+'" class="form-control">'
													+ '<option value="0">'
													+ "Select Field"
													+ '</option>'
													+ '	<c:forEach items="${listOfTbAcFieldMasterItems}" varStatus="status" var="fieldItem">'												
													+'<option value="${fieldItem.key}" code="${fieldItem.key}">${fieldItem.value} </option>'
													+ '</c:forEach>'													 
													+ '</select>'
													+ '</td>	'
													
													
													+'<td>'
					                                + '<select name="listOfTbBankaccount['+cnt+'].fundId" id="fundId'+cnt+'" class="form-control">'
                                                    + '<option value="0">'
													+ "Select Fund"
													+ '</option>'
													+ '	<c:forEach items="${fundList}" varStatus="status" var="lookUp">'												
													+'<option  code="${lookUp.fundCode}" value="${lookUp.fundId}">${lookUp.fundCompositecode} ${lookUp.fundDesc} </option>'
													+ '</c:forEach>'													 
													+ '</select>'
													+ '</td>  '
											         ;
													
											$('#tbl1 tr').last().after('<tr id="tr'+cnt+'" class="accountClass">'+ row
													
												
												 	+ '<td  class="text-center">'
													+ '<a title="Delete" class="btn btn-danger btn-sm deletClass" href="#" id="deleteAccount'+cnt+'" onclick="removeRow(' + cnt + '\',\'' + mode + ')"><i class="fa fa-trash"></i></a></td></tr>'); 

											var response =__doAjaxRequest('AccountReceiptEntry.html?SLIDate', 'GET', {}, false,'json');
											var disableBeforeDate = new Date(response[0], response[1], response[2]);
											var date = new Date();
											var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());	
											 $("#openingDate"+cnt).datepicker({
												dateFormat: 'dd/mm/yy',		
												changeMonth: true,
												changeYear: true,
												minDate : disableBeforeDate,
												maxDate : today,
											});
											 cnt++;
							                   var content  = $(this).closest('#bankTable tr').clone();;
							   			        
							 				     content.find('div.chosen-container').remove();
							 					 content.find("select:eq(0)").chosen().trigger("chosen:updated");  
							    				 $('.error-div').hide();
							                     reorderAccount(mode);  
				                }
                                   else
		                            {
		                          showErrormsgboxTitle("Please Enter the All Mandatory fields");
		                            } 
                       });

					});	 

   </script>

<c:url value="${saveAction}" var="url_form_submit" />
<c:url value="${mode}" var="form_mode" />
<c:set var="saved" value="${saved}" />

<!--Add Section Start Here-->
<form:form class="form-horizontal" modelAttribute="bankAccountMaster"
	cssClass="form-horizontal" method="POST" action="${url_form_submit}"
	name="${saved}" id="bankMasterForm">
	<div class="error-div alert alert-danger alert-dismissible"
		id="errorDivId" style="display: none;">
		<button type="button" class="close" onclick="closeOutErrBox()"
			aria-label="Close">
			<span>&times;</span>
		</button>
		<span id="errorId"></span>
	</div>
	<jsp:include page="/jsp/tiles/validationerror.jsp" />
	<form:hidden path="successFlag" id="successFlag" />
	<form:hidden path="alreadyExist" id="alreadyExist" />
	<c:if test="${form_mode ne 'create'}">
		<form:hidden path="bmBankbranch" />
		<form:hidden path="bankType" />
		<form:hidden path="ulbBankId" />
		<form:hidden path="accountId" />
		<form:hidden path="bankName" />
		<form:hidden path="orgId" />
	</c:if>
	<form:hidden path="" value="${mode}" id="formMode_Id" />
	<div class="form-group">

		<label class="col-sm-2 control-label required-control"><spring:message
				code="account.bankmaster.bankname" /></label>
		<div class="col-sm-10">
			<form:select path="bankName"
				cssClass="form-control mandColorClass chosen-select-no-results bankName"
				id="bankName">
				<form:option value="0">
					<spring:message code="account.common.select" />
				</form:option>
				<c:forEach items="${customerBankList}" varStatus="status"
					var="cbBankid">
					<form:option value="${cbBankid.bankId}" code="${cbBankid.bankId}">${cbBankid.bank} - ${cbBankid.branch} - ${cbBankid.ifsc}</form:option>
				</c:forEach>
			</form:select>
		</div>


	</div>


	<div class="form-group">
		<label class="col-sm-2 control-label required-control"><spring:message
				code="account.bankmaster.banktype" /></label>
		<c:choose>
			<c:when test="${form_mode eq 'create'}">
				<apptags:lookupField items="${bankType}" path="bankType"
					cssClass="form-control" hasChildLookup="false" hasId="true"
					showAll="false" selectOptionLabelCode="applicantinfo.label.select"
					isMandatory="true" />
			</c:when>
			<c:otherwise>
				<div class="col-sm-4">
					<form:select path="bankType" class="form-control" id="bankType"
						disabled="true">
						<form:option value="0">
							<spring:message code="account.bankmaster.select" />
						</form:option>
						<c:forEach items="${bankType}" var="lookUp">
							<form:option value="${lookUp.lookUpId}"
								code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
						</c:forEach>
					</form:select>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	<h4>
		<spring:message code="account.bankmaster.accountdetail" />
	</h4>
	<c:choose>
		<c:when test="${empty bankAccountMaster.listOfTbBankaccount}">
			<div class="table-responsive">
				<table
					class="table table-bordered table-striped min-width-1600 bankTable"
					id="tbl1">
					<tr>
						<th width="50" scope="col"><spring:message
								code="account.bankmaster.srno" /></th>
						<th width="200" scope="col"><spring:message
								code="account.bankmaster.accountno" /><span class="mand">*</span></th>
						<th width="200" scope="col"><spring:message
								code="account.bankmaster.accountname" /><span class="mand">*</span></th>
						<th width="150" scope="col"><spring:message
								code="account.bankmaster.type" /><span class="mand">*</span></th>
						<th width="200" scope="col"><spring:message code="account.budget.code.master.functioncode"
								text="Function" /><span class="mand">*</span></th>
						<th width="250" scope="col"><spring:message code="account.budget.code.primaryaccountcode"
								text="Primary Head" /><span class="mand">*</span></th>
						<th width="200" scope="col"><spring:message
								code="account.bankmaster.field" text="Location" /></th>
						<th width="200" scope="col"><spring:message
								code="account.bankmaster.fund" /></th>
						<th width="80" scope="col"><a title="Add"
							class="btn btn-blue-2 btn-sm addClass" id="addAccount" href="#">
								<i class="fa fa-plus"></i>
						</a></th>

					</tr>

					<tr id="tr0" class="accountClass">
						<td id="srNo">1</td>
						<td><form:input path="listOfTbBankaccount[0].baAccountNo"
								cssClass="form-control hasNumber mandColorClass" id="accountNo0"
								onchange="findduplicatecombinationexit(${0})" maxlength="20"></form:input></td>

						<td><form:input path="listOfTbBankaccount[0].baAccountName"
								cssClass="form-control mandColorClass hasCharacter" id="accountName0"></form:input></td>

						<td><form:select path="listOfTbBankaccount[0].cpdAccountType"
								class="form-control mandColorClass" id="cpdAccounttype0">
								<form:option value="0">
									<spring:message code="account.bankmaster.select" />
								</form:option>
								<c:forEach items="${accountType}" var="lookUp">
									<c:choose>
										<c:when test="${lookUp.defaultVal eq 'Y'}">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}" selected="selected">${lookUp.lookUpDesc} </form:option>
										</c:when>
										<c:otherwise>
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</form:select></td>
						<td><form:select path="listOfTbBankaccount[0].functionId"
								class="form-control mandColorClass" id="functionId0">
								<form:option value="">
									<spring:message code="account.budget.code.master.selectfunctioncode" text="Select Function" />
								</form:option>
								<c:forEach items="${listOfTbAcPrimaryMasterItems}"
									var="pacHeadItem">
									<form:option value="${pacHeadItem.key}">${pacHeadItem.value}</form:option>
								</c:forEach>
							</form:select> <form:hidden path="listOfTbBankaccount[0].sacHeadDesc"
								id="secHead0" /></td>
						<td><form:select path="listOfTbBankaccount[0].pacHeadId"
								class="form-control mandColorClass" id="primtHead0">
								<form:option value="">
									<spring:message code="account.budget.code.master.selectprimaryaccountcode" text="Select Primary Head" />
								</form:option>
								<c:forEach items="${listOfTbAcFunctionMasterItems}"
									var="functionItem">
									<form:option value="${functionItem.key}">${functionItem.value}</form:option>
								</c:forEach>
							</form:select> <form:hidden path="listOfTbBankaccount[0].sacHeadDesc"
								id="secHead0" /></td>
						<td><form:select path="listOfTbBankaccount[0].fieldId"
								class="form-control" id="fieldItem0">
								<form:option value="0">
									<spring:message code="account.budgetopenmaster.selectfieldcode" text="Select Field" />
								</form:option>
								<c:forEach items="${listOfTbAcFieldMasterItems}"
									varStatus="status" var="fieldItem">
									<form:option value="${fieldItem.key}" code="${fieldItem.key}">${fieldItem.value}</form:option>
								</c:forEach>
							</form:select></td>
						<td><form:select path="listOfTbBankaccount[0].fundId"
								class="form-control" id="fundId0">
								<form:option value="0">
									<spring:message code="account.budgetopenmaster.selectfundcode" text="Select Fund" />
								</form:option>
								<c:forEach items="${fundList}" var="lookUp">
									<form:option code="${lookUp.fundCode}" value="${lookUp.fundId}">${lookUp.fundCompositecode} ${lookUp.fundDesc}</form:option>
								</c:forEach>
							</form:select></td>

						<td class="text-center"><a title="Delete" href="#"
							class="btn btn-danger btn-sm deletClass" id=deleteAccount0
							onclick="removeRow(0,'')"><i class="fa fa-trash"></i></a></td>

					</tr>
				</table>
			</div>
		</c:when>
		<c:otherwise>

			<div class="table-responsive">
				<table
					class="table table-bordered table-striped min-width-1600 bankTable"
					id="tbl1">
					<tr>
						<th width="50" scope="col"><spring:message
								code="account.bankmaster.srno" /></th>
						<th width="200" scope="col"><spring:message
								code="account.bankmaster.accountno" /><span class="mand">*</span></th>
						<th width="200" scope="col"><spring:message
								code="account.bankmaster.accountname" /><span class="mand">*</span></th>
						<th width="150" scope="col"><spring:message
								code="account.bankmaster.type" /><span class="mand">*</span></th>
						<th width="200" scope="col"><spring:message code="account.budget.code.master.functioncode"
								text="Function" /><span class="mand">*</span></th>
						<th width="250" scope="col"><spring:message code="account.budget.code.master.primaryaccountcode"
								text="Primary Head" /><span class="mand">*</span></th>
						<th width="150" scope="col"><spring:message
								code="account.bankmaster.field" text="Location" /></th>
						<th width="200" scope="col"><spring:message
								code="account.bankmaster.fund" /></th>
						<th width="130" scope="col"><spring:message
								code="account.bankmaster.status" /><span class="mand">*</span></th>

					</tr>

					<c:forEach items="${bankAccountMaster.listOfTbBankaccount}"
						var="details" varStatus="status">
						<tr id="tr${status.count-1}" class="accountClass">
							<td id="srNo">${status.count}</td>

							<td><form:input
									path="listOfTbBankaccount[${status.count-1}].baAccountNo"
									cssClass="form-control mandColorClass hasNumber"
									id="accountNo${status.count-1}" maxlength="20"></form:input></td>

							<td><form:input
									path="listOfTbBankaccount[${status.count-1}].baAccountName"
									cssClass="form-control mandColorClass hasCharacter"
									id="accountName${status.count-1}"></form:input></td>

							<td><form:select
									path="listOfTbBankaccount[${status.count-1}].cpdAccountType"
									class="form-control mandColorClass"
									id="cpdAccounttype${status.count-1}">
									<form:option value="0">
										<spring:message code="account.bankmaster.select" />
									</form:option>
									<c:forEach items="${accountType}" var="lookUp">
										<form:option value="${lookUp.lookUpId}"
											code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
									</c:forEach>
								</form:select></td>
							<td><form:select
									path="listOfTbBankaccount[${status.count-1}].functionId"
									class="form-control" id="functionId${status.count-1}"
									disabled="true">
									<form:option value="">
										<spring:message code="account.bankmaster.select" />
									</form:option>
									<c:forEach items="${listOfTbAcPrimaryMasterItems}"
										var="pacHeadItem">
										<form:option value="${pacHeadItem.key}"
											code="${pacHeadItem.value}">${pacHeadItem.value}</form:option>
									</c:forEach>
								</form:select> <form:hidden
									path="listOfTbBankaccount[${status.count-1}].functionId" /></td>

							<td><form:select
									path="listOfTbBankaccount[${status.count-1}].pacHeadId"
									class="form-control" id="primtHead${status.count-1}"
									disabled="true">
									<form:option value="">
										<spring:message code="account.bankmaster.select" />
									</form:option>
									<c:forEach items="${listOfTbAcFunctionMasterItems}"
										var="functionItem">
										<form:option value="${functionItem.key}"
											code="${functionItem.value}">${functionItem.value}</form:option>
									</c:forEach>
								</form:select> <form:hidden
									path="listOfTbBankaccount[${status.count-1}].sacHeadDesc"
									id="secHead${status.count-1}" /> <form:hidden
									path="listOfTbBankaccount[${status.count-1}].pacHeadId" /></td>


							<td><form:select
									path="listOfTbBankaccount[${status.count-1}].fieldId"
									class="form-control" id="fieldItem${status.count-1}">
									<form:option value="0">
										<spring:message code="account.bankmaster.select" />
									</form:option>
									<c:forEach items="${listOfTbAcFieldMasterItems}"
										var="fieldItem">
										<form:option value="${fieldItem.key}" code="${fieldItem.key}">${fieldItem.value}</form:option>
									</c:forEach>
								</form:select></td>

							<td><form:select
									path="listOfTbBankaccount[${status.count-1}].fundId"
									class="form-control" id="fundId${status.count-1}">
									<form:option value="0">
										<spring:message code="account.bankmaster.select" />
									</form:option>
									<c:forEach items="${fundList}" var="lookUp">
										<form:option code="${lookUp.fundCode}"
											value="${lookUp.fundId}">${lookUp.fundCompositecode} ${lookUp.fundDesc}</form:option>
									</c:forEach>
								</form:select></td>

							<td><form:select
									path="listOfTbBankaccount[${status.count-1}].acCpdIdStatus"
									class="form-control mandColorClass"
									id="babStatus${status.count-1}">
									<form:option value="0">
										<spring:message code="account.bankmaster.select" />
									</form:option>
									<c:forEach items="${accountStatus}" var="lookUp">
										<form:option value="${lookUp.lookUpId}"
											code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
									</c:forEach>
								</form:select></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</c:otherwise>
	</c:choose>
	<br>
	<div class="text-center padding-bottom-20">
		<c:if test="${form_mode eq 'create'}">
			<button type="button" class="btn btn-success btn-submit"
				onclick="saveBankMaster(this)">
				<spring:message code="account.bankmaster.save" />
			</button>
			<button type="button" class="btn btn-warning createData"
				id="createData">
				<spring:message code="account.bankmaster.reset" text="Reset" />
			</button>
		</c:if>
		<c:if test="${form_mode ne 'create'}">
			<button type="button" class="btn btn-success btn-submit"
				onclick="saveBankMaster(this)">
				<spring:message code="account.bankmaster.save" />
			</button>
		</c:if>
		<input type="button" class="btn btn-danger"
			onclick="window.location.href='BankAccountMaster.html'"
			value="<spring:message code="account.bankmaster.back" text="Back"/>" id="cancelEdit" />
	</div>

</form:form>


