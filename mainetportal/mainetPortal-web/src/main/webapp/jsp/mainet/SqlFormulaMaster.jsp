<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script
	src="js/mainet/SqlFormulaMaster.js"></script>


	<ul class="breadcrumbs">
		<li><spring:message code="HD.horhome" /></li>
		<li><spring:message code="HD.addandhoarding" /></li>
		<li><spring:message code="HD.hormaster" /></li>
		<li class="active"><spring:message code="adv.rep.sqlFormula" /></li>
	</ul>
	
	<apptags:helpDoc url="PropertyTaxPropertyDataUploading.html"/>	
	
<div class="form-div">
	

	<h1>
		<spring:message text="SQL Code Formula" code="adv.rep.sqlFormula" />
	</h1>

	<div id="content">
	
					<div class="mand-label"><spring:message code="MandatoryMsg" text="MandatoryMsg" /></div>

					
					<div class="error-div" style="display:none;"></div>	
 				<form:form action="SqlFormulaMaster.html"
						name="SqlFormulaMaster" method="post" class="form"
						id="SqlFormulaMaster">
						

				 
				<div class="regheader"><spring:message text="Formula Details" code="adv.sqlHeading" /></div> 
				 
				 
						<input type="button"
							value="<spring:message code="adv.rep.addFormula" text="Add Formula"/>"
							onClick="openForm('SqlFormulaMaster.html','add');" class="css_btn float-right">
							
						
								 <div class="form-elements">

							<div class="element" id="">
								<label for=""><spring:message code="UWMS.report.departmentname"
										text="Department Name" />:</label>
								<apptags:lookupField items="${command.departmentList}"
									path="entity.dpDeptid" showOnlyLabel="false" hasId="true"
									selectOptionLabelCode="aud.select.department"
									cssClass="mandClassColor" />
								<span class="mand">*</span>
							</div>

						
					<div class="element">
						<label for=""> <spring:message code="adv.rep.formulaCode"
								text=" Formula Code"></spring:message> :
						</label>
						<form:input path="entity.sfCode" id="sfCode1"
							cssClass="maxLength20  subsize " readonly="false"
							hasId="true" />
					</div>
	             </div>
	              <div class="form-elements padding_5">
					<div align="center" class="btn_fld">
						<input type="button"
							value="<spring:message code="adv.rep.search" text="Search"/>"
							onClick="searchForm(this);" class="css_btn"><input type="button"  id="resetForm"  class="css_btn"  onclick="clearErrorDiv();" value="<spring:message code="HD.Reset" />">
								
					</div>
                   </div>
                   
                   
	           
					
                 
					
						  
			       </form:form>
			       <div class="form-elements padding_10">
			       
			       <apptags:jQgrid id="SqlFormulaMaster"
					url="SqlFormulaMaster.html?SQL_Formula_Search" mtype="post"
					gridid="gridSqlFormulaMaster"
					colHeader="adv.rep.formulaCode,adv.rep.view"
					colModel="[	
								{name : 'sfCode',index : 'sfCode',editable : false,sortable : true,search : false,width :'500', align : 'center'},
								{name : 'viewMode',index : 'viewMode',width :'40',formatter  : viewModeTemplate}
								]"
					height="200"
					width="750"
					 caption=""
					  isChildGrid="false"
					hasActive="false" 
					hasViewDet="false" 
					hasEdit="true"
					hasDelete="false" 
					loadonce="false" 
					sortCol="rowId" 
					showrow="true" />
			       
			       
			       
                   </div>
                   				
			      
				</div>
			</div>
	

