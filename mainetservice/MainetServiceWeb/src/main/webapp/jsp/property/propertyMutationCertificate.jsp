<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/property/publicNotice.js" ></script>   
<!-- End JSP Necessary Tags -->

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div id="receipt">
	<div class="widget">
	<div class="widget-header">
				<h2><b><spring:message text="Mutation Certificate"/></b></h2>				
	</div>
		
<!-- Start Widget Content -->
<div id="printPreview">
	<div class="widget-content padding">

		<!-- Start Form -->
		<form:form action="MutationAuthorization.html"
					class="form-horizontal form" name="MutationAuthorization"
					id="MutationAuthorization
					">	
			

                    <div class="col-xs-12 col-sm-12  text-center">
                        <h2 class="text-medium margin-bottom-0 margin-top-0 text-bold">
							${userSession.getCurrent().organisation.ONlsOrgname}
						</h2>
                        <div class="clearfix padding-10"></div>
                        
                        <h2 class="text-large margin-bottom-0 margin-top-0 text-bold">
							${command.publicNoticeDto.deptName}
						
						</h2>

                    </div>
                    <div class="clearfix padding-5"></div>

                    <div class="col-xs-12 col-sm-12 text-right">
                        <p>
                            <spring:message code="property.report.Date"/>${command.publicNoticeDto.noticeDate}
                        </p>
                    </div>
                    <hr>
                    <div class="col-xs-12 col-sm-12  text-center">
                        <h2 class="text-large margin-bottom-0 margin-top-0 text-bold">
							<spring:message text="Mutation Certificate" code=""/>
						</h2>
                    </div>

                    <!-- <div class="clearfix padding-5"></div> -->

                    <%-- <div class="col-xs-12 col-sm-12 text-right">
                        <p>
                           <spring:message code="property.report.NoticeNo"/>${command.publicNoticeDto.noticeNo} 
                            <br> <spring:message code="property.report.NoticeDate"/>${command.publicNoticeDto.noticeDate} 
                        </p>
                    </div> --%>
                   <!--  <div class="clearfix padding-25"></div> -->

                    <div class="col-xs-11">
                        <p class="margin-top-15"><span class="text-bold"> <spring:message code="property.report.Subject"/>
                        </span><spring:message text="Application for mutation certificate:"/></p>
                    </div>

                    <div class="col-sm-11">
                        <p class="margin-top-15">
                            <span class="text-bold"><spring:message code="property.report.propNo"/></span>${command.publicNoticeDto.propNo}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <span class="text-bold"><spring:message code="property.report.oldPropNo"/></span>${command.publicNoticeDto.oldProNo}
                            <span class="text-bold"><spring:message text="House No. "/></span>${command.publicNoticeDto.houseNo}
                            <br>
                            <span class="text-bold">${command.publicNoticeDto.ward1L}</span>&nbsp;:&nbsp;${command.publicNoticeDto.ward1V}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            
                            <c:if test="${command.publicNoticeDto.ward2L ne null }">     
                            <span class="text-bold">${command.publicNoticeDto.ward2L}</span>&nbsp;:&nbsp;${command.publicNoticeDto.ward2V}
                            </c:if>
                            <br>
                            <c:if test="${command.publicNoticeDto.ward3L ne null }">     
                            <span class="text-bold">${command.publicNoticeDto.ward3L}</span>&nbsp;:&nbsp;${command.publicNoticeDto.ward3V} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                            </c:if>
                            
                            <c:if test="${command.publicNoticeDto.ward4L ne null }">      
                            <span class="text-bold">${command.publicNoticeDto.ward4L}</span>&nbsp;:&nbsp;${command.publicNoticeDto.ward4V}
                        	</c:if>
                        </p>
                    </div>

                    <div class="col-xs-11">
                        <p class="margin-top-15"><span class="text-bold"> <spring:message text="Mr/Mrs"/></span></p>
                        <%-- <p class="margin-top-15"><span class="text-bold"><spring:message code="property.report.Reference"/></span><spring:message code="property.report.content7"/>${command.publicNoticeDto.applicationNo}&nbsp;<spring:message code="property.report.content8"/>${command.publicNoticeDto.appDate}</p> --%>
                    </div>
                    <div class="col-xs-12">
                        <p class="margin-top-15 text-justify"> <spring:message text="Regarding your application for mutation certificate, Application no. "/>
                        <span class="text-bold">&nbsp;${command.publicNoticeDto.applicationNo}</span> 
                        <spring:message code="property.report.Date"/>${command.publicNoticeDto.noticeDate}
                        <spring:message text=" by submitted required documents The name of"/>&nbsp;<span class="text-bold">${command.publicNoticeDto.previousOwner}</span></p> 
                       <p> <spring:message text="has been changed by changing the name of "/>
                        <span class="text-bold">${command.displayOwnerNames}</span>&nbsp;<spring:message text="is set."/></p>
                         
                    </div>
                    <div class="padding-5 clear">&nbsp;</div>					
					<div class = "form-group clearfix padding-vertical-15">
							<div class="col-xs-8 text-center"></div>
							<div class="col-xs-4 text-center">
				              <p class="margin-top-15">Authorised signature</p>
				              <p>	${userSession.getCurrent().organisation.ONlsOrgname}</p>
				            </div>
					</div>
                    <div class="padding-5 clear">&nbsp;</div>
					
                
	                <div class="text-center hidden-print padding-10">
	                    
	                    <button onClick="printdiv('printPreview');"
								class="btn btn-primary hidden-print">
								<i class="fa fa-print"></i><spring:message code="property.report.Print"/>
						</button>
	                    <!-- <button type="button" class="btn btn-danger" onclick="back()">
	                        Cancel
	                    </button> -->
	                </div>

            </form:form>
            </div>
			</div><!-- End Widget Content -->		
			</div>	
		</div>
</div><!-- End Content here -->

