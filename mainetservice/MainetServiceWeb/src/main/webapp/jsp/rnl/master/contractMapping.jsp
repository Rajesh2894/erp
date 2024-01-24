<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
<!-- <script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script> -->
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script src="js/rnl/master/mappingForm.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>


 <!-- <div class="content animated slideInDown"> -->
 <div class="content animated">
      <div class="widget">
        <div class="widget-header">
          <h2><spring:message code="rnl.estate.cont.map" text="Estate Contract Mapping"></spring:message></h2>
          <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div>
        </div>
        <div class="widget-content padding">
          <div class="mand-label clearfix">
			<span><spring:message code="rnl.book.field" text="Field with"></spring:message> <i class="text-red-1">*</i><spring:message code="master.estate.field.mandatory.message" text="is mandatory"></spring:message> 
			</span>
		</div>
		 <div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
				<ul>
					<li><label id="errorId"></label></li>
				</ul>
		 </div>
           <form:form method="post" action="EstateContractMapping.html" class="form-horizontal" name="mappingForm" id="mappingForm" >
            <jsp:include page="/jsp/tiles/validationerror.jsp" />
            <div class="table-responsive margin-bottom-10">
               <table class="table table-striped table-bordered" id="datatables1">
                <thead>
                  <tr>
                    <td><spring:message code="rnl.master.contract.no"        text="Contract No."></spring:message></td>
                    <td><spring:message code="rnl.master.contract.date"      text="Contract Date"></spring:message></td>
                  	<td><spring:message code="master.complaint.department"   text="Department"></spring:message></td>
                  	<td><spring:message code="rnl.master.represented.by"     text="Represented By"></spring:message></td>
                    <td><spring:message code="rnl.master.vender.name"        text="Vendor Name"></spring:message></td>
                    <td><spring:message code="rnl.master.contract.from.date" text="Contract From Date"></spring:message></td>
                    <td><spring:message code="rnl.master.contract.to.date"   text="Contract To Date"></spring:message></td>
                    <td><spring:message code="rnl.contract.selection"        text="Contract Selection"></spring:message></td>
                    <td width="120"><spring:message code="rnl.view.contract" text="View Contract"></spring:message></td>
                  </tr>
                </thead>
                <tbody id="propertyListId">
                   <c:forEach items="${command.contractList}" var="data" varStatus="count">
                     <tr>  
                           <td>${data.contractNo}</td>
                           <td>${data.contDate}</td>
                           <td>${data.deptName}</td>
                           <td>${data.representedBy}</td>
                           <td>${data.vendorName}</td>
                           <td>${data.fromDate}</td>
                           <td>${data.toDate}</td>
                           <td><form:radiobutton cssClass="margin-left-30" path="estateContMappingDTO.contractId" value="${data.contId}"></form:radiobutton></td>
                           <td class="text-center"><a href="javascript:void(0);" class="btn btn-blue-2 btn-sm" onClick="showContract(${data.contId},'V')"><strong class="fa fa-eye"></strong><span class="hide">View</span></a></td>
                      </tr>
                   </c:forEach>
                  
                </tbody>
              </table>
            </div>
            <div class="panel-group accordion-toggle" id="accordion_single_collapse">
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"> <a data-toggle="collapse" class="" data-parent="#accordion_single_collapse" href="#estate"><spring:message code="rnl.master.prop.map" text="Property Mapping"></spring:message></a> </h4>
                </div>
                <div id="estate" class="panel-collapse collapse in">
                  <div class="panel-body">
                    <div class="form-group">
                     
                      <label class="control-label col-sm-2 required-control" for="estateId"><spring:message code='estate.label.name'/></label>
                       <div class="col-sm-4">
                           <form:select path="estateContMappingDTO.esId" class="chosen-select-no-results form-control" id="estateId">
			                   <form:option value="0"><spring:message code="rnl.master.select" text="select"/></form:option>
			                   <c:forEach items="${command.estateMasters}" var="objArray">
                                  <form:option value="${objArray[0]}"><c:choose><c:when test="${userSession.languageId eq 2}">${objArray[3]}</c:when><c:otherwise>${objArray[2]}</c:otherwise></c:choose></form:option>
                               </c:forEach> 
                             </form:select> 
                       </div>
                    </div>
                    <div class="margin-top-10" id="propTableId">
                        <jsp:include page="contractMapProp.jsp" ></jsp:include>
                    </div>
                     <form:hidden path="dupCheckFlag"/>
                  </div>
                </div>
              </div>
            </div>
            <div class="text-center padding-top-10">
                      <div class="padding-top-10 text-center">
							<button type="button" class="btn btn-success btn-submit"
								 id="mappingsubmit">
								<spring:message code="bt.save" />
							</button>
							<button type="Reset" class="btn btn-warning" id="resetEstate" ><spring:message code="bt.clear"/></button>
							<input type="button" id="backBtn" class="btn btn-danger" onclick="back()" 
							   value="<spring:message code="bt.backBtn"/>" />
						</div>	
              
            </div>
          </form:form>
        </div>
      </div>
      <!-- End of info box --> 
      
      <!--Wedgets Start Here-->
      
      <!--Wedgets End Here--> 
 </div>