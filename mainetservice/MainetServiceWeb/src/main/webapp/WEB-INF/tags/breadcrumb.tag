<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script type="text/javascript">
    jQuery(document).ready(function($){
    	$('.breadcrumb').html("");
    	var stored = localStorage['breadCrumbData'];
    	$('.breadcrumb').append(stored);
    });
  </script>
    	
<ol class="breadcrumb">
</ol>
 