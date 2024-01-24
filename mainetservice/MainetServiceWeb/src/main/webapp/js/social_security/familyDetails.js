/*$(document).ready(function() {


 reOrderownerFamilyDetailsSequence('.appendableFamilyClass');




 });*/

$(function() {
	$(".datepicker2").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d',
		yearRange : "-150:-0"
	});

	$(".datepicker2").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
});
$(function() {
	/* To add new Row into table */
	$("#addUnitRow").on('click', function() {

		
		var errorList = [];
		errorList = validateownerFamilyDetailsTable(errorList);
		if (errorList.length == 0) {

			// fileCountUpload();
			var content = $("#ownerFamilyDetail").find('tr:eq(1)').clone();
			$("#ownerFamilyDetail").append(content);

			content.find("input:text").val('');
			content.find("select").val('');
			content.find("textarea").val('');
			$(".datepicker2").removeClass("hasDatepicker");
			// content.find("select").val('0');
			content.find("input:hidden").val('');
			$('.error-div').hide();
			$(document).on("focus", ".datepicker2", function(){
		        $(this).datepicker({
		        	dateFormat : 'dd/mm/yy',
		    		changeMonth : true,
		    		changeYear : true
		        });
		});
			reOrderownerFamilyDetailsSequence('.appendableFamilyClass'); // reorder
																			// id
																			// and
																			// Path
		} else {
			displayErrorsOnPage(errorList);
		}
	});
});

function validateownerFamilyDetailsTable() {
	
	var errorList = [];
	var rowCount = $('#ownerFamilyDetail tr').length - 1;
	var mobNo = [];
	var famMemUidNo = [];
	var email = [];

	if ($.fn.DataTable.isDataTable('#ownerFamilyDetail')) {
		$('#ownerFamilyDetail').DataTable().destroy();
	}
	if (errorList == 0)
		$("#ownerFamilyDetail tbody tr")
				.each(
						function(i) {

							if (rowCount < 2) {

								var famMemName = $("#famMemName" + i).val();
								var relation = $("#relation" + i).val();
								var gender = $("#gender" + i).val();
								var dob = $("#dob" + i).val();
								var age = $("#age" + i).val();
								var education = $("#education" + i).val();
								var occupation = $("#occupation" + i).val();
								var contactNo = $("#contactNo" + i).val();

								var constant = 1;
							} else {
								var famMemName = $("#famMemName" + i).val();
								var relation = $("#relation" + i).val();
								var gender = $("#gender" + i).val();
								var dob = $("#dob" + i).val();
								var age = $("#age" + i).val();
								var education = $("#education" + i).val();
								var occupation = $("#occupation" + i).val();
								var contactNo = $("#contactNo" + i).val();

								var constant = i + 1;
							}
							if (famMemName == '0' || famMemName == undefined
									|| famMemName == "") {
								errorList
										.push(getLocalMessage("social.validation.famMemName")
												+ " " + constant);
							}
							if (relation == '0' || relation == undefined
									|| relation == "") {
								errorList
										.push(getLocalMessage("social.validation.relation")
												+ " " + constant);
							}
							if (gender == '0' || gender == undefined
									|| gender == "") {
								errorList
										.push(getLocalMessage("social.validation.gender")
												+ " " + constant);
							}
							if (dob == '0' || dob == undefined
									|| dob == "") {
								errorList
										.push(getLocalMessage("social.validation.dob")
												+ " " + constant);
							}
							if (age == '0' || age == undefined
									|| age == "") {
								errorList
										.push(getLocalMessage("social.validation.age")
												+ " " + constant);
							}
							if (education == '0' || education == undefined
									|| education == "") {
								errorList
										.push(getLocalMessage("social.validation.education")
												+ " " + constant);
							}
							if (occupation == '0' || occupation == undefined
									|| occupation == "") {
								errorList
										.push(getLocalMessage("social.validation.occupation")
												+ " " + constant);
							}
							if (contactNo == '0' || contactNo == undefined
									|| contactNo == "") {
								errorList
										.push(getLocalMessage("social.validation.contactNo")
												+ " " + constant);
							}

						});

	return errorList;
}

function reOrderownerFamilyDetailsSequence(classNameFirst) {

	$(classNameFirst).each(
			function(i) {
				
				var famMemName = $("#famMemName" + i).val();
				var relation = $("#relation" + i).val();
				var gender = $("#gender" + i).val();
				var dob = $("#dob" + i).val();
				var age = $("#age" + i).val();
				var education = $("#education" + i).val();
				var occupation = $("#occupation" + i).val();
				var contactNo = $("#contactNo" + i).val();

				// id binding
				$(this).find("input:text:eq(0)").attr("id", "famMemName" + i);
				$(this).find("input:text:eq(1)").attr("id", "relation" + i);
				$(this).find("select:eq(0)").attr("id", "gender" + i);
				$(this).find("input:text:eq(2)").attr("id", "dob" + i);

				$(this).find("input:text:eq(3)").attr("id", "age" + i);
				$(this).find("input:text:eq(4)").attr("id", "education" + i);
				$(this).find("input:text:eq(5)").attr("id", "occupation" + i);
				$(this).find("input:text:eq(6)").attr("id", "contactNo" + i);

				// path binding
				$(this).find("input:text:eq(0)").attr(
						"name",
						"applicationformdto.ownerFamilydetailDTO[" + i
								+ "].famMemName");
				$(this).find("input:text:eq(1)").attr(
						"name",
						"applicationformdto.ownerFamilydetailDTO[" + i
								+ "].relation");
				$(this).find("select:eq(0)").attr(
						"name",
						"applicationformdto.ownerFamilydetailDTO[" + i
								+ "].gender");
				$(this).find("input:text:eq(2)").attr(
						"name",
						"applicationformdto.ownerFamilydetailDTO[" + i
								+ "].dob");
				$(this).find("input:text:eq(3)").attr(
						"name",
						"applicationformdto.ownerFamilydetailDTO[" + i
								+ "].age");
				$(this).find("input:text:eq(4)").attr(
						"name",
						"applicationformdto.ownerFamilydetailDTO[" + i
								+ "].education");
				$(this).find("input:text:eq(5)").attr(
						"name",
						"applicationformdto.ownerFamilydetailDTO[" + i
								+ "].occupation");
				$(this).find("input:text:eq(6)").attr(
						"name",
						"applicationformdto.ownerFamilydetailDTO[" + i
								+ "].contactNo");

			});
}

$('body').on('focus', ".hasAadharNo", function() {
	$('.hasAadharNo').keyup(function() {
		this.value = this.value.replace(/[^0-9]/g, '');
	});
});

function DeleteRow(obj)

{

	if ($("#ownerFamilyDetail tr").length != 2) {
		$(obj).parent().parent().remove();
		/* $(deleteRow).closest('tr').remove(); */
		reOrderownerFamilyDetailsSequence('.appendableFamilyClass');
	} else {
		var errorList = [];
		errorList.push(getLocalMessage("trade.firstrowcannotbeRemove"));
		displayErrorsOnPage(errorList);
	}
}
