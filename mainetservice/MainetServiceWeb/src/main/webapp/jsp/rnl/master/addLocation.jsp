<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<apptags:breadcrumb></apptags:breadcrumb>>

    <!-- Start Content here -->
    <div class="content animated slideInDown"> 
      <!-- Start info box -->
      <div class="widget">
        <div class="widget-header">
          <h2><spring:message code="rnl.location.master" text="Location Master"/></h2>
          <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i><span class="hide"><spring:message code="rnl.book.help" text="Help"/></span></a> </div>
        </div>
        <div class="widget-content padding">
                    <div class="mand-label clearfix"><span><spring:message code="master.field.message"/><i class="text-red-1">*</i> <spring:message code="master.field.mandatory.message"/></span></div>
				          <div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
							<ul>
								<li><label id="errorId"></label></li>
								<form:errors path="*" cssClass="errorblock" element="div" />
							</ul>
				        </div>
	            <form:form method="post" action="Location.html" class="form-horizontal" modelAttribute="location" name="locationForm" id="locationForm" >
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
						<div class="form-group">
				              <label for="locNameReg" class="control-label col-sm-2"><spring:message code="rnl.master.loc.rname" text="Location Name (Reg)"/></label>
				              <div class="col-sm-4">
				                   <form:input  type="text" class="form-control" path="locNameReg"  ></form:input>
				              </div>
				              <label for="locNameEng" class="control-label col-sm-2"><spring:message code="rnl.master.loc.ename" text="Location Name (Eng)"/></label>
				              <div class="col-sm-4">
				                   <form:input path="locNameEng" type="text" class="form-control"  />
				                </div>
				             
                       </div>
                       <div class="form-group">
				              <label for="areaNameReg" class="control-label col-sm-2"><spring:message code="rnl.master.loc.rname" text="Location Name (Reg)"/></label>
				              <div class="col-sm-4">
				                   <form:input  type="text" class="form-control" path="areaNameReg"  ></form:input>
				              </div>
				              <label for="areaNameEng" class="control-label col-sm-2"><spring:message code="rnl.master.loc.ename" text="Location Name (Eng)"/></label>
				              <div class="col-sm-4">
				                   <form:input path="areaNameEng" type="text" class="form-control"  />
				                </div>
                       </div>
                        <div class="form-group">
				              <label for="gis" class="control-label col-sm-2"><spring:message code="rnl.master.gis" text="GIS"/></label>
				              <div class="col-sm-4">
				                   <form:input  type="text" class="form-control" path="gis"  ></form:input>
				              </div>
				        </div>
                        <div class="text-center padding-top-10">
                            <button type="button" class="btn btn-success btn-submit"
								    id="submitEstate"><spring:message code="bt.save"/></button>
                            <button type="Reset" class="btn btn-warning" id="resetEstate" ><spring:message code="bt.clear"/></button>
                            <button type="button" class="btn btn-blue-1" id="locMap" ><spring:message code="rnl.master.loc.map" text="Location Mapping"/></button>
						  </div> 
						  <div class="form-group"><a class="btn btn-link" role="button" data-toggle="collapse" href="#collapseExample" aria-expanded="false" aria-controls="collapseExample"> <spring:message code="rnl.master.imp.exp" text="Import / Export"/></a></div>
			           <div class="collapse" id="collapseExample">
			              <div class="well">
			                <div class="form-group">
			                  <label class="col-sm-2 control-label"><spring:message code="rnl.master.imp" text="Import File .csv/.xlsx"/></label>
			                  <div class="col-sm-5">
			                    <input name="" type="file" class="form-control">
			                    <p class="text-red"><spring:message code="rnl.master.note" text="Note: Previous records will be disabled and new records will be update"/></p>
			                  </div>
			                  <div class="col-sm-5">
			                    <button class="btn btn-green-3"> <spring:message code="rnl.master.import" text="Import"/> </button>
			                  </div>
			                </div>
			                <div class="form-group">
			                  <label class="col-sm-2 control-label"><spring:message code="rnl.master.exp.csv" text="Export File .csv/.xlsx"/></label>
			                  <div class="col-sm-4">
			                    <button class="btn btn-blue-3"><spring:message code="rnl.master.export" text="Export"/></button>
			                  </div>
			                 </div>
			                </div>
			            </div>   
						  
			    </form:form>			
           </div>
          </div>
       </div> 