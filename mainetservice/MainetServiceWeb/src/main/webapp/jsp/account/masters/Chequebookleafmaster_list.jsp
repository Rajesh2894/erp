<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/ui/i18n/grid.locale-en.js"></script>
<script src="js/mainet/jquery.jqGrid.min.js"></script>

<script> 

function SearchChequeData(){

	debugger;
	$('.error-div').hide();

	var errorList = [];

	var bmBankid = $("#bmBankid").val();
	
	if (bmBankid == "" || bmBankid =="0") {
		errorList.push(getLocalMessage('account.select.bank'));
	}
	
	if(errorList.length>0){
    	
    	var errorMsg = '<ul>';
    	$.each(errorList, function(index){
    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';   		
    	});
    	errorMsg +='</ul>';
    	$('#errorId').html(errorMsg);
    	$('#errorDivId').show();
		$('html,body').animate({ scrollTop: 0 }, 'slow');   	
    }
	 
	if (errorList.length == 0) 
	{
	
	var url = "Chequebookleafmaster.html?getChequeData";

	var requestData = {"bmBankid" :bmBankid,
	};
	
	var result = __doAjaxRequest(url, 'POST', requestData, false, 'json');
	
	if (result != null && result != "") {
		$("#grid").jqGrid('setGridParam', {
			datatype : 'json'
		}).trigger('reloadGrid');
	} else {
		var errorList = [];
		
		errorList.push(getLocalMessage("No records found for selected criteria"));
		
		if(errorList.length>0){		    	
	    	var errorMsg = '<ul>';
	    	$.each(errorList, function(index){
	    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';		    		
	    	});
	    	errorMsg +='</ul>';		    	
	    	$('#errorId').html(errorMsg);
	    	$('#errorDivId').show();
			$('html,body').animate({ scrollTop: 0 }, 'slow');		    		    	
	    }
		$("#grid").jqGrid('setGridParam', {
			datatype : 'json'
		}).trigger('reloadGrid');
	}
}
};

function closeErrBox() {
	$('.error-div').hide();
}






$(function() {	
	$(document).on('click', '.createData', function() {

		var url = "Chequebookleafmaster.html?form";
	    $.ajax({
			url : url,
			success : function(response) {					
				$(".widget-content").html(response);
				$(".widget-content").show();
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});				
	});			
});


var chequebookId = '';
$(function() {
	 $("#grid").jqGrid(
			{
				url : "Chequebookleafmaster.html?getLoadGridData",datatype : "json",mtype : "GET",colNames : [getLocalMessage('accounts.chequebook.id'), getLocalMessage('accounts.fromcheque.no'), getLocalMessage('accounts.tocheque.no'),getLocalMessage('accounts.chequebookleaf.total_Cheque_no'), getLocalMessage('bill.action')],
				colModel : [
				             {name : "chequebookId",width : 0,sortable : true,hidden: true ,searchoptions: { "sopt": ["bw", "eq"] }}, 
				             {name : "fromChequeNo",width : 20,sortable : true, searchoptions: { "sopt": ["bw", "eq"] }}, 
				             {name : "toChequeNo",width : 45,sortable : true, searchoptions: { "sopt": ["bw", "eq"] }}, 
				             {name : "checkbookLeave",width : 75,sortable : false,searchoptions: { "sopt": [ "eq"] }} , 
				             {name : 'chequebookId', index: 'chequebookId', width:30 , align: 'center !important',formatter:addLink,search :false}
				            ],
				pager : "#pagered",
				emptyrecords: getLocalMessage("jggrid.empty.records.text"),
				rowNum : 30,
				rowList : [ 5, 10, 20, 30 ],
				sortname : "chequebookId",
				sortorder : "desc",
				height : 'auto', 
				viewrecords : true,
				gridview : true,
				loadonce : true,
				jsonReader : {
					root : "rows",
					page : "page",
					total : "total",
					records : "records",
					repeatitems : false,
				},
				autoencode : true,
				caption : getLocalMessage("cheque.Book.Leaf")
			});
	 jQuery("#grid").jqGrid('navGrid','#pagered',{edit:false,add:false,del:false,search:true,refresh:false}); 
	 $("#pagered_left").css("width", "");
}); 

function addLink(cellvalue, options, rowdata) 
{
   return "<a class='btn btn-blue-3 btn-sm viewCheqlefClass' title='View'value='"+rowdata.chequebookId+"' chequebookId='"+rowdata.chequebookId+"' ><i class='fa fa-building-o'></i></a> " +
   		 "<a class='btn btn-warning btn-sm editCheqlefClass' title='Edit'value='"+rowdata.chequebookId+"' chequebookId='"+rowdata.chequebookId+"' ><i class='fa fa-pencil'></i></a> ";
}

function returnEditUrl(cellValue, options, rowdata, action) {
	chequebookId = rowdata.chequebookId;
	return "<a href='#'  return false; class='editCheqlefClass'><img src='css/images/edit.png' width='20px' alt='Edit  Master' title='Edit  Master' /></a>";
}


function returnViewUrl(cellValue, options, rowdata, action) {
	chequebookId = rowdata.chequebookId;
	return "<a href='#'  return false; class='viewCheqlefClass'><img src='css/images/grid/view-icon.png' width='20px' alt='View  Master' title='View  Master' /></a>";
}


$(function() {
	$(document)
	.on('click',
			'.editCheqlefClass',
			function() {
				var $link = $(this);
				var chequebookId = $link.closest('tr').find('td:eq(0)').text();
				var url = "Chequebookleafmaster.html?formForUpdate";
				var requestData = "chequebookId=" + chequebookId + "&MODE1=" + "Edit";
				var returnData =__doAjaxRequest(url,'post',requestData,false);
				var divName = '.content';
				$(divName).removeClass('ajaxloader');
				$(divName).html(returnData);
				return false;
			});

	$(document)
			.on('click',
					'.viewCheqlefClass',
					function() {
						var $link = $(this);
						var chequebookId = $link.closest('tr').find('td:eq(0)').text();
						var url = "Chequebookleafmaster.html?viewMode";
						var requestData = "chequebookId=" + chequebookId + "&MODE1=" + "View";
						var returnData =__doAjaxRequest(url,'post',requestData,false);
						var divName = '.content';
						$(divName).removeClass('ajaxloader');
						$(divName).html(returnData);
						return false;
					});

});






</script>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content form-div" id="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="chequebookleaf.master.cheque"
						text="Cheque" /></strong>
				<spring:message code="chequebookleaf.master.book.leaf"
					text="Book Leaf Master" />
			</h2>
		<apptags:helpDoc url="Chequebookleafmaster.html" helpDocRefURL="Chequebookleafmaster.html"></apptags:helpDoc>		
		</div>
		<div class="widget-content padding">


			<form:form method="post" action="Chequebookleafmaster.html"
				name="chequebookleafmaster" id="chequebookleafmaster"
				class="form-horizontal" modelAttribute="tbAcChequebookleafMas">

				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<div id="parentDataDivId">
					<div class="form-group">
						<label class="col-sm-2 control-label requiresd-control"><spring:message
								code="accounts.Secondaryhead.BankName" text="Bank Name" /></label>
						<div class="col-sm-4">
							<form:select id="bmBankid" path="bmBankid"
								cssClass="form-control mandColorClass chosen-select-no-results">
								<form:option value="">
									<spring:message code="accounts.chequebookleaf.select_bank"
										text="Select Bank" />
								</form:option>
								<c:forEach items="${bankMap}" var="departMstData">
									<form:option value="${departMstData.key}"> ${departMstData.value}</form:option>
								</c:forEach>
							</form:select>
						</div>

					</div>
				</div>


				<div class="text-center margin-bottom-10">

					<button type="button" class="btn btn-success"
						onclick="SearchChequeData()">
						<i class="fa fa-search"></i>
						<spring:message code="account.bankmaster.search" text="Search" />
					</button>
					<input type="button" class="btn btn-warning"
						onclick="javascript:openRelatedForm('Chequebookleafmaster.html');"
						value="<spring:message code="account.bankmaster.reset" text="Reset"/>" id="cancelEdit" />
					<button type="button" class="btn btn-blue-2 createData"
						id="addButton">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="account.bankmaster.create" text="Create" />
					</button>
				</div>


				<table id="grid"></table>
				<div id="pagered"></div>

			</form:form>
		</div>
	</div>
</div>