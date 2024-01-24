<?xml version="1.0" encoding="UTF-8" standalone="no"?>

<div xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:s="http://www.springframework.org/tags"
	xmlns:form="http://www.springframework.org/tags/form"
	xmlns:util="urn:jsptagdir:/WEB-INF/tags/util"
	xmlns:input="urn:jsptagdir:/WEB-INF/tags/input" version="2.0">
	<%@page import="java.util.Date"%>
	<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
	<%
	response.setContentType("text/html; charset=utf-8");
%>
	<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<script src="js/account/SecondaryheadMaster.js"></script>
	<SCRIPT>
$(document).ready(function() {
	var x = $(".listOfTbAcFunctionMasterItems").find(':selected').attr('code');
	$("#primeryObejctdiscription").val(x);
	$("#bmBankid").change(function() {
		var bmBankid = $(this).val();
		var selectobject=document.getElementById('baAccountid');
		$(selectobject).find('option:gt(0)').remove();
		
		if (bmBankid > 0 && bmBankid!='')
		{
			var postdata = 'bmBankid=' + bmBankid;
			
			var json = __doAjaxRequest('tbAcSecondaryheadMaster.html?bankaccountList','POST', postdata, false, 'json');

			var  optionsAsString='';

			for(var i = 0; i < json.length; i++)
			{
			    optionsAsString += "<option value='" + json[i].baAccountid + "'>" + json[i].baAccountcode+"-"+ json[i].baAccountname + "</option>";
			}
			$("#baAccountid").append( optionsAsString );
		
		}
		});			

	
	
	if($(LedgerType).find(':selected').attr('code') == 'VD')
	{
	
	$(".BankName").hide();
	$(".Bank").hide();
	$(".Other").hide();
	$(".Vendor").show();
	$(".VendorList").show();
	
	}

if($(LedgerType).find(':selected').attr('code') == 'BK')
{

$(".BankName").show();
$(".Bank").show();
$(".Other").hide();
$(".Vendor").hide();
$(".VendorList").hide();
}
if($(LedgerType).find(':selected').attr('code') == '0')
{

$(".BankName").hide();
$(".Bank").hide();
$(".Other").hide();
$(".Vendor").hide();
$(".VendorList").hide();
}
if($(LedgerType).find(':selected').attr('code') == 'OT')
{

$(".Other").show();
$(".BankName").hide();
$(".Bank").hide();
$(".Vendor").hide();
$(".VendorList").hide();

}
	
		
	});

</SCRIPT>
	<c:if test="${mode != 'create'}">
		<SCRIPT>
	$(document).ready(function() {
		jQuery('.hasCharacter').keyup(function () { 
		    this.value = this.value.replace(/[^a-z A-Z]/g,'');
		});
		
		
		$("#listOfTbAcFunctionMasterItems").attr('disabled', 'disabled');
		$("#LedgerType").attr('disabled', 'disabled');
		$("#bmBankid").attr('disabled', 'disabled');
		$("#baAccountid").attr('disabled', 'disabled');
		$("#baAccountidedit").attr('disabled', 'disabled');
		$("#vmVendorid").attr('disabled', 'disabled');
	});
	
	
</SCRIPT>
	</c:if>
	<jsp:directive.page contentType="text/html;charset=UTF-8" />
	<apptags:breadcrumb></apptags:breadcrumb>

	<div id="heading_wrapper" class="content">


		<div class="widget" id="widget">
			<div class="widget-header">

				<h2>
					<spring:message code="" text="Secondary  Account Headh Master" />
				</h2>
			</div>

			<div class="widget-content padding">

				<util:message message="${message}" messages="${messages}" />

				<s:url value="tbAcSecondaryheadMaster?create" var="url_form_submit" />


				<form:form class="form-horizontal"
					modelAttribute="secondaryheadMaster" cssClass=" form-horizontal"
					method="POST" action="${url_form_submit}">
					<form:errors path="*" cssClass="alert alert-danger  hello"
						element="div" onclick="">
					</form:errors>



					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse"
										href="#Additional_Owners"><spring:message
											code="accounts.Secondaryhead.AccountCodeDetails"
											text="Secondaryhead Master" /></a>
								</h4>
							</div>
							<div id="Additional_Owners" class="panel-collapse collapse in">
								<div class="panel-body">
									<c:if test="${mode != 'create'}">
										<!-- Store data in hidden fields in order to be POST even if the field is disabled -->
										<form:hidden path="sacHeadId" />
									</c:if>

									<DIV class="form-group">
										<label class="col-sm-2 control-label required-control"><spring:message
												code="account.primary.obj.code" text="primary Object code:" /></label>
										<div class="col-sm-4">
											<form:select id="listOfTbAcFunctionMasterItems"
												path="pacHeadId"
												cssClass="listOfTbAcFunctionMasterItems form-control"
												onchange="">
												<form:option value="">
													<spring:message code="account.select" text="Select" />
												</form:option>
												<c:forEach items="${listOfTbAcFunctionMasterItems}"
													varStatus="status" var="functionItem">
													<form:option value="${functionItem.key}"
														code="${functionItem.value}">${functionItem.value}</form:option>
												</c:forEach>
											</form:select>

										</div>
										<label class="col-sm-2 control-label required-control"><spring:message
												code="account.primary.obj.description"
												text="primary Object   description:" /></label>
										<div class="col-sm-4">
											<form:input id="primeryObejctdiscription" path=""
												class=" form-control primeryObejctdiscription "
												maxLength="20" readonly="true" />
										</div>

									</DIV>
									<div class="form-group LedgerType1">
										<label class="col-sm-2 control-label required-control"><spring:message
												code="account.ledger.type" text="LedgerType:" /></label>
										<div class="col-sm-4">

											<form:select id="LedgerType" path="sacLeddgerTypeCpdId"
												cssClass="form-control LedgerType">
												<form:option value="" code="0">
													<spring:message code="account.select" text="Select" />
												</form:option>
												<c:forEach items="${legerTypeList}" var="LedgerType">
													<form:option value="${LedgerType.lookUpId}"
														code="${LedgerType.lookUpCode}">${LedgerType.descLangSecond}</form:option>
												</c:forEach>
											</form:select>


										</div>



									</div>




									<div class="form-group  BankName">
										<label class="col-sm-2 control-label required-control"><spring:message
												code="account.bank.name" text="Bank Name:" /></label>
										<div class="col-sm-4">

											<form:select id="bmBankid" path="bankName"
												cssClass="form-control bmBankid">
												<form:option value="">
													<spring:message code="accounts.chequebookleaf.select_bank"
														text="Select Bank" />
												</form:option>
												<c:forEach items="${banklist}" var="departMstData">
													<form:option value="${departMstData.bmBankid}"
														code="${departMstData.bmBankname}">${departMstData.bmBankname}</form:option>
												</c:forEach>
											</form:select>


										</div>


										<label class="col-sm-2 control-label required-control"><spring:message
												code="account.bank.accounts" text="Bank Accounts :" /></label>
										<div class="col-sm-4">
											<c:if test="${mode == 'create'}">
												<form:select id="baAccountid" path="baAccountid"
													cssClass="form-control baAccountid">
													<form:option value="" code="">
														<spring:message code="account.select.bank.acc"
															text="Select Bank Accounts" />
													</form:option>
												</form:select>
											</c:if>
											<c:if test="${mode != 'create'}">
												<form:select id="baAccountidedit" path="baAccountid"
													cssClass=" form-control baAccountid">
													<form:option value="">
														<spring:message code="account.select.bank.acc"
															text="Select Bank Accounts" />
													</form:option>
													<c:forEach items="${tbBankaccount1}" var="baAccountidedit">
														<form:option value="${baAccountidedit.baAccountid}"
															code="${baAccountidedit.baAccountcode} - ${baAccountidedit.baAccountname}">  ${baAccountidedit.baAccountcode} - ${baAccountidedit.baAccountname}</form:option>
													</c:forEach>
												</form:select>
											</c:if>
										</div>
									</div>

									<div class="form-group VendorList">
										<label class="col-sm-2 control-label required-control"><spring:message
												code="accounts.Secondaryhead.VendorList" text="VendorList" />
											:</label>
										<div class="col-sm-4">

											<form:select id="vmVendorid" path="vmVendorid"
												cssClass="form-control vmVendorid">
												<form:option value="">
													<spring:message code="account.select" text="Select " />
												</form:option>
												<c:forEach items="${VendorList}" var="Vendor">
													<form:option value="${Vendor.vmVendorid}"
														code="${Vendor.vmVendorcode}_ ${Vendor.vmVendorname}">${Vendor.vmVendorcode}_ ${Vendor.vmVendorname}</form:option>
												</c:forEach>
											</form:select>


										</div>



									</div>
								</div>


							</div>

							<div class="panel panel-default Other">
								<div class="">
									<h4 class="panel-title">
										<a data-toggle="" class=""
											data-parent="#accordion_single_collapse" href="#other"><spring:message
												code="accounts.Secondaryhead.Other" text="Other" /></a>
									</h4>
								</div>
								<div id="other" class="panel-collapse collapse in">
									<div class="panel-body"></div>
								</div>
							</div>

							<div class="panel panel-default Vendor">
								<div class="">
									<h4 class="panel-title">
										<a data-toggle="" class=""
											data-parent="#accordion_single_collapse" href="#vendor"><spring:message
												code="accounts.Secondaryhead.Vendor" text="Vendor" /></a>
									</h4>
								</div>
								<div id="vendor" class="panel-collapse collapse in">
									<div class="panel-body"></div>
								</div>
							</div>


							<div class="panel panel-default Bank">
								<div class="">
									<h4 class="panel-title">
										<a data-toggle="" class=""
											data-parent="#accordion_single_collapse" href="#other"><spring:message
												code="accounts.Secondaryhead.Bank" text="Bank" /></a>
									</h4>
								</div>
								<div id="other" class="panel-collapse collapse in">
									<div class="panel-body"></div>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="account.secondary.description"
										text="Secondary account description:" /></label>
								<div class="col-sm-4">
									<form:textarea id="" path="sacHeadDesc"
										class=" form-control  sacHeadDesc " maxLength="20"
										readonly="false" />
								</div>

								<label class="col-sm-2 control-label required-control"><spring:message
										code="account.secondary.code" text="Secondary account Code:" /></label>
								<div class="col-sm-4">
									<form:input id="" path="sacHeadCode"
										class=" form-control  sacHeadCode " maxLength="20"
										readonly="true" />
								</div>
							</div>


							<!-- ACTION BUTTONS -->
							<div class=" text-center padding-top-10 ">

								<c:if test="${mode != 'create'}">
									<!-- "DELETE" button ( HREF link ) -->
									<s:url var="deleteButtonURL"
										value="/tbAcSecondaryheadMaster/delete/${tbAcSecondaryheadMaster.sacHeadId}" />
								</c:if>

								<button type="submit" class="btn btn-success btn-submit">
									<s:message code="save" />
									<spring:message code="account.configuration.save" text="Save" />
								</button>
								<!-- "CANCEL" button ( HREF link ) -->
								<s:url var="cancelButtonURL" value="/tbAcSecondaryheadMaster" />
								<a role="button" class="btn btn-danger"
									href="tbAcSecondaryheadMaster.html"><s:message
										code="cancel" /> <spring:message code="account.btn.cancel"
										text="cancel" /></a>


								<!-- "SAVE" button ( SUBMIT button ) -->


							</div>
				</form:form>

			</div>
		</div>