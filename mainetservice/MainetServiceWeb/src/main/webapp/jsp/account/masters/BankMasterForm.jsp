<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ page import="java.io.*,java.util.*"%>
<script src="js/mainet/validation.js"></script>
<script src="js/account/bankMaster.js"></script>
<script>
 $(document).ready(function() {
						var cnt = $('#tbl1 tr').length - 1;
				       for(var i=0;i<cnt;i++)
							{
							var fundRequired = $('#isFundRequired'+i).is(':checked');
							if(fundRequired)
							{
								$("#viewFund"+i).prop("disabled",false);
							}
							else
							{
								$("#viewFund"+i).prop("disabled",true);
							}
							}
					    if('${mode}'=='view') 
								{
								$('input[type=text]').attr('readOnly',true);
								$('#bankAddress').attr('disabled',true);
								$('#addAccount').attr('disabled',true);
							    $('[id^="deleteAccount"]').attr('disabled',true);
								$('select').attr("disabled", true);	
								$('[id^="openingDate"]').removeClass("lessthancurrdate cal");
								$('input[type=checkbox]').attr('disabled',true);
								}
						if('${mode}'=='update')
						{
							$('input[type=text]').attr('readOnly',true);
						    $('select').attr("disabled", true);	
						    $('#bankContact').attr('readOnly',false);
							$('#bankEmail').attr('readOnly',false);
							$('#cbbankName').attr('disabled',false);
						    $('[id^="babStatus"]').attr('disabled',false);
						    $('[id^="cpdAccounttype"]').attr('disabled',false);
						    $('[id^="accountNo"]').attr('readOnly',false);
						    $('[id^="accountName"]').attr('readOnly',false);
						    $('[id^="opBal"]').attr('readOnly',false);
							$('[id^="openingDate"]').attr('readOnly',false);
						 }
						if('${mode}'=='create')
							{
							 var now = new Date();
							 var today = now.getDate()  + '/' + (now.getMonth() + 1) + '/' + now.getFullYear();
						     $('[id^="openingDate"]').val(today);
							}
						$("#addAccount").click(function(){
							var countAccount=cnt-1;
						   if($('#accountNo'+countAccount).val()!='' && $('#accountName'+countAccount).val()!='')
				                {  
				                var srNo=cnt+1;
				                var mode='${mode}';
						    		var row =    '<td id="srNo"> '+srNo+'</td>  <td>'
													+ '<input id="accountNo'+cnt+'" name="listOfTbBankaccount['+cnt+'].baAccountcode" class="form-control" type="text" maxlength="20" ></input></td>'
													+'<td>'
                                                    + '<select name="listOfTbBankaccount['+cnt+'].cpdAccounttype" id="cpdAccounttype'+cnt+'" class="form-control">'
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
													+ '<td><input  type="text" class="form-control "  name="listOfTbBankaccount['+cnt+'].baAccountname" id="accountName'+cnt+'"></input></td>'
													+ '<td><input  type="text" class="form-control cal lessthancurrdate" name="listOfTbBankaccount['+cnt+'].tempdate" id="openingDate'+cnt+'"></input></td>'
													+ '<td><input  type="text" class="form-control hasNumber" name="listOfTbBankaccount['+cnt+'].listOfTbBankaccountBal[0].babOpeningbalance" id="opBal'+cnt+'" onkeyup="copyBal('+cnt+')"  maxlength="12"></input></td>'
													+ '<td><input  type="text" class="form-control hasNumber" name="listOfTbBankaccount['+cnt+'].listOfTbBankaccountBal[0].babClosingbalance" id="closeBal'+cnt+'" readonly="true"  maxlength="12"></input></td>'
													+'<td>'
                                                    + '<select name="listOfTbBankaccount['+cnt+'].acCpdId" id="babStatus'+cnt+'" class="form-control">'
													+ '<option value="0">'
													+ "Select Status"
													+ '</option>'
													+ '	<c:forEach items="${accountStatus}" var="lookUp">'
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
													+'</td>'
													+'<td>'
										            +'<div class="checkbox-inline">'
										            +'<input type=checkbox name="listOfTbBankaccount['+cnt+'].fundAplFlag" value="Y" id="isFundRequired'+cnt+'" onclick="openFundForm('+cnt+')"/>'
										            +'</div>'
													+'</td>'
													+'<td>'
													+'<button type="button" class="btn btn-success viewFund" onclick="viewFundDetails(' + cnt + '\',\'' + mode + ')" id="viewFund'+cnt+'" disabled="disabled">View</button>'
													+ '</td>';
													
											$('#tbl1 tr').last().after('<tr id="tr'+cnt+'" class="accountClass">'+ row
													+ '<td><a data-toggle="tooltip" data-placement="top" title="" class="btn btn-danger btn-sm deletClass" data-original-title="Delete Owner" id="deleteAccount'+cnt+'" onclick="removeRow(' + cnt + '\',\'' + mode + ')"><i class="fa fa-trash"></i></a></td></tr>');
											        var now = new Date();
											        var today = now.getDate()  + '/' + (now.getMonth() + 1) + '/' + now.getFullYear();
											        $("#openingDate"+cnt).val(today);
								                   cnt++;
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
<form:form class="form-horizontal" modelAttribute="tbBankmaster"
	cssClass="form-horizontal" method="POST" action="${url_form_submit}"
	name="${saved}">
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
		<form:hidden path="bmBankid" />
		<form:hidden path="userId" />
		<form:hidden path="langId" />
		<form:hidden path="" />
	</c:if>
	<div class="form-group">
		<label class="col-sm-2 control-label required-control"><spring:message
				code="account.bankmaster.banktype" text="Bank Type" /></label>

		<apptags:lookupField items="${bankType}" path="bankType"
			cssClass="form-control" hasChildLookup="false" hasId="true"
			showAll="false" selectOptionLabelCode="applicantinfo.label.select"
			isMandatory="true" />

		<label class="col-sm-2 control-label  required-control"><spring:message
				code="account.bankmaster.bankcode" text="Bank Code" /></label>
		<div class="col-sm-4">
			<form:input path="bmBankcode" cssClass="form-control" id="bankCode"
				maxlength="6"></form:input>
		</div>
	</div>

	<div class="form-group">
		<label class="col-sm-2 control-label required-control"><spring:message
				code="account.bankmaster.bankname" text=" Bank Name" /></label>
		<div class="col-sm-4">
			<form:input path="bmBankname" cssClass="form-control" id="bankName"></form:input>
		</div>
		<label class="col-sm-2 control-label"><spring:message
				code="account.bankmaster.bankbranch" text=" Bank Branch" /> </label>
		<div class="col-sm-4">
			<form:input path="bmBankbranch" cssClass="form-control"
				id="bankBranch"></form:input>
		</div>
	</div>

	<div class="form-group">
		<label class="col-sm-2 control-label"><spring:message
				code="account.bankmaster.bankcontactno" text=" Bank Contact No." /></label>
		<div class="col-sm-4">
			<form:input path="bmBankcontactnos" cssClass="form-control"
				id="bankContact" maxlength="20"></form:input>
		</div>
		<label class="col-sm-2 control-label"><spring:message
				code="account.bankmaster.bankaddress" text="Bank Address" /> </label>
		<div class="col-sm-4">
			<form:textarea path="bmBankaddress" class="form-control"
				id="bankAddress"></form:textarea>
		</div>
	</div>


	<div class="form-group">
		<label class="col-sm-2 control-label"><spring:message
				code="account.bankmaster.email" text="E-mail" /> </label>
		<div class="col-sm-4">
			<form:input path="bmBankemail" cssClass="form-control" id="bankEmail"></form:input>
		</div>
	</div>

	<h4>
		<spring:message code="account.bankmaster.accountdetail"
			text="Bank Account Details" />
	</h4>
	<c:choose>
		<c:when test="${empty tbBankmaster.listOfTbBankaccount}">
			<table class="table table-bordered" id="tbl1">
				<tr>
					<th scope="col"><spring:message code="account.bankmaster.srno"
							text="Sr No" /></th>
					<th scope="col"><spring:message
							code="accounts.vendormaster.bankAccountNumber"
							text="Bank Account Number" /><span class="mand">*</span></th>
					<th scope="col"><spring:message code="bank.master.accountType"
							text="Account Type" /><span class="mand">*</span></th>
					<th scope="col"><spring:message code="bank.master.accountName"
							text="Account Name" /><span class="mand">*</span></th>
					<th scope="col"><spring:message code="bank.master.opdate"
							text="Opening Date" /></th>
					<th scope="col"><spring:message code="bank.master.opBal"
							text="Opening Balance" /><span class="mand">*</span></th>
					<th scope="col"><spring:message code="bank.master.closeBal"
							text="Closing Balance" /></th>
					<th scope="col"><spring:message code="bank.master.acc.status"
							text="A/c Status" /><span class="mand">*</span></th>
					<th scope="col"><spring:message code="bank.master.isfundReq"
							text="Is Fund Required" /><span class="mand">*</span></th>
					<th scope="col"><spring:message code="bank.master.viewFund"
							text="View Fund Details" /></th>
					<th><a data-toggle="tooltip" data-placement="top" title=""
						class="btn btn-blue-2 btn-sm" data-original-title="Add Account"
						id="addAccount"><i class="fa fa-plus"></i></a></th>
				</tr>

				<tr id="tr0" class="accountClass">
					<td id="srNo">1</td>
					<td><form:input path="listOfTbBankaccount[0].baAccountcode"
							cssClass="form-control" id="accountNo0" maxlength="20"></form:input></td>
					<td><form:select path="listOfTbBankaccount[0].cpdAccounttype"
							class="form-control" id="cpdAccounttype0">
							<form:option value="0">
								<spring:message code="bank.master.seltype" text="Select Type" />
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
					<td><form:input path="listOfTbBankaccount[0].baAccountname"
							cssClass="form-control" id="accountName0"></form:input></td>
					<td><form:input path="listOfTbBankaccount[0].tempdate"
							cssClass="cal form-control lessthancurrdate" id="openingDate0"></form:input></td>
					<td><form:input
							path="listOfTbBankaccount[0].listOfTbBankaccountBal[0].babOpeningbalance"
							cssClass="form-control hasNumber" id="opBal0"
							onkeyup="copyBal(0)" maxlength="12"></form:input></td>
					<td><form:input
							path="listOfTbBankaccount[0].listOfTbBankaccountBal[0].babClosingbalance"
							cssClass="form-control hasNumber" id="closeBal0" readonly="true"
							maxlength="12"></form:input></td>
					<td><form:select path="listOfTbBankaccount[0].acCpdId"
							class="form-control" id="babStatus0">
							<form:option value="0">
								<spring:message code="accounts.master.sel.status"
									text="Select Status" />
							</form:option>
							<c:forEach items="${accountStatus}" var="lookUp">
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
					<td>
						<div class="checkbox-inline">
							<form:checkbox path="listOfTbBankaccount[0].fundAplFlag"
								value="Y" id="isFundRequired0" onclick="openFundForm(0)" />
						</div>
					</td>
					<td>
						<button type="button" class="btn btn-success viewFund"
							onclick="viewFundDetails(0,'${form_mode}')" disabled="disabled"
							id="viewFund0">
							<spring:message code="bank.master.view" text="View" />
						</button>
					</td>
					<td><a data-toggle="tooltip" data-placement="top" title=""
						class="btn btn-danger btn-sm deletClass"
						data-original-title="Delete Account" id=deleteAccount0
						onclick="removeRow(0,'${form_mode}')"><i class="fa fa-trash"></i></a>
					</td>
				</tr>
			</table>
		</c:when>
		<c:otherwise>
			<table class="table table-bordered" id="tbl1">
				<tr>
					<th scope="col"><spring:message code="account.bankmaster.srno"
							text="Sr No" /></th>
					<th scope="col"><spring:message
							code="accounts.vendormaster.bankAccountNumber"
							text="Bank Account Number" /><span class="mand">*</span></th>
					<th scope="col"><spring:message code="bank.master.accountType"
							text="Account Type" /><span class="mand">*</span></th>
					<th scope="col"><spring:message code="bank.master.accountName"
							text="Account Name" /><span class="mand">*</span></th>
					<th scope="col"><spring:message code="bank.master.opdate"
							text="Opening Date" /></th>
					<th scope="col"><spring:message code="bank.master.opBal"
							text="Opening Balance" /><span class="mand">*</span></th>
					<th scope="col"><spring:message code="bank.master.closeBal"
							text="Closing Balance" /></th>
					<th scope="col"><spring:message code="bank.master.acc.status"
							text="A/c Status" /><span class="mand">*</span></th>
					<th scope="col"><spring:message code="bank.master.isfundReq"
							text="Is Fund Required" /><span class="mand">*</span></th>
					<th scope="col"><spring:message code="bank.master.viewFund"
							text="View Fund Details" /></th>
					<th><a data-toggle="tooltip" data-placement="top" title=""
						class="btn btn-blue-2 btn-sm" data-original-title="Add Account"
						id="addAccount"><i class="fa fa-plus"></i></a></th>
				</tr>
				<c:forEach items="${tbBankmaster.listOfTbBankaccount}" var="details"
					varStatus="status">
					<tr id="tr${status.count-1}" class="accountClass">
						<form:hidden
							path="listOfTbBankaccount[${status.count-1}].baAccountid" />
						<td id="srNo">${status.count}</td>
						<td><form:input
								path="listOfTbBankaccount[${status.count-1}].baAccountcode"
								cssClass="form-control" id="accountNo${status.count-1}"
								maxlength="20"></form:input></td>
						<td><form:select
								path="listOfTbBankaccount[${status.count-1}].cpdAccounttype"
								class="form-control" id="cpdAccounttype${status.count-1}">
								<form:option value="0">select</form:option>
								<c:forEach items="${accountType}" var="lookUp">

									<form:option value="${lookUp.lookUpId}"
										code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
								</c:forEach>
							</form:select></td>
						<td><form:input
								path="listOfTbBankaccount[${status.count-1}].baAccountname"
								cssClass="form-control" id="accountName${status.count-1}"></form:input></td>
						<td><form:input
								path="listOfTbBankaccount[${status.count-1}].tempdate"
								cssClass="cal form-control lessthancurrdate"
								id="openingDate${status.count-1}"></form:input></td>
						<td><form:input
								path="listOfTbBankaccount[${status.count-1}].listOfTbBankaccountBal[0].babOpeningbalance"
								cssClass="form-control hasNumber" id="opBal${status.count-1}"
								onkeyup="copyBal(${status.count-1})" maxlength="12"></form:input>
							<form:hidden
								path="listOfTbBankaccount[${status.count-1}].listOfTbBankaccountBal[0].babBalanceid" /></td>
						<td><form:input
								path="listOfTbBankaccount[${status.count-1}].listOfTbBankaccountBal[0].babClosingbalance"
								cssClass="form-control hasNumber" id="closeBal${status.count-1}"
								readonly="true" maxlength="12"></form:input></td>
						<td><form:select
								path="listOfTbBankaccount[${status.count-1}].acCpdId"
								class="form-control" id="babStatus${status.count-1}">
								<form:option value="0">
									<spring:message code="accounts.master.sel.status"
										text="Select Status" />
								</form:option>
								<c:forEach items="${accountStatus}" var="lookUp">
									<form:option value="${lookUp.lookUpId}"
										code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
								</c:forEach>
							</form:select></td>
						<td>
							<div class="checkbox-inline">
								<form:checkbox
									path="listOfTbBankaccount[${status.count-1}].fundAplFlag"
									value="Y" id="isFundRequired${status.count-1}"
									onclick="openFundForm(${status.count-1})" />
							</div>
						</td>
						<td>
							<button type="button" class="btn btn-success viewFund"
								onclick="viewFundDetails(${status.count-1},'${form_mode}')"
								id="viewFund${status.count-1}">
								<spring:message code="bank.master.view" text="View" />
							</button>
						</td>
						<td><a data-toggle="tooltip" data-placement="top" title=""
							class="btn btn-danger btn-sm deletClass"
							data-original-title="Delete Account"
							id="deleteAccount${status.count-1}"
							onclick="removeRow(${status.count-1},'${form_mode}')"> <i
								class="fa fa-trash"></i></a></td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>

	<br>
	<c:if test="${form_mode ne 'view'}">
		<div class="text-center padding-bottom-20">
			<button type="button" class="btn btn-success btn-submit"
				onclick="saveBankMaster(this)">
				<spring:message code="account.bankmaster.save" text="Save" />
			</button>
			<button type="Reset" class="btn btn-default">
				<spring:message code="account.bankmaster.clear" text="Clear" />
			</button>
			<input type="button" class="btn btn-danger"
				onclick="window.location.href='BankMaster.html'"
				value="<spring:message code="water.btn.cancel"/>" id="cancelEdit" />
		</div>
	</c:if>
	<c:if test="${form_mode eq 'view'}">
		<div class="text-center padding-bottom-20">
			<input type="button" class="btn btn-danger"
				onclick="window.location.href='BankMaster.html'"
				value="<spring:message code="water.btn.cancel"/>" id="cancelEdit" />
		</div>
	</c:if>
</form:form>


