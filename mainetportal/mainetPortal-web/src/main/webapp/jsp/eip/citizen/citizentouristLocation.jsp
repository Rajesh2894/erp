<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<style>
h3 {
	margin: 8px;
}
.section {
	font-size: 11px;
	margin: 4px 4px 6px 4px;	
	border-bottom: 1px solid #DFE4E7;
	overflow: hidden;		
}

.section div{
	margin: 2px;
	padding: 2px;	
	overflow: hidden;		
}

.section  .text {
	font-size: 12px;
	font-weight: bold;
	text-align: justify;
	width: 600px;	
}

.section  .profile_img {
	width: 120px !important;
	float: right;			
}

.section .profile_img > img{
	height: 100px !important;
	width: 100px !important;
	float: right !important;
	margin: 5px;	
}

.section .document{
	font-size: 12px;	
	clear: both;
}

.section .description{
	text-align: justify;
	width: 600px;	
	min-height : 50px; 
	overflow: hidden;
	float: left;
	clear: both;
}

.section > .move_up {
	margin-top: -30px;
}

</style>



<script>
	function fn_getTourSTLoc(obj) {
		var catId = $("#" + obj.id + " option:selected").val();
		var postdata = 'categoryId='+catId;
		var resulthtml = __doAjaxRequest('TouristLocation.html?searchTourSTLocInfo','POST',postdata,false,'html');
		$('#touristLocDiv').empty();
		$('#touristLocDiv').html(resulthtml);

	}
</script>

<div id="heading_wrapper">

	<div id="heading_bredcrum">
		<ul>
			<li><a href="CitizenHome.html"><spring:message code="menu.home"/></a></li>
			<li>&gt;</li>
			<li><a href="javascript:void(0);"><spring:message code="menu.eip"/></a></li>
			<li>&gt;</li>
			<li><a href="javascript:void(0);"><spring:message code="eip.cityinfo"/>City Information</a></li>
			<li>&gt;</li>
			<li>Tourist Location </li>
		</ul>
	</div>

</div>
<div class="clearfix" id="home_content">
	<div class="col-xs-12">
		<div class="row">
			<div class="form-div">
				
				<h3>Tourist Locations</h3>
			
				<form:form action="TouristLocation.html" name="frmTrtLoc" id="frmTrtLoc">

					<div class="form-elements" align="center">
						<div class="element">
							<label for="deptId_dpDeptid">Select Category</label>
							<apptags:selectField isLookUpItem="true" changeHandler="fn_getTourSTLoc(this);" items="${command.allCategories}" hasId="true" selectOptionLabelCode="Select Category" fieldPath="category.catId" />
						</div>
					</div>
					
					<div id="touristLocDiv">
						<c:forEach var="lookUp" items="${command.touristLocationInfo}">
							<div class="section">
								<div class="text">${lookUp.title}</div>
								<div class="text">${lookUp.address}</div>
								<div class="description">${lookUp.lookUpDesc}</div>
								<c:if test="${not empty lookUp.locationImage}">
								<div class="profile_img"><img src="${lookUp.locationImage}" alt="${lookUp.locationImage}" title="${lookUp.locationImage}" /> </div>
								</c:if>
							</div>
						</c:forEach>
					</div>
				</form:form>

			</div>
		</div>
	</div>
</div>
