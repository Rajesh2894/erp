<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script>
    jQuery(document).ready(function($){
    	
    	$('.breadcrumb').html("");
    	var stored = '';
    	if($('#langId').val() == 1){
    		stored =	localStorage['breadCrumbDataPortal'];
    	}else{
    		stored =	localStorage['breadCrumbdataRegPortal'];
    		
    	}
    	
		if(stored == undefined || stored == null || stored == '' ){
			stored =	localStorage['breadCrumbDataPortal'];
		}
    	$('.breadcrumb').append(stored);
    	
    });
  </script>
    	
<ol class="breadcrumb">
</ol>
<input type="hidden" value ="${userSession.getCurrent().getLanguageId()}" id="langId"/>