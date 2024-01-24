<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib	prefix="apptags" 	tagdir="/WEB-INF/tags"%>
<script type="text/javascript">

$(document).ready(function(){
	
	if ($('#viewFlag').val() == "view") {	
		
		if($('#viewFlag').val() == "view"){
			$("#saveSignaDAta").hide();
		}
		else{
			$("#saveSignaDAta").show();
		}
		$('table.gridtable tr td textarea').addClass('input-disabled').attr('disabled','disabled');
		$('table.gridtable tr td select').addClass('input-disabled').attr('disabled','disabled');
		$('table.gridtable tr td:last-child').hide()
		$('table.gridtable tr th:last-child').hide()
		
		$('.form-elements .element textarea').addClass('input-disabled').attr('disabled','disabled');
		$('.form-elements .element input[type=text]').addClass('input-disabled').attr('disabled','disabled');
		
		
	}
	
	
});


</script>

<c:set var="editveiwflag" value="${tbSignPathDet.editFlag}"/>  

<div class="form-elements padding_top_10">	
				 
				 <form:hidden path="signdetId" id="signdetId"/>
				 
			    <div class="form-elements">	
				<div class="element">
				<label><spring:message code=""  text="Designation English"/> :</label>	
				<form:textarea path="desigEng" class="mandClassColor"></form:textarea>
				</div>
				<div class="element">
				<label><spring:message code=""  text="Designation Hindi"/> :</label>			
				<form:textarea path="desigReg"  class="mandClassColor" ></form:textarea>
				</div>	
			    </div>
			
			    <div class="form-elements">	
				<div class="element">
				<label><spring:message code=""  text="Secpath English"/> :</label>			
				<form:textarea path="secpathEng" class="mandClassColor" ></form:textarea>
				</div>	
				<div class="element">
				<label><spring:message code=""  text="Secpath Hindi"/> :</label>			
				<form:textarea path="secpathReg" class="mandClassColor" ></form:textarea>
				</div>	
				</div>
						
			    <div class="form-elements">					
				<div class="element">
				<label><spring:message code=""  text="Note English"/> :</label>			
				<form:textarea path="noteEng"  class="mandClassColor"></form:textarea>
				</div>	
				<div class="element">
				<label><spring:message code=""  text="Note Hindi"/> :</label>			
				<form:textarea path="noteReg"   class="mandClassColor"></form:textarea>
				</div>	
				</div>
				
				<div class="form-elements">	
				<div class="element">
				<label><spring:message code=""  text="From No English"/> :</label>			
				<form:input path="formnoEng"  class="mandClassColor" ></form:input>
				</div>
				<div class="element">
				<label><spring:message code=""  text="From No Hindi ${tbSignPathDet.addFlag eq 'add'}"/> :</label>			
				<form:input path="formnoReg"  class="mandClassColor" ></form:input>
				</div>
				</div>
				
				
				</div>
				
				<div class="clear"></div>
				
				<div class="btn_fld clear padding_top_10">
					<c:if test="${tbSignPathDet.addFlag eq 'add' || tbSignPathDet.editFlag eq 'edit'}">
						<input name="" type="button" value="Save" class="css_btn" id="saveSignaDAta"  onclick="add(this);"/>
						<apptags:resetButton/>
					</c:if> 
				</div>
					
				
				
				
				
				
				
			
			
		 