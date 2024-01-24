 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
	<script>
	 $(document).ready(function() {
		   var workOrder  = $("#workOrder").val();
		   if(workOrder!="" ||  workOrder!=null)
			  {
			   
			    var	errMsgDiv='.msg-dialog-box';
				var message='';
				var label = "Proceed";
				
				message	+='<h5 class=\"text-center text-blue-2 padding-2\">Your Application Id is :</h5>' 
				message	+='<h6 class=\"text-center text-blue-2 padding-10\">'+workOrder+'</h6>' 
				 message	+='<div class=\'text-center padding-bottom-10\'>'+	
					'<input type=\'button\' value=\''+label+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
					' onclick="closeBox()"/>'+
					'</div>';

				$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
				$(errMsgDiv).html(message);
				$(errMsgDiv).show();
				$('#btnNo').focus();
				showModalBox(errMsgDiv);
			     
			  }
		});	
	
	function printdiv(printpage)
	{
		var headstr = "<html><head><title></title></head><body>";
		var footstr = "</body>";
		var newstr = document.all.item(printpage).innerHTML;
		var oldstr = document.body.innerHTML;
		document.body.innerHTML = headstr+newstr+footstr;
		window.print();
		document.body.innerHTML = oldstr;
		return false;
	}
	
	 function closeBox () {
			$.fancybox.close();
		}
</script>
<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>   
    <div class="content">
      <div class="widget">
        <div class="widget-header">
          <h2><spring:message code="rtgs.payment.advice"
						 text="RTGS Payment Advice" /></h2>
        </div>
        <div class="widget-content padding">
		
		<form:hidden path="command.bpoDto.workOrderNumber" id= "workOrder"/>
	
          <form action="" method="get" class="form-horizontal" >
          
            <div id="receipt">
              <div class="form-group">
             
                <div class="col-xs-2"><img src="${userSession.orgLogoPath}" width="80"></div>
                <div class="col-xs-12 col-sm-12 text-center">
                 <%--  <h3 class="text-extra-large">${ userSession.getCurrent().organisation.ONlsOrgname}</h3> --%>
                  <h3 class="text-extra-large margin-bottom-0 margin-top-0">
                                <c:if test="${userSession.languageId eq 1}">
                                     ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
                                </c:if>
                                <c:if test="${userSession.languageId eq 2}">
                                      ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
                                </c:if>                            
                            </h3>
                  </input>
                  
                <%--      <input id = "workOrder" value =${ command.bpoDto.workOrderNumber} type= "text" readOnly = "true"> --%>
                  <p><spring:message code="rtgs.payment.advice"
						 text="RTGS Payment Advice" /></p>
                </div>
               
              </div>
              <table class="table table-bordered table-condensed">
                <tr>
                  <th><spring:message code="rtgs.payment.ulb1"
						 text="ULB Name" /></th>
                  <td colspan="4">${ userSession.getCurrent().organisation.ONlsOrgname}</td>
                  
                  
                </tr>
                <tr>
                  <th><spring:message code="rtgs.payment.department1"
						 text="Department" /></th>
                  <td colspan="5">${command.bpoDto.departmentName}</td>
                </tr>
                <tr>
                  <th><spring:message code="rtgs.payment.entry1"
						 text="RTGS Entry No." /></th>
                  <td>456632</td>
                  <th><spring:message code="rtgs.payment.date1"
						 text="Date" /></th>
                  <td colspan="2"><fmt:formatDate pattern="dd-MM-yyyy" value="${command.bpoDto.date}" /></td>
                  
                </tr>
                <tr>
                  <th><spring:message code="rtgs.payment.beneficiary1"
						 text="Beneficiary Name" /></th>
                  <th><spring:message code="rtgs.payment.bank.branch"
						 text="Bank Name & Branch Name" /></th>
				  <th><spring:message code="rtgs.payment.beneficiary2"
						 text="Beneficiary A/c No." /></th>
				  <th><spring:message code="rtgs.payment.ifsc"
						 text="IFSC Code" /></th>
				  <th><spring:message code="rtgs.payment.amount"
						 text="Amount" /></th>
                </tr>
                <c:set var="total" value="${0}" scope="page"/>
                <c:forEach items="${command.bpoDto.dtoList}" var="dtoList">
												<tr>
													<td>${dtoList.beneficiaryName}</td>
													<td>${dtoList.bankName}-${dtoList.branchName}</td>
													<td>${dtoList.accountNumber}</td>
													<td>${dtoList.ifscCode}</td>
													<td align="right">${dtoList.amount}</td>
												</tr>
												 <c:set var="total" value="${total + dtoList.amount}" scope="page"/>
											</c:forEach>
               
                <tr>
                  <th colspan="4" class="text-right"><spring:message code="rtgs.payment.total"
						 text="Total" /></th>
                  <td align="right">${total}</td>
                 </tr>
				  <tr>
                  
                  <th><spring:message code="rtgs.payment.amount1"
						 text="Amount in Words" /></th>
				  <td colspan="4">
				 
				  <spring:eval expression="T(com.abm.mainet.common.utility.Utility).convertNumberToWord(${total})"
											var="lookup" />${lookup} </td>
                 </tr>
               
              
               
              </table>
             
             
              
              <div class="text-center hidden-print padding-10">
                <button onclick="printdiv('receipt');" class="btn btn-primary hidden-print"><i class="fa fa-print"></i>
                <spring:message code="rtgs.payment.print"
						 text="Print" /> </button>
               
               <input type="button" class="btn btn-danger"
							onclick="window.location.href='BeneficiaryPaymentOrder.html'"
							code="rtgs.payment.cancel"
							value="Cancel" id="cancelEdit" />
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
    </div>
  