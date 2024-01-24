<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<ul class="nav nav-tabs margin-bottom-10" id="myTab">

	<c:choose>
		<c:when test="${command.mbMasDto.workMbNo eq null }">
			<li id="tab1"><a href="#menu1" onclick=""><spring:message
						code="wms.Header" /></a></li>
			<li id="tab2"><a href="#menu2" onclick=''><spring:message
						code="wms.MBDetails" /></a></li>
			<li id="tab3"><a href="#menu3" onclick=''><spring:message
						code="wms.MBNonSORItemsDetails" /></a></li>
			<!-- <li id="tab4"><a  href="#menu4" onclick=''>MB
					Tax Details</a></li> -->

			<li id="tab4"><a href="#menu4" onclick=""><spring:message
						code="work.estimate.overheads" text="Overheads" /></a></li>
						
			<li id="tab5"><a href="#menu5" onclick=""><spring:message
						code="wms.CheckList" text="CheckList" /></a></li>
												
			<li id="tab6"><a href="#menu6" onclick=""><spring:message
						code="work.estimate.enclosures" /></a></li>
		</c:when>
		<c:otherwise>
			<li id="tab1"><a data-toggle="tab" href="#menu1"
				onclick="openMbMasForm();"><spring:message code="wms.Header" /></a></li>
			<li id="tab2"><a data-toggle="tab" href="#menu2"
				onclick='measurementSheet();'><spring:message
						code="wms.MBDetails" /></a></li>
			<li id="tab3"><a data-toggle="tab" href="#menu3"
				onclick='openMbNonSorForm();'><spring:message
						code="wms.MBNonSORItemsDetails" /></a></li>
			<!-- <li id="tab4"><a data-toggle="tab" href="#menu4"
				onclick='openMbTaxDetailsForm()'>MB Tax Details</a></li> -->


			
			<li id="tab4"><a data-toggle="tab" href="#menu4"
				onclick="openAddWorkOverheadsForm();"><spring:message
						code="work.estimate.overheads" text="Overheads" /> </a></li>
						
			<li id="tab5"><a data-toggle="tab" href="#menu5"
				onclick="openCheckList();"><spring:message code="wms.CheckList"
						text="CheckList" /></a></li>
			

			<li id="tab6"><a data-toggle="tab" href="#menu6"
				onclick="openAddMbEnclosuersForm();"><spring:message
						code="work.estimate.enclosures" /></a></li>
		</c:otherwise>
	</c:choose>
</ul>
