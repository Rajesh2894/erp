<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
response.setContentType("text/html; charset=utf-8");
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/ui/i18n/grid.locale-en.js"></script>
<script src="js/mainet/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="js/material_mgmt/service/GoodsRecievedNotes.js"></script>

<div id="GoodsItem">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="Goods.Received.Note" text="Goods Received Note" />
				</h2>
			</div>
			<div class="widget-content padding">
				<div class="mand-label clearfix">
					<span><spring:message code="material.management.mand"
							text="Field with" /> <i class="text-red-1">*</i> <spring:message
							code="material.management.mand.field" text="is mandatory" /> </span>
				</div>
				<form:form action="GoodsReceivedNotesItem.html" name="Goods Received Note " id="GoodsReceivedNote"
					class="form-horizontal" commandName="command">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>
					
					<form:hidden path="saveMode" id="saveMode" />

					<div class=" form-group ">
						<c:if test="${command.saveMode eq 'V'}">
							<apptags:input labelCode="material.management.grnNo"
									path="goodsNotesDto.grnno" isDisabled="true" />
						</c:if>
						
						<apptags:date labelCode="goods.recieved.date" cssClass="fromDateClass"
							datePath="goodsNotesDto.receiveddate" fieldclass="lessthancurrdate"
							isMandatory="true" isDisabled="${command.saveMode eq 'V'}"></apptags:date>
					</div>
					

					<div class="form-group control-label">
						<c:choose>
							<c:when test="${command.saveMode ne 'V'}">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="purchase.requisition.purOrderNo" /></label>
								<div class="col-sm-4">
									<form:select path="goodsNotesDto.poid" id="poNo" data-rule-required="true" 
										class="form-control chosen-select-no-results input-group" onchange="getpoitem()">
										<form:option value="">
											<spring:message code="adh.select" text="Select"></spring:message>
										</form:option>
										<c:forEach items="${command.purchaseOrderObjects}" var="poObject">
											<form:option value="${poObject[0]}">${poObject[1]}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</c:when>
							<c:otherwise>
								<form:hidden path="goodsNotesDto.poid" id="poId" />
								<apptags:input labelCode="Purchase.order.number"
									path="goodsNotesDto.poNumber" isMandatory="true"
									isReadonly="true" />
							</c:otherwise>
						</c:choose>
						
						<apptags:date labelCode="Purchase.order.date" datePath="goodsNotesDto.poDate"
							fieldclass="datepicker" isDisabled="true"></apptags:date>
					</div>

					<div class=" form-group ">
						<form:hidden path="goodsNotesDto.storeid" id="storeId" />
						<apptags:input labelCode="material.management.store" path="goodsNotesDto.storeName" 
								isMandatory="true" isReadonly="true" />
					</div>
					
					<h4>
						<spring:message code="Goods.Received.Entry" text="Goods Received Entry" />
					</h4>
					<div id="GoodsEntry">
						<c:set var="e" value="0" scope="page" />
						<table class="table table-bordered margin-bottom-10 " id="GoodsEntryTable">
							<thead>
								<tr>
									<th class="text-center"><spring:message code="Sr.No" text="Sr No" /></th>
									<th class="text-center"><spring:message code="material.item.master.name"
											text="Item Name" /></th>
									<th class="text-center"><spring:message code="U.o.M" text="UoM" /></th>
									<th class="text-center"><spring:message code="Item.Management.Method" 
											text="Item Management Method" /></th>
									<th class="text-center"><spring:message code="Ordered.Quantity" 
											text="Ordered Quantity" /></th>
									<th class="text-center"><spring:message code="Previously.Recieved.Quantity"
											text="Previously Recieved Quantity" /></th>
									<th class="text-center"><spring:message code="material.management.receivedQuantity"
											text=" Received Quantity" /><i class="text-red-1">*</i></th>
									<th class="text-center"><spring:message code="Remaining.QuantitytobeRecieved"
											text="Remaining Quantity to be Recieved" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.goodsNotesDto.goodsreceivedNotesItemList}" var="data" varStatus="loop">
									<tr id="firstItemRow" class="firstItemRow">
										<td class="text-center">${e + 1}</td>
										<td><form:input path="goodsNotesDto.goodsreceivedNotesItemList[${e}].itemDesc"
												id="itemDesc${e}" class="form-control" disabled="true" /></td>
										<td><form:input path="goodsNotesDto.goodsreceivedNotesItemList[${e}].uomDesc"
												id="uom${e}" class="form-control" disabled="true" /></td>
										<td><form:input path="goodsNotesDto.goodsreceivedNotesItemList[${e}].ManagementDesc"
												id="item${e}" class="form-control" disabled="true" /></td>
										<td><form:input path="goodsNotesDto.goodsreceivedNotesItemList[${e}].orederqty"
												id="orederqty${e}" class="form-control text-right hasNumber"
												disabled="true" /></td>
										<td><form:input path="goodsNotesDto.goodsreceivedNotesItemList[${e}].prevrecqt"
												id="prevrecqt${e}" class="form-control text-right hasNumber"
												disabled="true" /></td>
										<td><form:input path="goodsNotesDto.goodsreceivedNotesItemList[${e}].receivedqty"
												type="text" id="receivedqty${e}" class="form-control text-right"
												onkeypress="return hasAmount(event, this, 10, 2)" oninput="remainingqtytorcv(${e})" 
												disabled="${command.saveMode eq 'V'}" /></td>
										<td><form:input type="text" path="goodsNotesDto.goodsreceivedNotesItemList[${e}].remainingQty"
												readonly="true" id="remainingQty${e}" class="form-control text-right" /></td>
									</tr>
									<c:set var="e" value="${e + 1}" scope="page" />
								</c:forEach>
							</tbody>
						</table>
					</div>
					<br>

					<div class="form-group">
						<apptags:input labelCode="remark.goods" path="goodsNotesDto.remarks" maxlegnth="200"
							cssClass="form-control required-control" isMandatory="true" isDisabled="${command.saveMode eq 'V'}" />
					
						<c:if test="${command.saveMode ne 'V'}">
							<label class="col-sm-2 control-label required-control" id="careDocLbl"
									for="UploadDocument"><spring:message code="material.management.Upload.Delivery.challan.or.PO"
									text="Upload Delivery Challan Or PO." /></label>
							<div class="col-sm-4">
								<apptags:formField fieldType="7" fieldPath="" currentCount="0"
									showFileNameHTMLId="true" folderName="0" fileSize="WORK_COMMON_MAX_SIZE" isMandatory="false"
									maxFileCount="QUESTION_FILE_COUNT" validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS_PNG">
								</apptags:formField>
								<small class="text-blue-2"> <spring:message text="(Upload File upto 5MB and Only pdf,doc,docx,jpeg,jpg,png,gif,bmp extension(s) file(s) are allowed.)"
										code="NOCBuildingPermission.uploadformat" /></small>
							</div>
						</c:if>
					</div>

					<div class=" form-group ">
						<div class=" col-sm-12 text-center ">
							<c:if test="${! command.attachDocsList.isEmpty()}">
								<div class="table-responsive">
									<table class="table table-bordered margin-bottom-10"
										id="deleteDoc">
										<tr>
											<th class="text-center" width="15%"><spring:message
													code="population.master.srno" text="Sr. No." /></th>
											<th><spring:message code="scheme.view.document" text="" /></th>
										</tr>
										<c:set var="e" value="0" scope="page" />
										<c:forEach items="${command.attachDocsList}" var="lookUp">
											<tr>
												<td class="text-center">${e+1}</td>
												<td class="text-center"><apptags:filedownload filename="${lookUp.attFname}" 
														filePath="${lookUp.attPath}" actionUrl="GoodsReceivedNotesItem.html?Download" /></td>
											</tr>
											<c:set var="e" value="${e + 1}" scope="page" />
										</c:forEach>
									</table>
								</div>
							</c:if>
						</div>
					</div>

					<div class="text-center padding-bottom-10">
						<c:if test="${command.saveMode ne 'V'}">
							<button type="button" class="btn btn-success btn-submit" onclick="saveGoodsReceivedNote(this);">
								<spring:message code="material.management.submit" text="Submit"></spring:message>
							</button>
							<button type="button" class="btn btn-warning" onclick="openForm('GoodsReceivedNotesItem.html','add')">
								<spring:message code="material.management.reset" text="Reset"></spring:message>
							</button>
						</c:if>
						<apptags:backButton url="GoodsReceivedNotesItem.html"></apptags:backButton>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>