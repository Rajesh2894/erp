<%@ taglib tagdir="/WEB-INF/tags" prefix="apptags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<script src="assets/libs/ckeditor/ckeditor.js"></script>
<script src="assets/libs/ckeditor/adapters/jquery.js"></script>
<script src="js/mainet/validation.js"></script>
<script>
$(".fileUploadClass").change(function(){

	var	file=event.target.files;
	var fileName=file[0].name;
	
	var fileNamePattern = new RegExp(/^[a-z A-Z 0-9#_.-]+$/);
	if (!fileNamePattern.test( fileName ) ) {
		
	   var msg = getLocalMessage("Common.file.validation.file.pattern.text");
	   var okMsg = getLocalMessage('bt.ok');
	   message='<h4 class="text-center padding-5 margin-top-0 text-info" style="line-height: 20px;">'+msg+'</h4>';
	   message +='<div class="text-center padding-top-10"><input type=\'button\' value=\''+okMsg+'\' class=\'btn btn-blue-2\' onclick="$.fancybox.close()" /></div>';
	   $('.error-dialog').html(message);
	   showModalBoxWithoutClose('.error-dialog');
		   $(this).val(null);
		    return false;
	}
	});
</script>

<script>
	$(document).ready(function() {
		
		
		var pathname  = document.URL ;
		var txt ="AdminFaqCheker";
		var txt2 =$("#isCheckerFlag").val();
		if(pathname.indexOf(txt) > -1 || txt2=="Y") {
			//$("#AdminFaqCheker").show()
			$("#chekkerflag1").val("Y");
			$(".AdminFaqbutton").hide();
			$(".AdminFaqChekerbutton").show();
			$("#AdminFaqChekerback").show();
			$("#AdminFaqback").hide();
			$("#Rejction").hide();
			$("#AdminAdd").hide();
			
			$(".radiobutton").click(function() {
				 if( $(this).is(":checked") ){
			            var val = $(this).val(); 
			            if( val =='Y'){
			            	  $("#Rejction").hide();
			            	  $("#Rejction").val('');
			            	}else
			            	{
			            	 $("#Rejction").show();
			            	}
			        };
		    });
			
			/* if($("input:radio:checked").val()=='Y'){
				$("#AdminFaqCheker").show();
	         }
			if($("input:radio:checked").val()=='N'){
				$("#AdminFaqCheker").show();
				} */
			}else 
				{
				//$("#AdminFaqCheker").hide()
				$("#chekkerflag1").val("N");
				$(".AdminFaqbutton").show();
				$(".AdminFaqChekerbutton").hide();
				$("#AdminFaqChekerback").hide();
				$("#AdminFaqback").show();
				$("#AdminAdd").show();
				 $(".radiobutton").prop('disabled',true);
				 $(".radiobutton").removeClass('mandClassColor');
				 $(".radiobutton").addClass('disablefield');
				/* if($("input:radio:checked").val()=='Y'){
					$("#AdminFaqCheker").show();
					 $(".radiobutton").prop('disabled',true);
					 $(".radiobutton").removeClass('mandClassColor');
					 $(".radiobutton").addClass('disablefield');
		         }
				
				if($("input:radio:checked").val()=='N'){
					$("#AdminFaqCheker").show();
					 $(".radiobutton").prop('disabled',true);
					 $(".radiobutton").removeClass('mandClassColor');
					 $(".radiobutton").addClass('disablefield');
					} */
			}
	
		$('#sectionDataTable').dataTable({  

			"paging":true,
			"ordering": false,
			"bDestroy": true,			
			"scrollX": true,
			"lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]]
			});

		
		  $(".datepicker").datepicker({		     	
		        dateFormat: 'dd/mm/yy',
		        changeMonth: true,
		        changeYear: true
				
	 		}); 

		   $('.datetimepicker').datetimepicker({
				dateFormat: 'dd/mm/yy',
				timeFormat: "hh:mm tt",
				changeMonth: true,
				changeYear: true
			}); 
		  
		  /* $('.datetimepicker').datetimepicker({
				dateFormat: 'dd/mm/yy',
				timeFormat: "hh:mm tt",
				changeMonth: true,
				changeYear: true,
				hour : '16',
				minute :'00'
			}); */
		  
		$('#String1').ckeditor({skin : 'bootstrapck'});

		
});

	function saveOrUpdateSection(obj, message, successUrl, actionParam)
	{
		var errorList = [];
		var pathname  = document.URL ;
		var txt ="AdminFaqCheker";
		var txt2 =$("#isCheckerFlag").val();
		if( txt2=="Y") {
			 if ($('.remark').val()==null || $('.remark').val()==""){
				 errorList.push(getLocalMessage('eip.admin.validatn.cheker'));   
				}
			} 
		var appOrRejFlag =  $("input[name='entity.chekkerflag']:checked").val()
		var mode = $('#mode').val()
		 var successMessage ='';
		if(mode == 'edit'){
			if(appOrRejFlag!= undefined && appOrRejFlag == 'Y' && document.URL.includes('AdminFaqCheker')){
				  successMessage = getLocalMessage('admin.approve.successmsg');
			}else if(appOrRejFlag!= undefined && appOrRejFlag == 'N' &&  document.URL.includes('AdminFaqCheker')){
				  successMessage = getLocalMessage('admin.reject.successmsg');
			}else{
			      successMessage = getLocalMessage('admin.update.successmsg');
			}
		} else{
			 successMessage = getLocalMessage('admin.save.successmsg');
		}
	    
		if (!actionParam) {
			
			actionParam = "SaveDetail";
		}
		if (errorList.length == 0){
			return doFormActionForSave($('#getFormId'),successMessage, actionParam, true , successUrl);
		}else{
			return errorList;
			}
		}

	$(document).ready(function() {
		$('#String2').ckeditor({skin : 'bootstrapck'});
		$(".ena").each(function(){
			normalise($(this));
		});
	});
	
	function resetForm1(resetBtn) {
		
		cleareFile(resetBtn);
		if (resetBtn && resetBtn.form) {
			$('div[id*=profile_img_path]').html('');
			$('.alert').hide();
			$('div').removeClass("has-success has-error");
			$('#SectionDetails').find('input:text,textarea').val('');
		};
	}
	function resetForm(resetBtn) {
		resetFormOnAdd(SectionDetailsFrm);
	    var url	=	'SectionDetails.html'+'?cleareFile';
	    var response= __doAjaxRequest(url,'post',{},false);
		return false; 
	}
	
	function normalise(elem,order){	
		var that=$(elem),selc=$("option:selected",that).prop("tabindex");
		"retro"==order?that.hasClass("dd_01_rg")?$(".dd_01_en").prop("selectedIndex",selc):that.hasClass("dd_02_rg")?$(".dd_02_en").prop("selectedIndex",selc):that.hasClass("dd_03_rg")?$(".dd_03_en").prop("selectedIndex",selc):that.hasClass("dd_04_rg")?$(".dd_04_en").prop("selectedIndex",selc):that.hasClass("dd_05_rg")?$(".dd_05_en").prop("selectedIndex",selc):that.hasClass("dd_06_rg")?$(".dd_06_en").prop("selectedIndex",selc):that.hasClass("dd_07_rg")?$(".dd_07_en").prop("selectedIndex",selc):that.hasClass("dd_08_rg")?$(".dd_08_en").prop("selectedIndex",selc):that.hasClass("dd_09_rg")?$(".dd_09_en").prop("selectedIndex",selc):that.hasClass("dd_10_rg")?$(".dd_10_en").prop("selectedIndex",selc):that.hasClass("dd_11_rg")?$(".dd_11_en").prop("selectedIndex",selc):that.hasClass("dd_12_rg")&&$(".dd_12_en").prop("selectedIndex",selc):that.hasClass("dd_01_en")?$(".dd_01_rg").prop("selectedIndex",selc):that.hasClass("dd_02_en")?$(".dd_02_rg").prop("selectedIndex",selc):that.hasClass("dd_03_en")?$(".dd_03_rg").prop("selectedIndex",selc):that.hasClass("dd_04_en")?$(".dd_04_rg").prop("selectedIndex",selc):that.hasClass("dd_05_en")?$(".dd_05_rg").prop("selectedIndex",selc):that.hasClass("dd_06_en")?$(".dd_06_rg").prop("selectedIndex",selc):that.hasClass("dd_07_en")?$(".dd_07_rg").prop("selectedIndex",selc):that.hasClass("dd_08_en")?$(".dd_08_rg").prop("selectedIndex",selc):that.hasClass("dd_09_en")?$(".dd_09_rg").prop("selectedIndex",selc):that.hasClass("dd_10_en")?$(".dd_10_rg").prop("selectedIndex",selc):that.hasClass("dd_11_en")?$(".dd_11_rg").prop("selectedIndex",selc):that.hasClass("dd_12_en")&&$(".dd_12_rg").prop("selectedIndex",selc);
	}

	function addNewNewSection(formName,actionParam)
	  {

	   	var rowId=$("#secId" ).val();
	  	var rowIdStr='&rowId=';
	      
	  	openForm(formName,actionParam+rowIdStr+rowId);	
	   }
	function hideOrShowDiv(){
		$('#hideOrShowDiv').hide();
	}
</script> 
<script>


  $(document).ready(function() {  	
  		$(".form-elements .element label").filter(function () {
  	    if ($.trim($(this).text()) == "{mel :") {
  	    	$(this).next('span').children('input').removeClass('fontfix');
  	    }
	    if ($.trim($(this).text()) == "{-mel :") {
	   	$(this).next('span').children('input').removeClass('fontfix');
	   }
	if ($.trim($(this).text()) == "{melAayDI :") {
	  	$(this).next('span').children('input').removeClass('fontfix');
	  }
	if ($.trim($(this).text()) == "{meLa AayDI :") {
	  	$(this).next('span').children('input').removeClass('fontfix');
	  }
	if ($.trim($(this).text()) == "{-mel AayDI :") { 
	  	$(this).next('span').children('input').removeClass('fontfix');
	  }
  	    
  	   }); 
  		
  		
  		
  		
  		$("table.gridtable tr th").filter(function() {
  		  if ($.trim($(this).text()) == "{mel") {
  		    $(this).addClass('fontfix');
  		    $('table.gridtable tr td:nth-child(' + ($(this).index() + 1) + ')').children('a').addClass('arial')
  		  }

  		  if ($.trim($(this).text()) == "{-mel") {
  		    $(this).addClass('fontfix');
  		    $('table.gridtable tr td:nth-child(' + ($(this).index() + 1) + ')').children('a').addClass('arial')
  		  }

  		  if ($.trim($(this).text()) == "{melAayDI") {
  		    $(this).addClass('fontfix');
  		    $('table.gridtable tr td:nth-child(' + ($(this).index() + 1) + ')').children('a').addClass('arial')
  		  }

  		  if ($.trim($(this).text()) == "{meLa AayDI") {
  		    $(this).addClass('fontfix');
  		    $('table.gridtable tr td:nth-child(' + ($(this).index() + 1) + ')').children('a').addClass('arial')
  		  }

  		  if ($.trim($(this).text()) == "{-mel AayDI") {
  		    $(this).addClass('fontfix');
  		    $('table.gridtable tr td:nth-child(' + ($(this).index() + 1) + ')').children('a').addClass('arial')
  		  }

  		});

  			
  			
  			 
  			
		

  			
		
		
		
$('.decimal').keyup(function(){
    var val = $(this).val();
    if(isNaN(val)){
         val = val.replace(/[^0-9]./g,'');

         if(val.split('.').length>2) 
             val =val.replace(/\.+$/,"");

         $(this).val("0.0");
    }
    else
    {
    	$(this).val(val); 
    }
});
});



function deleteUploadFileFromEIP(obj,fileName,cnt,del,elementName)
{
		
var formName	=	findClosestElementId(obj,'form');

   var theForm	=	'#'+formName;
		
		var url	=	$(theForm).attr('action');
		
		url=url.split("?");

		var data = "fileName="+fileName+"&del="+del+"&elementName="+elementName;
		
		
		var mainUrl=url[0]+'?DeleteImage';
		
		__doAjaxRequest(mainUrl, 'post', data , false,'json');
           
		if(del=='image')
			{
			 $("#id_"+cnt).hide(); 
			
			}
		else if(del=='video')
			{
		   $("#videoId_"+cnt).hide();
			}
		else if(del=='file')
		{
	  	 $("#fileid_"+elementName+"_"+cnt).hide();
		} 
		   $("#fileupload").show();
		
}
$(document).ready(function() { 
	


	
	 $( "#String1" ).blur(function() {
		 
		
		 
	
    var post  =  $( "#String1").val();	
    var firstimg =  $(post ).find("img:first").attr("src");
    var second =  $(post ).find("img:eq(1)").attr("src");
    var third =  $(post ).find("img:eq(2)").attr("src");
    var fourth =  $(post ).find("img:eq(3)").attr("src");
    var five =  $(post ).find("img:eq(4)").attr("src");
    var res = post.replace($(post ).find("img:eq(0)").attr("src"),firstimg+"\\");
    post = res;
    var post = post.replace($(post ).find("img:eq(1)").attr("src"),second+"\\");
    var post = post.replace($(post ).find("img:eq(2)").attr("src"),third+"\\");
    var post = post.replace($(post ).find("img:eq(3)").attr("src"),fourth+"\\");
    var post = post.replace($(post ).find("img:eq(4)").attr("src"),five+"\\");
    $( "#String1" ).val(post);
  
    var res ;
    var count= 0 ; 
    $(post ).find("img").each(function () {
		  var curSrc = $(this).attr('src');
		  post =post.replace('src="','src=\\"');
		  $( "#String1" ).val(post);
		 
		});
   
	});

});


   


</script>



<apptags:breadcrumb></apptags:breadcrumb>


<div class="content animated">
      <div class="widget">
        <div class="widget-header">
	     <c:choose>
	     <c:when test="${command.makkerchekkerflag ne 'C'}">
   	     <h2><spring:message code="section.header.add" text="Add Section Details"/></h2>
	     </c:when>
	     <c:otherwise>
	     <h2><spring:message code="section.header.approve" text="Approve Section Details"/></h2>
	     </c:otherwise>
	     </c:choose>
        </div> 
     <div class="widget-content padding">   
<div class="clearfix" id="content">
<c:choose>
	<c:when test="${command.listMode}"> 
		<div class="text-center btn_fld padding-bottom-10">
			<input type="button" class="css_btn btn btn-info" id="AdminAdd" value="<spring:message code="portal.common.button.add" />" onclick="return openForm('SectionDetails.html','AddDetails');" />
			<%-- <input type="button" class="css_btn btn btn-danger" onclick="closebox('','SectionDetails.html')" value="<spring:message code="portal.common.button.back" />"> --%>
			<a href="SectionDetails.html" class="btn btn-danger" id="AdminFaqback"><spring:message code="portal.common.button.back" /></a>
			<a href="SectionDetails.html?AdminFaqCheker" class="btn btn-danger" id="AdminFaqChekerback"><spring:message code="portal.common.button.back" /></a>
		</div>
		<c:set var="colSpan" value="1"/>
		<c:set var="detailsList" value="${command.tableDetails}"/>
		<c:if test="${command.section.subLinkFieldMappings.size() gt 0}">
		<div class="table-responsive">
		
		<table class="table table-bordered table-striped dataTableNormal" id="sectionDataTable">
			<thead>
				<tr>
				<c:forEach items="${detailsList.get(0)}" var="lookUp">
				 <c:choose>
				 <c:when test="${fn:contains(lookUp.lookUpType, 'rg')}">
					<th class="fontfix">${lookUp.lookUpDesc}</th>
					<c:set var="colSpan" value="${colSpan+1}"/>
					</c:when>
					<c:otherwise>
					<th>${lookUp.lookUpDesc}</th>
					<c:set var="colSpan" value="${colSpan+1}"/>
					</c:otherwise>
					 </c:choose> 
				</c:forEach>
					<th><spring:message code="grid.edit" text="EDIT"/></th>
					<th><spring:message code="SectionDetail.delete" text="SectionDetail.delete" /></th>
				</tr>
			</thead>
			
			<tbody>
				<c:choose>
					<c:when test="${fn:length(detailsList.get(1)) > 0 }">
						<c:set var="temp" value="0"/>
						<tr>
						<c:forEach items="${detailsList.get(1)}" var="lookUp" varStatus="status">						
							<td><a href="javascript:void(0);" onclick="editDetails('${lookUp.lookUpId}')">${lookUp.lookUpDesc}</a></td>
							<c:set var="temp" value="${temp+1}"/>
							<c:if test="${temp == ((colSpan-1))}">
							<td>
								<a href="javascript:void(0);" onclick="editDetails('${lookUp.lookUpId}')" style="display:inline-block;">
									<img alt="Edit" src="css/images/edit.png" width="17"/>
								</a>
							</td>
							<td>
								<form action="SectionDetails.html" method="post" class="form" style="display:inline-block;"  id="frmSectionDetails${lookUp.lookUpId}" name="frmSectionDetails${lookUp.lookUpId}">
								<a 	href="javascript:void(0);" title="Delete"
									onclick="return deleteElementForLink('frmSectionDetails${lookUp.lookUpId}','DeleteDetail',${lookUp.lookUpId});">
									<img alt="Delete" src="css/images/delete.png" width="17"/>
								</a>
								<input type="hidden" name="rowId" id="rowId${lookUp.lookUpId}" value="${lookUp.lookUpId}"/>
								</form>
							</td>
								</tr>
								<c:set var="temp" value="0"/>
							</c:if>
					</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="${colSpan+1}"><spring:message code="SectionDetails.NoRow" text="SectionDetails.NoRow" /></td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
			
		</table>
		</div>
		</c:if>
	
	</c:when>
	
	<c:otherwise>
		<form:form action="SectionDetails.html" name="SectionDetails" id="SectionDetailsFrm" method="post" enctype="multipart/form-data" class="form-horizontal">
			
			<jsp:include page="/jsp/tiles/validationerror.jsp"/>
			<form:hidden path="subLinkMaster.checkerFlag1" id="chekkerflag1" />
			<input type ="hidden" id ="mode" value="${command.mode}"/>
			<form:hidden path="isChecker" id="isCheckerFlag"/>
			
			<c:forEach items="${command.section.subLinkFieldMappings}" var="subLinkFieldMap">
			<c:if test="${subLinkFieldMap.isDeleted eq 'N'}">
				<c:set var="ele" value="${fn:toLowerCase(subLinkFieldMap.filedNameMap)}"/>
				<c:set var="star" value="" />
					<c:set var="imgpath" value="${command.imgpath} " />
				<c:if test="${subLinkFieldMap.isMandatory eq 'Y'}">
					<c:set var="star" value="*" />
				</c:if>
				<c:choose>
					<c:when test="${subLinkFieldMap.fieldType==MainetConstants.DROP_DOWN_BOX or subLinkFieldMap.fieldType==MainetConstants.TEXT_FIELD  or subLinkFieldMap.fieldType==MainetConstants.TEXT_AREA or subLinkFieldMap.fieldType==MainetConstants.DATE_PICKER or subLinkFieldMap.fieldType==MainetConstants.TEXT_AREA_HTML  or subLinkFieldMap.fieldType==MainetConstants.LINK_FIELD}">
						 <c:if test="${subLinkFieldMap.fieldType==MainetConstants.TEXT_FIELD or subLinkFieldMap.fieldType==MainetConstants.LINK_FIELD}">
								<c:set var="maxlengthprop" value="150"></c:set>
						</c:if>
						
						<c:if test="${subLinkFieldMap.fieldType==MainetConstants.TEXT_AREA}">						
							<c:set var="maxlengthprop" value="2000"></c:set>						
						</c:if>
						<c:if test="${subLinkFieldMap.fieldType==MainetConstants.TEXT_AREA_HTML}">
					       <input type ="hidden" name="fieldType" value="${subLinkFieldMap.fieldType}">
						 <apptags:formField fieldType="${subLinkFieldMap.fieldType}" cssClass="mandClassColor empname subsize" fieldPath="img_path" labelCode="${subLinkFieldMap.fieldNameEn}"  showFileNameHTMLId="true"  randNum="${command.getUUIDNo()}" validnFunction="" maxFileCount="1" />
						 <div class="row padding-bottom-10">
							 <div class="col-sm-6">
								 <label for="String1"><spring:message code="section.add.english" text="Add Section in English"/></label>
							     <textarea id="String1" name="String1" required="required" >${String1}</textarea>
						     </div>
						     <div class="col-sm-6">
							     <label for="String2"><spring:message code="section.add.hindi" text="Add Section in Hindi"/></label>
							     <textarea id="String2" name="String2" required="required" >${String2}</textarea>
						     </div>
					     </div>
					     <div class="form-group">
						       
	   								<label class="col-sm-2 control-label"><spring:message code="section.add.uploadPath" text="Upload Path"/></label>
	   								<div class="col-sm-4">					     	
	  									 <input type="text" readonly="readonly" value="${command.uploadPath}" class="form-control">
	  								</div>
					     </div>
						</c:if>	
						
						<c:if test="${subLinkFieldMap.fieldType==MainetConstants.DROP_DOWN_BOX}">
							<!-- <div class="form-group"> -->
							 <div class="form-group">
								 <label class="col-sm-2 control-label"><spring:message code="" text="${subLinkFieldMap.fieldNameEn}"/>&nbsp;<span class="mand">*</span></label>
								 <div class="col-sm-4">
							     <c:if test="${not empty subLinkFieldMap.dropDownOptionEn}">
										<c:set var="options" value="${fn:split(subLinkFieldMap.dropDownOptionEn,',')}" />
										<c:if test="${subLinkFieldMap.filedNameMap=='DD_01'}">
											<form:select onchange="normalise(this)" path="entity.dd_01_en" cssClass="form-control dd_01_en ena" multiple="false">
													<form:option tabindex="0" value=""><spring:message code="drp.select_en" text="select"/></form:option>
												<c:forEach items="${options}" var="option" varStatus="count">
													<form:option tabindex="${count.index + 1}" value="${option}">${option}</form:option>
												</c:forEach>	
											</form:select>
										</c:if>
										<c:if test="${subLinkFieldMap.filedNameMap=='DD_02'}">
											<form:select onchange="normalise(this)" path="entity.dd_02_en" cssClass="form-control dd_02_en ena" multiple="false">
													<form:option tabindex="0" value=""><spring:message code="drp.select_en" text="select"/></form:option>
												<c:forEach items="${options}" var="option" varStatus="count">
													<form:option tabindex="${count.index + 1}" value="${option}">${option}</form:option>
												</c:forEach>	
											</form:select>
										</c:if>
										<c:if test="${subLinkFieldMap.filedNameMap=='DD_03'}">
											<form:select onchange="normalise(this)" path="entity.dd_03_en" cssClass="form-control dd_03_en ena" multiple="false">
													<form:option tabindex="0" value=""><spring:message code="drp.select_en" text="select"/></form:option>
												<c:forEach items="${options}" var="option" varStatus="count">
													<form:option tabindex="${count.index + 1}" value="${option}">${option}</form:option>
												</c:forEach>	
											</form:select>
								</c:if>
										<c:if test="${subLinkFieldMap.filedNameMap=='DD_04'}">
											<form:select onchange="normalise(this)" path="entity.dd_04_en" cssClass="form-control dd_04_en ena" multiple="false">
													<form:option tabindex="0" value=""><spring:message code="drp.select_en" text="select"/></form:option>
												<c:forEach items="${options}" var="option" varStatus="count">
													<form:option tabindex="${count.index + 1}" value="${option}">${option}</form:option>
												</c:forEach>	
											</form:select>
										</c:if>
										<c:if test="${subLinkFieldMap.filedNameMap=='DD_05'}">
											<form:select onchange="normalise(this)" path="entity.dd_05_en" cssClass="form-control dd_05_en ena" multiple="false">
													<form:option tabindex="0" value=""><spring:message code="drp.select_en" text="select"/></form:option>
												<c:forEach items="${options}" var="option" varStatus="count">
													<form:option tabindex="${count.index + 1}" value="${option}">${option}</form:option>
												</c:forEach>	
											</form:select>
										</c:if>
										<c:if test="${subLinkFieldMap.filedNameMap=='DD_06'}">
											<form:select onchange="normalise(this)" path="entity.dd_06_en" cssClass="form-control dd_06_en ena" multiple="false">
													<form:option tabindex="0" value=""><spring:message code="drp.select_en" text="select"/></form:option>
												<c:forEach items="${options}" var="option" varStatus="count">
													<form:option tabindex="${count.index + 1}" value="${option}">${option}</form:option>
												</c:forEach>	
											</form:select>
										</c:if>
										<c:if test="${subLinkFieldMap.filedNameMap=='DD_07'}">
											<form:select onchange="normalise(this)" path="entity.dd_07_en" cssClass="form-control dd_07_en ena" multiple="false">
													<form:option tabindex="0" value=""><spring:message code="drp.select_en" text="select"/></form:option>
												<c:forEach items="${options}" var="option" varStatus="count">
													<form:option tabindex="${count.index + 1}" value="${option}">${option}</form:option>
												</c:forEach>	
											</form:select>
										</c:if>
										<c:if test="${subLinkFieldMap.filedNameMap=='DD_08'}">
											<form:select onchange="normalise(this)" path="entity.dd_08_en" cssClass="form-control dd_08_en ena" multiple="false">
													<form:option tabindex="0" value=""><spring:message code="drp.select_en" text="select"/></form:option>
												<c:forEach items="${options}" var="option" varStatus="count">
													<form:option tabindex="${count.index + 1}" value="${option}">${option}</form:option>
												</c:forEach>	
											</form:select>
										</c:if>
										<c:if test="${subLinkFieldMap.filedNameMap=='DD_09'}">
											<form:select onchange="normalise(this)" path="entity.dd_09_en" cssClass="form-control dd_09_en ena" multiple="false">
													<form:option tabindex="0" value=""><spring:message code="drp.select_en" text="select"/></form:option>
												<c:forEach items="${options}" var="option" varStatus="count">
													<form:option tabindex="${count.index + 1}" value="${option}">${option}</form:option>
												</c:forEach>	
											</form:select>
										</c:if>
										<c:if test="${subLinkFieldMap.filedNameMap=='DD_10'}">
											<form:select onchange="normalise(this)" path="entity.dd_10_en" cssClass="form-control dd_10_en ena" multiple="false">
													<form:option tabindex="0" value=""><spring:message code="drp.select_en" text="select"/></form:option>
												<c:forEach items="${options}" var="option" varStatus="count">
													<form:option tabindex="${count.index + 1}" value="${option}">${option}</form:option>
												</c:forEach>	
											</form:select>
										</c:if>
										<c:if test="${subLinkFieldMap.filedNameMap=='DD_11'}">
											<form:select onchange="normalise(this)" path="entity.dd_11_en" cssClass="form-control dd_11_en ena" multiple="false">
													<form:option tabindex="0" value=""><spring:message code="drp.select_en" text="select"/></form:option>
												<c:forEach items="${options}" var="option" varStatus="count">
													<form:option tabindex="${count.index + 1}" value="${option}">${option}</form:option>
												</c:forEach>	
											</form:select>
										</c:if>
										<c:if test="${subLinkFieldMap.filedNameMap=='DD_12'}">
											<form:select onchange="normalise(this)" path="entity.dd_12_en" cssClass="form-control dd_12_en ena" multiple="false">
													<form:option tabindex="0" value=""><spring:message code="drp.select_en" text="select"/></form:option>
												<c:forEach items="${options}" var="option" varStatus="count">
													<form:option tabindex="${count.index + 1}" value="${option}">${option}</form:option>
												</c:forEach>	
											</form:select>
										</c:if>
								</c:if>
								</div>
								
							     <label class="col-sm-2 control-label"><spring:message code="" text="${subLinkFieldMap.fieldNameRg}"/>&nbsp;<span class="mand">*</span></label>
							     <div class="col-sm-4">
							     <c:if test="${not empty subLinkFieldMap.dropDownOptionReg}">
										<c:set var="options" value="${fn:split(subLinkFieldMap.dropDownOptionReg,',')}" />
										<c:if test="${subLinkFieldMap.filedNameMap=='DD_01'}">
											<form:select onchange="normalise(this,'retro')" path="entity.dd_01_rg" cssClass="form-control dd_01_rg" multiple="false">
												<form:option tabindex="0" value=""><spring:message code="drp.select_reg" text="select"/></form:option>
												<c:forEach items="${options}" var="option" varStatus="count">
													<form:option tabindex="${count.index + 1}" value="${option}">${option}</form:option>
												</c:forEach>
											</form:select>	
										</c:if>
										<c:if test="${subLinkFieldMap.filedNameMap=='DD_02'}">
											<form:select onchange="normalise(this,'retro')" path="entity.dd_02_rg" cssClass="form-control dd_02_rg" multiple="false">
												<form:option tabindex="0" value=""><spring:message code="drp.select_reg" text="select"/></form:option>
												<c:forEach items="${options}" var="option" varStatus="count">
													<form:option tabindex="${count.index + 1}" value="${option}">${option}</form:option>
												</c:forEach>
											</form:select>	
										</c:if>
										<c:if test="${subLinkFieldMap.filedNameMap=='DD_03'}">
											<form:select onchange="normalise(this,'retro')" path="entity.dd_03_rg" cssClass="form-control dd_03_rg" multiple="false">
												<form:option tabindex="0" value=""><spring:message code="drp.select_reg" text="select"/></form:option>
												<c:forEach items="${options}" var="option" varStatus="count">
													<form:option tabindex="${count.index + 1}" value="${option}">${option}</form:option>
												</c:forEach>
											</form:select>	
										</c:if>
										<c:if test="${subLinkFieldMap.filedNameMap=='DD_04'}">
											<form:select onchange="normalise(this,'retro')" path="entity.dd_04_rg" cssClass="form-control dd_04_rg" multiple="false">
												<form:option tabindex="0" value=""><spring:message code="drp.select_reg" text="select"/></form:option>
												<c:forEach items="${options}" var="option" varStatus="count">
													<form:option tabindex="${count.index + 1}" value="${option}">${option}</form:option>
												</c:forEach>
											</form:select>	
										</c:if>
										<c:if test="${subLinkFieldMap.filedNameMap=='DD_05'}">
											<form:select onchange="normalise(this,'retro')" path="entity.dd_05_rg" cssClass="form-control dd_05_rg" multiple="false">
												<form:option tabindex="0" value=""><spring:message code="drp.select_reg" text="select"/></form:option>
												<c:forEach items="${options}" var="option" varStatus="count">
													<form:option tabindex="${count.index + 1}" value="${option}">${option}</form:option>
												</c:forEach>
											</form:select>	
										</c:if>
										<c:if test="${subLinkFieldMap.filedNameMap=='DD_06'}">
											<form:select onchange="normalise(this,'retro')" path="entity.dd_06_rg" cssClass="form-control dd_06_rg" multiple="false">
												<form:option tabindex="0" value=""><spring:message code="drp.select_reg" text="select"/></form:option>
												<c:forEach items="${options}" var="option" varStatus="count">
													<form:option tabindex="${count.index + 1}" value="${option}">${option}</form:option>
												</c:forEach>
											</form:select>	
										</c:if>
										<c:if test="${subLinkFieldMap.filedNameMap=='DD_07'}">
											<form:select onchange="normalise(this,'retro')" path="entity.dd_07_rg" cssClass="form-control dd_07_rg" multiple="false">
												<form:option tabindex="0" value=""><spring:message code="drp.select_reg" text="select"/></form:option>
												<c:forEach items="${options}" var="option" varStatus="count">
													<form:option tabindex="${count.index + 1}" value="${option}">${option}</form:option>
												</c:forEach>
											</form:select>	
										</c:if>
										<c:if test="${subLinkFieldMap.filedNameMap=='DD_08'}">
											<form:select onchange="normalise(this,'retro')" path="entity.dd_08_rg" cssClass="form-control dd_08_rg" multiple="false">
												<form:option tabindex="0" value=""><spring:message code="drp.select_reg" text="select"/></form:option>
												<c:forEach items="${options}" var="option" varStatus="count">
													<form:option tabindex="${count.index + 1}" value="${option}">${option}</form:option>
												</c:forEach>
											</form:select>	
										</c:if>
										<c:if test="${subLinkFieldMap.filedNameMap=='DD_09'}">
											<form:select onchange="normalise(this,'retro')" path="entity.dd_09_rg" cssClass="form-control dd_09_rg" multiple="false">
												<form:option tabindex="0" value=""><spring:message code="drp.select_reg" text="select"/></form:option>
												<c:forEach items="${options}" var="option" varStatus="count">
													<form:option tabindex="${count.index + 1}" value="${option}">${option}</form:option>
												</c:forEach>
											</form:select>	
										</c:if>										
										<c:if test="${subLinkFieldMap.filedNameMap=='DD_10'}">
											<form:select onchange="normalise(this,'retro')" path="entity.dd_10_rg" cssClass="form-control dd_10_rg" multiple="false">
												<form:option tabindex="0" value=""><spring:message code="drp.select_reg" text="select"/></form:option>
												<c:forEach items="${options}" var="option" varStatus="count">
													<form:option tabindex="${count.index + 1}" value="${option}">${option}</form:option>
												</c:forEach>
											</form:select>	
										</c:if>
										<c:if test="${subLinkFieldMap.filedNameMap=='DD_11'}">
											<form:select onchange="normalise(this,'retro')" path="entity.dd_11_rg" cssClass="form-control dd_11_rg" multiple="false">
												<form:option tabindex="0" value=""><spring:message code="drp.select_reg" text="select"/></form:option>
												<c:forEach items="${options}" var="option" varStatus="count">
													<form:option tabindex="${count.index + 1}" value="${option}">${option}</form:option>
												</c:forEach>
											</form:select>	
										</c:if>
										<c:if test="${subLinkFieldMap.filedNameMap=='DD_12'}">
											<form:select onchange="normalise(this,'retro')" path="entity.dd_12_rg" cssClass="form-control dd_12_rg" multiple="false">
												<form:option tabindex="0" value=""><spring:message code="drp.select_reg" text="select"/></form:option>
												<c:forEach items="${options}" var="option" varStatus="count">
													<form:option tabindex="${count.index + 1}" value="${option}">${option}</form:option>
												</c:forEach>
											</form:select>	
										</c:if>
								</c:if>
								</div>
						     </div>
					     	<!-- </div> -->
						</c:if>
							
						<c:if test="${subLinkFieldMap.fieldType!=MainetConstants.DROP_DOWN_BOX and subLinkFieldMap.fieldType !=MainetConstants.TEXT_AREA_HTML and subLinkFieldMap.fieldType !=MainetConstants.LINK_FIELD}">		
						 <div class="form-group">
						<apptags:formField fieldType="${subLinkFieldMap.fieldType}" cssClass="form-control" fieldPath="entity.${ele}" labelCode="${subLinkFieldMap.fieldNameEn}" labelCodeRg="${subLinkFieldMap.fieldNameRg}" isMandatory="${subLinkFieldMap.isMandatory eq 'Y'}" maxlength="${maxlengthprop}" />
						
																</div>
															</c:if>
															<c:if test="${ subLinkFieldMap.fieldType == MainetConstants.LINK_FIELD}">
															 <div class="form-group">
						                        <apptags:formField fieldType="${subLinkFieldMap.fieldType}" cssClass="form-control" fieldPath="entity.${ele}" labelCode="${subLinkFieldMap.fieldNameEn}"  isMandatory="${subLinkFieldMap.isMandatory eq 'Y'}" maxlength="${maxlengthprop}" />
</div>
															</c:if>
					</c:when>
					<c:otherwise>
						<div class="form-group">
	
								<c:set var="showFileNameHTMLId" value="false"/>
								<c:if test="${subLinkFieldMap.fieldType==MainetConstants.ATTACHMENT_FIELD or subLinkFieldMap.fieldType==MainetConstants.PROFILE_IMG or subLinkFieldMap.fieldType==MainetConstants.VIDEO}">
							              <c:choose>
							             <c:when  test="${subLinkFieldMap.fieldType=='6'}">
												     	<c:set var="del" value="video"></c:set>
												     	<c:if test="${command.mode eq 'Edit'}">
												      <div class ="">
												      <c:if test="${not empty command.videoName}">
												     	  <apptags:formField fieldType="${subLinkFieldMap.fieldType}" cssClass="mandClassColor empname subsize" fieldPath="video_path" labelCode="${subLinkFieldMap.fieldNameEn}"  showFileNameHTMLId="true"  randNum="${command.getUUIDNo()}" validnFunction="filterVideo" />
												          <c:forEach items="${command.videoName}" var="Namelist" varStatus="sts">
												         	<c:set var="count" value="${sts.count}"/>
												     		 <div id="videoId_${count}"> 
												          		<p>${Namelist}<img src="css/images/close.png" width="17" alt='Delete File' onclick="deleteUploadFileFromEIP(this,'${Namelist}','${count}','${del}','${ele}')"> </p> 
													        </div> 
													    </c:forEach>
													    </c:if>
													    <c:if test="${empty command.videoName}">
													    <apptags:formField fieldType="${subLinkFieldMap.fieldType}" cssClass="mandClassColor empname subsize" fieldPath="entity.${ele}" labelCode="${subLinkFieldMap.fieldNameEn}"  showFileNameHTMLId="true"  randNum="${command.getUUIDNo()}" validnFunction="filterVideo" />
													    </c:if>
													  </div>
													    </c:if>
													  <c:if test="${command.mode ne 'Edit'}">
												     	  <apptags:formField fieldType="${subLinkFieldMap.fieldType}" cssClass="mandClassColor empname subsize" fieldPath="entity.${ele}" labelCode="${subLinkFieldMap.fieldNameEn}"  showFileNameHTMLId="true"  randNum="${command.getUUIDNo()}" validnFunction="filterVideo" />
												      
													    </c:if>
													
							             
							             </c:when>
							              <c:otherwise>
													<c:choose>
														<c:when test="${subLinkFieldMap.fieldType==MainetConstants.ATTACHMENT_FIELD}">
															<c:set var="del" value="file"></c:set>
														<c:if test="${command.mode eq 'Edit'}">
														<div class="">
														
														<c:if test="${ele eq 'att_01'}">
														<c:if test="${not empty command.attName}">
														<div id="docb1">
												     	    <apptags:formField fieldType="${subLinkFieldMap.fieldType}" cssClass="mandClassColor empname subsize" fieldPath="entity.${ele}" labelCode="${subLinkFieldMap.fieldNameEn}"  showFileNameHTMLId="true" validnFunction="pdfWordExcelvalidn" randNum="${command.getUUIDNo()}" />
												          <c:forEach items="${command.attName}" var="Attlist" varStatus="sts">
												         	<c:set var="count" value="${sts.count}"/>
												     		 <div id="fileid_${ele}_${count}" class="sectionfileshowdiv_${ele}"> 
												          		<p>${Attlist}<img src="css/images/close.png" width="17" alt='Delete File' onclick="deleteUploadFileFromEIP(this,'${Attlist}','${count}','${del}','${ele}')"> </p> 
													        </div> 
													    </c:forEach>
													    </div>
													    </c:if>
													    </c:if>
													    <c:if test="${ele eq 'att_02'}">
													    <c:if test="${not empty command.attName2}">
													    <div id="docb2">
												     	    <apptags:formField fieldType="${subLinkFieldMap.fieldType}" cssClass="mandClassColor empname subsize" fieldPath="entity.${ele}" labelCode="${subLinkFieldMap.fieldNameEn}"  showFileNameHTMLId="true" validnFunction="pdfWordExcelvalidn" randNum="${command.getUUIDNo()}" />
												          <c:forEach items="${command.attName2}" var="Attlist2" varStatus="sts2">
												         	<c:set var="count2" value="${sts2.count}"/>
												     		 <div id="fileid_${ele}_${count2}" class="sectionfileshowdiv_${ele}"> 
												          		<p>${Attlist2}<img src="css/images/close.png" width="17" alt='Delete File' onclick="deleteUploadFileFromEIP(this,'${Attlist2}','${count2}','${del}','${ele}')"> </p> 
													        </div> 
													    </c:forEach>
													    </div>
													    </c:if>
													    </c:if>
													    
													    <c:if test="${ele eq 'att_01'}">
													    <c:if test="${empty command.attName}">
													    <apptags:formField fieldType="${subLinkFieldMap.fieldType}" cssClass="mandClassColor empname subsize" fieldPath="entity.${ele}" labelCode="${subLinkFieldMap.fieldNameEn}"  showFileNameHTMLId="true" validnFunction="pdfWordExcelvalidn" randNum="${command.getUUIDNo()}" />
													    </c:if>
													    </c:if>
													    <c:if test="${ele eq 'att_02'}">
													    <c:if test="${empty command.attName2}">
													    <apptags:formField fieldType="${subLinkFieldMap.fieldType}" cssClass="mandClassColor empname subsize" fieldPath="entity.${ele}" labelCode="${subLinkFieldMap.fieldNameEn}"  showFileNameHTMLId="true" validnFunction="pdfWordExcelvalidn" randNum="${command.getUUIDNo()}" />
													    </c:if>
													    </c:if>
													    </div>
													    </c:if>
														<c:if test="${command.mode ne 'Edit'}">
															<apptags:formField fieldType="${subLinkFieldMap.fieldType}" cssClass="mandClassColor empname subsize" fieldPath="entity.${ele}" labelCode="${subLinkFieldMap.fieldNameEn}"  showFileNameHTMLId="true" validnFunction="pdfWordExcelvalidn" randNum="${command.getUUIDNo()}" />
														</c:if>
															
														
														
														</c:when>
													<c:otherwise>
										
												<c:if test="${command.secType ne command.getLookUpId('PHOTO')}"> 
												<c:choose>
													<c:when test="${command.mode eq 'Edit'}">
													<c:set var="del" value="image"></c:set>
													<c:if test="${not empty command.imgName}">
												       <apptags:formField fieldType="${subLinkFieldMap.fieldType}" cssClass="mandClassColor empname subsize" fieldPath="entity.${ele}" labelCode="${subLinkFieldMap.fieldNameEn}"  showFileNameHTMLId="true"  randNum="${command.getUUIDNo()}" validnFunction="filterImage" maxFileCount="1" callbackOtherTask="hideOrShowDiv()"/>
													<div id="hideOrShowDiv">
													<c:forEach items="${command.imgName}" var="Namelist" varStatus="sts">
												         	<c:set var="count" value="${sts.count}"/>
												     		 <div id="id_${count}"> 
												     	 <p>${Namelist}<img src="css/images/close.png" width="17" alt='Delete File' onclick="deleteUploadFileFromEIP(this,'${Namelist}','${count}','${del}','${ele}')"> </p> 
													        </div> 
													    </c:forEach>
													    </div>
													    </c:if>
													    <c:if test="${empty command.imgName}">
													     <apptags:formField fieldType="${subLinkFieldMap.fieldType}" cssClass="mandClassColor empname subsize" fieldPath="entity.${ele}" labelCode="${subLinkFieldMap.fieldNameEn}"  showFileNameHTMLId="true"  randNum="${command.getUUIDNo()}" validnFunction="filterImage" maxFileCount="1"/>
													    </c:if>
													    
												        </c:when>
												     <c:otherwise>
												    <apptags:formField fieldType="${subLinkFieldMap.fieldType}" cssClass="mandClassColor empname subsize" fieldPath="entity.${ele}" labelCode="${subLinkFieldMap.fieldNameEn}"  showFileNameHTMLId="true"  randNum="${command.getUUIDNo()}" validnFunction="filterImage" maxFileCount="1"/>
												   </c:otherwise>
												    </c:choose>
												     </c:if>
												     <c:if test="${command.secType eq command.getLookUpId('PHOTO')}"> 
												     
												     	<c:set var="del" value="image"></c:set>
												     	<c:if test="${command.mode eq 'Edit'}">
												        <div class =""> 
												         <c:if test="${not empty command.imgName}">
												     	    <apptags:formField fieldType="${subLinkFieldMap.fieldType}" cssClass="mandClassColor empname subsize" fieldPath="img_path" labelCode="${subLinkFieldMap.fieldNameEn}"  showFileNameHTMLId="true"  randNum="${command.getUUIDNo()}" validnFunction="filterImage" />
												          <c:forEach items="${command.imgName}" var="Namelist" varStatus="sts">
												         	<c:set var="count" value="${sts.count}"/>
												     		 <div id="id_${count}"> 
												          		<p>${Namelist}<img src="css/images/close.png" width="17" alt='Delete File' onclick="deleteUploadFileFromEIP(this,'${Namelist}','${count}','${del}','${ele}')"> </p> 
													        </div> 
													    </c:forEach>
													    </c:if>
													    <c:if test="${empty command.imgName}">
													     <apptags:formField fieldType="${subLinkFieldMap.fieldType}" cssClass="mandClassColor empname subsize" fieldPath="entity.${ele}" labelCode="${subLinkFieldMap.fieldNameEn}"  showFileNameHTMLId="true"  randNum="${command.getUUIDNo()}" validnFunction="filterImage" fileSize="20000" maxFileCount="1"/>
													    </c:if>
													    </div>
													    </c:if>
													  <c:if test="${command.mode ne 'Edit'}">
													  <div class =""> 
													     
													      <apptags:formField fieldType="${subLinkFieldMap.fieldType}" cssClass="mandClassColor empname subsize" fieldPath="entity.${ele}" labelCode="${subLinkFieldMap.fieldNameEn}"  showFileNameHTMLId="true"  randNum="${command.getUUIDNo()}" validnFunction="filterImage" fileSize="20000" maxFileCount="1"/>
													   </div>
													    </c:if>
													</c:if>
													
												     </c:otherwise>
													</c:choose>
											</c:otherwise>
									</c:choose>	
													
								</c:if>

						</div>
					</c:otherwise>
				</c:choose>
			</c:if>
				
			</c:forEach>
			
			<c:set var="isckEditor" value="${ true}" scope="page"></c:set>
							<c:forEach items="${command.section.subLinkFieldMappings}" var="subFileMap">								
								<c:if test="${subFileMap.fieldType == MainetConstants.TEXT_AREA_HTML}">
									<c:set var="isckEditor" value="${ false}" scope="page"></c:set>
								</c:if>
							</c:forEach>
			<c:if test="${command.subLinkMaster.isArchive eq 'Y'}">
			<div class="form-group">
				<label class="control-label col-sm-2">
					<spring:message code="admin.publicNotice.IssueDate" />&nbsp;<span class="mand">*</span>
				</label>
				<div class="col-sm-4">
					<apptags:dateField cssClass="mandClassColor form-control empname subsize" datePath="entity.issueDate" fieldclass="datetimepicker" readonly="true"></apptags:dateField>
				</div>
				<label class="control-label col-sm-2">
					<spring:message code="admin.publicNotice.ValidityDate" />&nbsp;<span class="mand">*</span>
				</label>
				<div class="col-sm-4">
					<apptags:dateField cssClass="mandClassColor form-control empname subsize" datePath="entity.validityDate" fieldclass="datetimepicker" readonly="true"></apptags:dateField>
				</div>
				
			</div>
			<div class="form-group">
			<div id="yoyo">
					<label class="col-sm-2 control-label"><spring:message code="eip.secDetOrd" text="Section Detail Order"/></label>
					<div class="col-sm-4">
						<form:input path="entity.subLinkFieldDtlOrd" cssClass=" hasNumber form-control"/>
					</div>
					<c:if test ="${isckEditor}">
				<label class="col-sm-2 control-label" for="entity.isHighlighted"><spring:message
							code="eip.section.details.ishighlighted" text="Is Highlighted" /></label>
					<div class="col-sm-4">
						<form:checkbox path="entity.isHighlighted" value="Y"/>
					</div>
					</c:if>
				</div>
			</div>	
			</c:if>
			
			<c:if test="${command.subLinkMaster.isArchive ne 'Y'}">
			<div class="form-group" id="yoyo">
				<label class="col-sm-2 control-label"><spring:message code="eip.secDetOrd" text="Section Detail Order"/></label>
				<div class="col-sm-4">
					<form:input path="entity.subLinkFieldDtlOrd" cssClass=" hasNumber form-control"/>
				</div>
				<c:if test ="${isckEditor}">
				<label class="col-sm-2 control-label" for="entity.isHighlighted"><spring:message
							code="eip.section.details.ishighlighted" text="Is Highlighted" /></label>
					<div class="col-sm-4">
						<form:checkbox path="entity.isHighlighted" value="Y"/>
					</div>
					</c:if>
					
			</div>
			</c:if>
			
			<div class="form-group padding-top-10" id="AdminFaqCheker">				
					<div class="col-sm-12 text-center">
                      
                       <form:radiobutton path="entity.chekkerflag" name="radiobutton" value="Y" class="radiobutton"/> <spring:message code="Authenticate" text="Authenticate" />&nbsp;&nbsp;
                       <form:radiobutton path="entity.chekkerflag" name="radiobutton" value="N" class="radiobutton"/> <spring:message code="Reject" text="Reject" />
	                </div>					
				</div> 
				
			<div class="text-center margin-top-10" >
				<input class="btn btn-success AdminFaqChekerbutton" type="button" value="<spring:message code="eip.admin.helpDocs.save"/>" onclick="return saveOrUpdateSection(this,'', 'SectionDetails.html?AdminFaqCheker','SaveDetail');" id="getFormId"></input>
				<input class="btn btn-success AdminFaqbutton" type="button" value="<spring:message code="eip.admin.helpDocs.save"/>" onclick="return saveOrUpdateSection(this,'', 'SectionDetails.html','SaveDetail');" id="getFormId"></input>
				<%-- <apptags:submitButton entityLabelCode="Details"  cssClass="btn btn-success" successUrl="SectionDetails.html" actionParam="SaveDetail"/> --%>
				<%-- <apptags:submitButton entityLabelCode="Details"  cssClass="AdminFaqChekerbutton btn btn-success" successUrl="SectionDetails.html" actionParam="SaveDetail"/> --%>
			  <%-- <input class="btn btn-success AdminFaqChekerbutton" type="button" value="<spring:message code="eip.admin.helpDocs.save" text="Save"/>" onclick="return saveOrUpdateSection(this,'', 'SectionDetails.html','SaveDetail');" id="getFormId"></input> --%>
			  <%-- <input type="button" class="btn btn-warning" value="<spring:message code="saveBtn" text="Save"/>" onclick="saveForm(this)" > --%>
				<input type="button" class="btn btn-warning" value="<spring:message code="rstBtn"/>" onclick="resetForm(this)" >
				<input type="button" class="btn btn-danger" value="<spring:message code="section.cancelElement" text="section.cancelElement"/>" onclick="return goBack();"/>
			</div>
			
		</form:form>
		
	</c:otherwise>
	
</c:choose>
</div>
		<c:if test="${not command.listMode}"> 		
	<div class="update-info">
	
	
		<ul>
			<li>
				<div class="ui-header"><spring:message code="audit.fields.creator" text="Creator"/></div>
				<div class="ui-content">
					<p>
						<span class="ui-u-name"><spring:message code="audit.fields.username" text="Username"/></span>
						<c:if test="${ not empty command.entity.createdBy }">
						<span>${command.entity.createdBy }</span>
						</c:if>
					</p>
					<p>
						<span class="ui-date"><spring:message code="audit.fields.createdon" text="Created On"/></span>
						<c:if test="${not empty command.entity.createdBy  and not empty command.entity.lmodDate }">
						<fmt:formatDate type = "both" value="${command.entity.lmodDate}"  dateStyle="SHORT"  timeStyle="SHORT"  pattern="dd/MM/yyyy  HH:mm "  var="createdDate"/>
						<span>${createdDate}</span>
						</c:if>
					</p>
				</div>
			</li> 
			 
				<li>
					<div class="ui-header"><spring:message code="audit.fields.approver" text="Approver"/></div>
					<div class="ui-content">
						<p>
							<span class="ui-u-name"><spring:message code="audit.fields.username" text="Username"/></span>
							 <c:if test="${ not empty command.entity.lastApprovedBy }">
							<span>${command.entity.lastApprovedBy}</span>
							</c:if> 
						</p>
						<p>
							<span class="ui-date"><spring:message code="audit.fields.modifiedon" text="Modified On"/></span>
							<c:if test="${not empty command.entity.lastApprovedBy  and  not empty command.entity.lastApprovedDate }">
							<fmt:formatDate type = "both" value="${command.entity.lastApprovedDate}"  dateStyle="SHORT"  timeStyle="SHORT"  pattern="dd/MM/yyyy  HH:mm "  var="lastApprovedDate"/>
							<span>${lastApprovedDate}</span>
							</c:if>
							
						</p>
					</div>
				</li>
			
			 <li>
				<div class="ui-header"><spring:message code="audit.fields.modifier" text="Modifier"/></div>
				<div class="ui-content">
					<p>
						<span class="ui-u-name"><spring:message code="audit.fields.username" text="Username"/></span>
						<c:if test="${ not empty command.entity.modifiedBy }">
						<span>${command.entity.modifiedBy}</span>
						</c:if>
					</p>
					<p>
						<span class="ui-date"><spring:message code="audit.fields.modifiedon" text="Modified On"/></span>
						<c:if test="${not empty command.entity.modifiedBy and not empty command.entity.updatedDate }">
						 <fmt:formatDate type = "both" value="${command.entity.updatedDate}"  dateStyle="SHORT"  timeStyle="SHORT"  pattern="dd/MM/yyyy  HH:mm "  var="updatedDate"/>
						<span>${updatedDate}</span>
						</c:if>
					</p>
				</div>
			</li> 
		</ul>
	</div>
	</c:if>
 </div>
 </div>	
 </div>
 
<script>
$(document).ready(function(){
	$( "#docb1" ).on("change", function( event ) {
	   $(".sectionfileshowdiv_att_01").remove();
	});
	$("#docb2" ).on("change", function( event ) {
	   $(".sectionfileshowdiv_att_02").remove();
	}); 
});
</script>