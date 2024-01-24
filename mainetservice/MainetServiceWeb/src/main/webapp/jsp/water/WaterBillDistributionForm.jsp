<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 <script type="text/javascript" src="js/mainet/validation.js"></script>
 <!-- 
<script 
src="https://cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js"></script> -->

 <script type="text/javascript">
	$(document).ready(function(e) {
		$('.lessthancurrdate').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '-0d',
			yearRange : "-100:-0"
		});
		
		/*  $("#distrutionTable").dataTable(); */

		if ($("#dataSize").val() > 0) {
			var radioValue = $("input[name='billCcnType']:checked").val();
			if (radioValue === 'S') {
				$('#selectall').prop('checked', true);
				$('.checkall').each(function() {
					this.checked = true;
				});
			}
		}

		var radioValue = $("input[name='billCcnType']:checked").val();
		if (radioValue === 'S') {
			$('#connNumber').show();
			$('#multiSearch').hide();
		} else {
			$('#connNumber').hide();
			$('#ccnNumber').val('');
			$('#multiSearch').show();
		}

		$(".conSelect").click(function() {
			var radioValueSel = $("input[name='billCcnType']:checked").val();
			if (radioValueSel === 'S') {
				$('#connNumber').show();
				$('#multiSearch').hide();
			} else {
				$('#connNumber').hide();
				$('#ccnNumber').val('');
				$('#multiSearch').show();
			}
		});

		$('#selectall').click(function(event) {
			if (this.checked) {
				$('.checkall').each(function() {
					this.checked = true;
				});
			} else {
				$('.checkall').each(function() {
					this.checked = false;

				});
			}
		});

		$('.checkall').on('click', function() {
			if ($('.checkall:checked').length == $('.checkall').length) {
				$('#selectall').prop('checked', true);
			} else {
				$('#selectall').prop('checked', false);
			}

		});

	});

	function serachBillDistributionData(obj) {
		var formName = findClosestElementId(obj, 'form');

		var theForm = '#' + formName;

		var url = 'BillDistributionForm.html?serachBillDistributionData';

		$(theForm).attr('action', url);

		$(theForm).submit();
	}

	function saveData(element) {
		return saveOrUpdateForm(element,
				"Bill distribution  done successfully.",
				'BillDistributionForm.html', 'saveform');
	}
	function resetDistributionbill(element) {
		$("#billdistributionId").submit();
	}
</script>

 <apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here --> 
<iframe id="txtArea1" style="display:none"></iframe>
<div class="content"> 
  <div class="widget">
    <div class="widget-header">
      <h2>Bill/Notice Distribution</h2>
      <apptags:helpDoc url="BillDistributionForm.html"></apptags:helpDoc> 
    </div>
    <div class="widget-content padding">
      <form:form action="BillDistributionForm.html" method="post" class="form-horizontal" name="billdistribution" id="billdistributionId">
       		<jsp:include page="/jsp/tiles/validationerror.jsp" />
       		
       		<c:if test="${command.validatePrefix eq true}">
       		<div class="form-group">
					<label class="control-label col-sm-2"><spring:message code="water.billDistributionForm.Distribution"/><span
						class="mand">*</span></label>
					<div class="col-sm-4">
						<label class="radio-inline"><form:radiobutton
								path="billCcnType" value="S" id="single" class="conSelect" /><spring:message code="water.meterReading.Single"/></label>
						<label class="radio-inline"><form:radiobutton
								path="billCcnType" value="M" id="All" class="conSelect" />
							Multiple</label>
					</div>

					<div id='connNumber'>
						<label class="control-label col-sm-2 required-control"><spring:message code="water.ConnectionNo"/></label>
						<div class="col-sm-4">
							<form:input path="connectionNo" type="text"
								class="form-control" id="ccnNumber"></form:input>
						</div>
					</div>
				</div>
					<div class="form-group">
									
									<label class="col-sm-2 control-label"><spring:message code="water.billDistributionForm.TypeofDistribution"/>Type of Distribution</label>
									<div>
										<c:set var="baseLookupCode" value="TOD" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="distriutionType"
											cssClass="form-control required-control"
											hasChildLookup="false" hasId="true" showAll="false"
											selectOptionLabelCode="eip.select" isMandatory="true" />
									</div>

					</div>
       		
       		
       		<div id='multiSearch'>
       		
				<div class="panel-group accordion-toggle margin-bottom-0"
					id="accordion_single_collapse">

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#multiple">Multiple</a>
							</h4>
						</div>
						<div id="multiple" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group  margin-bottom-0">
									<apptags:lookupFieldSet
										cssClass="form-control required-control" baseLookupCode="WWZ"
										hasId="true" pathPrefix="entity.areaDivision" isMandatory="true"
										hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true" showAll="true" />
								</div>
							</div>
						</div>
					</div>

				</div>
       		</div>
       		  <form:hidden path="" value="${fn:length(command.billMas)}" id="dataSize"/>
       		
       		<div class="text-center">
				  <button type="submit" class="btn btn-info" onclick="return serachBillDistributionData(this);"><i class="fa fa-search"></i> Search</button>
					<button type="button" class="btn btn-warning"
						onclick="resetDistributionbill(this)">Reset</button>
				</div>
				
				<c:if test="${not empty command.billMas}"> 
            <h4>Connection Details</h4>
             <div class="table-responsive max-height-300">
              <table class="table table-bordered table-condensed table-striped" id="distrutionTable">
                  <thead>
              
	              	<tr>
		               <th width="120"><label class="checkbox-inline"><input type="checkbox" id="selectall"/><spring:message code="water.selectall" text="Select All"/></label></th>
					   <th width="100"><spring:message code="water.nodues.srno"/></th>
					   <th><spring:message code="water.ConnectionNo"/></th>
					   <th><spring:message code="water.consumerName"/></th>
					   <th><spring:message code="water.billDistributionForm.BillNo"/></th>
					   <th><spring:message code="water.billDistributionForm.BillGeneratedDate"/></th>
					    <th><spring:message code="water.billDistributionForm.DistributionDate"/></th>
	              	</tr>
	              	</thead>
	              <c:forEach items="${command.billMas}" var="data"  varStatus="status"> 
	              <tbody>
	              	<tr>
		              <td><label class="checkbox margin-left-20"><form:checkbox path="billMas[${status.index}].bmEntryFlag" value="Y" cssClass="checkall" id="${data.bmIdno}"/></label></td>
		              <td><c:out value="${status.index+1}"></c:out></td>
		              <td><c:out value="${data.wtV1}"></c:out></td>
		              <td><c:out value="${data.bmCcnOwner}"></c:out></td>
		              <td><c:out value="${data.bmNo}"></c:out></td>
		                <td><c:out value="${data.bmBilldt}"></c:out></td>
		  <td><form:input path="billMas[${status.index}].distDate" type="text" id="fromDate${status.index}" class="lessthancurrdate form-control "  ></form:input> </td>
	              	</tr>
	              	</tbody>
             	 </c:forEach>
         		</table>
         	</div>
            <div class="text-center margin-top-10">
             <button type="button" class="btn btn-success btn-submit" onclick="return saveData(this);">Save</button>
             <button type="button" class="btn btn-danger" onclick="window.location.href='AdminHome.html'">Cancel</button>
            </div>
            </c:if>
            
            </c:if>
            <c:if test="${command.validatePrefix ne true}">
             <span><b>Distribution entry is not allowed.</b></span>
            </c:if>
       	</form:form>
       	</div>
       	</div>
       	</div>
       
       		