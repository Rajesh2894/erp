<%@tag import="com.abm.mainet.common.exception.FrameworkException"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ attribute name="gridid" required="true" rtexprvalue="true"	description="The id of the grid table."%>
<%@ attribute name="id" required="true" rtexprvalue="true"	description="The id of the grid-div"%>
<%@ attribute name="url" required="false" rtexprvalue="true" description="Default request send to server for displaying grid data"%>
<%@ attribute name="mtype" required="false" rtexprvalue="false"	description="Type of request want to send i.e, GET/POST"%>
<%@ attribute name="width" required="false" rtexprvalue="false"	description="Custom Width given to the grid table. Default is auto."%>
<%@ attribute name="height" required="true" rtexprvalue="false"	description="Custom height given to the grid. Default is zero."%>
<%@ attribute name="colHeader" required="true" rtexprvalue="true"	description="The column header given to the grid."%>
<%@ attribute name="colModel" required="true" rtexprvalue="true" description="The column model(s) for maaping json data to the respective column header."%>
<%@ attribute name="caption" required="true" rtexprvalue="false" description="The caption given to the grid."%>

<%@ attribute name="editurl" required="false" rtexprvalue="false" description="URL of server to perform edit operation.Default is taken from 'gridId' attribute."%>
<%@ attribute name="deleteURL" required="false" rtexprvalue="true" description="URL of server to perform delete operation.Default is taken from 'gridId' attribute."%>

<%@ attribute name="view" required="false" rtexprvalue="true" description=""%>
<%@ attribute name="add" required="false" rtexprvalue="true" description=""%>
<%@ attribute name="edit" required="false" rtexprvalue="true" description=""%>
<%@ attribute name="del" required="false" rtexprvalue="true" description=""%>
<%@ attribute name="search" required="false" rtexprvalue="true" type="java.lang.Boolean" description=""%>
<%@ attribute name="refresh" required="false" rtexprvalue="true" description=""%>

<%@ attribute name="loadonce" required="true" rtexprvalue="true" description=""%>

<%@ attribute name="showrow" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Whether to display row number column or not."%>	
<%@ attribute name="sortCol" required="false" rtexprvalue="false" description="Default sort column name"%>

<%@ attribute name="isChildGrid" required="true" rtexprvalue="true" type="java.lang.Boolean" description="Indicates whether grid is parent child grid or not."%>

<%@ attribute name="hasActive" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Whether to show active column header in grid or not."%>
<%@ attribute name="hasViewDet" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Whether to show view details column header in grid or not."%>
<%@ attribute name="viewAjaxRequest" required="false" type="java.lang.Boolean" description=""%>
<%@ attribute name="hasEdit" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Whether to show edit column header in grid or not."%>
<%@ attribute name="showInDialog" required="false" rtexprvalue="true" type="java.lang.Boolean"  description="Whether to show in pop-up dialog or not. Default is false." %>
<%@ attribute name="hasDelete" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Whether to show delete column header in grid or not."%>

<%@ attribute name="showTotal" required="false" rtexprvalue="false" type="java.lang.Boolean" description="Whether to show total of the column. Default is false."%>
<%@ attribute name="colNames" required="false" rtexprvalue="true" type="java.lang.String" description="Coma(,) separated column name(s) whose total to be displayed at the footer."%>
<%@ attribute name="postData" required="false" rtexprvalue="false" type="java.lang.String" description="send data to controller"%>
<%@ attribute name="multiselect" required="false" rtexprvalue="true" description="Default request send to server for displaying grid multiselect status"%>

<jsp:useBean id="labelResolver" class="com.abm.mainet.common.util.LabelResolver"/>

<%	// if grid is child and show viewDetails column set true
	/* if((isChildGrid)	&&	(hasViewDet!=null	&&	hasViewDet)	)
	{
		throw new FrameworkException("Child grid cannot have 'Viewdetails' column header.");
	} */

	if(viewAjaxRequest==null)
	{
		viewAjaxRequest	=	true;
	}
	
	if(search==null)
	{
		search	=	true;
	}
	
	
	// if grid is not child grid and (viewDetails and edit) column set true
	if((hasViewDet!=null && hasViewDet) && (hasEdit!=null && hasEdit))
	{
		throw new FrameworkException("In parent grid, 'ViewDetails' and 'Edit' column cannot co-exist.");
	}

	if(showTotal==null || !showTotal)
	{
		if(colNames!=null && colNames.length()>0)
		{
			throw new FrameworkException("Cannot set 'colNames' when 'showTotal' set as 'false' or 'null'.");
		}
		else
		{	
			colNames=new String();
			showTotal	=	false;
		}
	}
	
	if(showTotal!=null && showTotal)
	{
		if(colNames==null || colNames.length()==0)
		{
			throw new FrameworkException("'colNames' cannot be 'empty' or 'null' when 'showTotal' set as 'true'.");
		}
		else
		{
			showrow	=	true;
		}
	} 
	
	if(showrow==null)
	{	
		showrow	=	false;	
	}

	if(hasActive!=null && hasActive)
	{
		colHeader	+=	",grid.active";
		colModel	=	colModel.replace(']', ' ');
		
		if(!isChildGrid)
		{
			colModel	+=	",{name : 'activeStatus',index : 'activeStatus',search : false,sortable : false,formatter : activeTemplate,width : 50,align :'center'}]";
		}
		else
		{
			colModel	+=	",{name : 'activeStatus',index : 'activeStatus',search : false,sortable : false,formatter : childActiveTemplate,width : 70,align :'center'}]";
		}
	}
	if(hasViewDet!=null && hasViewDet)
	{
		colHeader	+=	",grid.viewDet";
		colModel	=	colModel.replace(']', ' ');
		
		if(!isChildGrid)
		{
			colModel	+=	",{name : 'editFlag',index : 'editFlag' ,search : false,sortable : false, formatter : viewTemplate,width : 82,align :'center'}]";
		}
		else
		{
			colModel	+=	",{name : 'activeStatus',index : 'activeStatus',search : false,sortable : false,formatter : childViewTemplate,width : 82,align :'center'}]";
		}
		
	}
	if(hasEdit!=null && hasEdit)
	{
		colHeader	+=	",grid.edit";
		colModel	=	colModel.replace(']', ' ');
		
		if(showInDialog==null)
		{
			showInDialog	=	false;
		}
		
		if(!isChildGrid)
		{
			if(showInDialog)
				colModel	+=	",{name : 'editFlag',index : 'editFlag',search : false,sortable : false,formatter : editTemplate, showInDialog : true ,width : 50,align :'center'}]";
			else
				colModel	+=	",{name : 'editFlag',index : 'editFlag',search : false,sortable : false,formatter : editTemplate, showInDialog : false ,width : 50,align :'center'}]";
		}
		else
		{
			colModel	+=	",{name : 'editFlag',index : 'editFlag', search : false,sortable : false,formatter : childEditTemplate,width : 50,  align :'center'}]";
		}
	}
	
	if(hasDelete!=null && hasDelete)
	{
		colHeader	+=	",grid.delete";
		colModel	=	colModel.replace(']', ' ');
		
		if(!isChildGrid)
		{
			colModel	+=	",{name : 'deleteFlag',index : 'deleteFlag',search : false,sortable : false, formatter : deleteTemplate,width : 50,align :'center'}]";
		}
		else
		{
			colModel	+=	",{name : 'deleteFlag',index : 'deleteFlag',search : false,sortable : false,formatter : childDeleteTemplate,width : 60,align :'center'}]";
		}
	}
%>

<script>
	$(function() {		
		prepareGrid({
			gridId:"${gridid}",
			id : "${id}",
			mtype : "${mtype}",
			width : "${width}",
			height : "${height}",
			pagerId : "pager_${gridid}",
			colHeader : <%=labelResolver.getGridColumHeader(colHeader)%>,
			colModel : <%=colModel%>,
			url : "${url}",
			sortCol : "${sortCol}",
			caption : "<%=labelResolver.getColumHeader(caption)%>",
			loadonce : ${loadonce},
			showrow	: <%=showrow%>,
			showTotal :  <%=showTotal%>,
			colNames : "<%=colNames%>",
			multiselect : <%=multiselect%>,
			editurl : "${editurl}",
			deleteURL : "${deleteURL}",
			viewAjaxRequest : <%=viewAjaxRequest%>,
			search:<%=search%>,
			postData:{data : function() {
				   return '${postData}';
			    }
             }
		});
	});
</script>

<!-- jqgrid height auto -->
<script>
$(document).ready(function() {
	  if ($("select.ui-pg-selbox option[value='5']").length) {
		  $(".ui-jqgrid .ui-jqgrid-bdiv").css('height', 'auto');
	  }	
	});
	
function editLink(cellValue, options, rowdata, action)
{
	if(rowdata.attPath === null || rowdata.attPath === ""){
		return "";
	}else{
		
		var value = rowdata.attPath;
		if(value !== null && value !== ''  && value!==undefined) {
			var pathValue = value.split('/');
			var fileExtention = pathValue[pathValue.length-1];
			var fileName=fileExtention.split('.');
			return "<a href='./"+ rowdata.attPath +"' target='_blank' class=''>"+fileName[0]+"<i class='fa fa-download'></i></a>";
		}else{
			return "";
		}
		
	}
    
}
function showImageOnGrid(cellValue, options, rowdata, action)
{
		var value = cellValue;
		if(value !== null && value !== ''  && value!==undefined) {
			var pathValue = value.split('/');
			var fileExtention = pathValue[pathValue.length-1];
			var fileName=fileExtention.split('.');
			 return "<div class='member-profile-image'><img src='./"+ value +"' alt='"+ fileName[0] +"'	title='"+ fileName[0] +"' class='img-responsive'> </div>";
		}else{
			return "";
		}
		
	
    
}
</script>

<div id="${id}" align="center">
	<table id="${gridid}"></table>
	<div id="pager_${gridid}"></div>
</div>