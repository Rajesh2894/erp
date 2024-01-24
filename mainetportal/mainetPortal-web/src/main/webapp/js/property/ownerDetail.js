function getOwnerDetails() {

	var ownerType = $("#ownerTypeId" + " option:selected").attr("code");
	// alert("ownerType >> " + ownerType);

	if (ownerType != undefined) {
		if (ownerType != "SO" && ownerType != "JO") {

			$(".singleOwnerDetail").hide();
			$(".ownerDetails").hide();

			$(".jointOwnerDetails").hide();
			$(".OwnerDetails").hide();

			$(".companyDetail").show();
			$(".detail").show();

		} else {
			/*
			 * $(".jointOwnerDetails").hide(); $(".companyDetail").hide();
			 */
			getSingleAndJoinOwnersDet(ownerType);
		}
	} else {

		$(".companyDetail").hide();
		$(".singleOwnerDetail").hide();
		$(".ownerDetails").hide();
		$(".jointOwnerDetails").hide();
		$(".OwnerDetails").hide();
		$(".ownerDetails").hide();
		$(".detail").hide();

	}

	/*
	 * if (ownerType == "SO") { $(".singleOwnerDetail").show();
	 * $(".ownerDetails").show(); $(".details").hide(); } else {
	 * $(".singleOwnerDetail").hide(); $(".ownerDetails").hide();
	 * $(".details").hide(); } if (ownerType == "JO") {
	 * $(".jointOwnerDetails").show(); $(".OwnerDetails").show();
	 * $(".details").hide(); } else { $(".jointOwnerDetails").hide();
	 * $(".ownerDetails").hide(); $(".details").hide(); }
	 * 
	 * if (ownerType != "SO" && ownerType != "JO") { $(".companyDetail").show();
	 * $(".details").hide(); } else { $(".companyDetail").hide();
	 * $(".details").hide(); }
	 */

}

function getSingleAndJoinOwnersDet(ownerType) {

	if (ownerType == "SO") {

		$(".singleOwnerDetail").show();
		$(".ownerDetails").show();

		$(".jointOwnerDetails").hide();
		$(".OwnerDetails").hide();

	}

	if (ownerType == "JO") {

		$(".singleOwnerDetail").hide();

		$(".jointOwnerDetails").show();
		$(".OwnerDetails").show();
		$(".ownerDetails").show();
	}

	$(".companyDetail").hide();
	$(".detail").show();

}

$(document).ready(function() {

	var ownerType = $("#ownerTypeId" + " option:selected").attr("code");

	if (ownerType != undefined) {
		// alert("ownerType >> " + ownerType);
		if (ownerType == "" || (ownerType != "SO" && ownerType != "JO")) {

			$(".singleOwnerDetail").hide();

			$(".jointOwnerDetails").hide();

			$(".companyDetail").hide();

			$(".ownerDetails").hide();

			$(".details").hide();

		} else {

			if (ownerType != "SO" && ownerType != "JO") {

				$(".singleOwnerDetail").hide();
				$(".ownerDetails").hide();

				$(".jointOwnerDetails").hide();
				$(".OwnerDetails").hide();

				$(".companyDetail").show();

			} else {
				/*
				 * $(".jointOwnerDetails").hide(); $(".companyDetail").hide();
				 */
				getSingleAndJoinOwnersDet(ownerType);
			}

			/*
			 * if (ownerType == "SO") {
			 * 
			 * $(".singleOwnerDetail").show(); $(".ownerDetails").show();
			 * $(".details").hide(); }
			 * 
			 * if (ownerType == "JO") {
			 * 
			 * $(".singleOwnerDetail").hide(); $(".details").hide();
			 * 
			 * $(".jointOwnerDetails").show(); $(".OwnerDetails").show();
			 * $(".details").hide(); $(".ownerDetails").show(); }
			 * 
			 * if (ownerType != "SO" && ownerType != "JO") {
			 * $(".companyDetail").show(); $(".details").hide(); } else {
			 * 
			 * $(".jointOwnerDetails").hide(); $(".details").hide();
			 * 
			 * $(".companyDetail").hide(); $(".details").hide(); }
			 */

		}
	} else {

		$(".companyDetail").hide();
		$(".singleOwnerDetail").hide();
		$(".ownerDetails").hide();
		$(".jointOwnerDetails").hide();
		$(".OwnerDetails").hide();
		$(".ownerDetails").hide();
		$(".detail").hide();

	}

});

$(function() {

	jQuery('.hasDecimal').keyup(function() {
		this.value = this.value.replace(/[^0-9\.]/g, '');
	});

	$("#jointOwner").on('click', '.addCF', function() {

		var errorList = [];

		if (errorList.length == 0) {
			var content = $(this).closest('tr').clone();
			$(this).closest("tr").after(content);
			var clickedIndex = $(this).parent().parent().index() - 1;
			content.find("input:text").val('');
			// content.find('input[type=checkbox][value=N]').prop('checked',
			// false);
			content.find("select").val('0');

			$('.error-div').hide();

			reOrderJointOwnerTableSequence('.jointTable');
			return false;
		} else {
			displayErrorsOnPage(errorList);
		}

	});

	$("#jointOwner").on('click', '.remCF', function() {

		if ($("#jointOwner tr").length != 2) {
			$(this).parent().parent().remove();
			reOrderJointOwnerTableSequence('.jointTable');
		} else {
			var errorList = [];
			errorList.push("First row cannot be remove.");
			displayErrorsOnPage(errorList);

		}

	});

});

function reOrderTableSequence(className) {

	$(className).each(
			function(i) {
				// re-ordering id

				$(this).find("select:eq(0)").attr("id", "ownerGender_" + i);
				$(this).find("input:text:eq(0)").attr("id",
						"assoOwnerName_" + i);
				$(this).find("input:text:eq(1)").attr("id",
						"assoFathusName_" + i);
				$(this).find("input:text:eq(2)")
						.attr("id", "assoMobileno_" + i);
				$(this).find("input:text:eq(3)")
						.attr("id", "assoAddharno_" + i);

				/*
				 * $(this).find("input:text:eq(3)").attr("id", "yeardate_"+i);
				 * $(this).find("input:text:eq(4)").attr("id", "rent_"+i);
				 */
				if (i == 0) {
					$(this).set("hidden:eq(0)").attr("name",
							"tableOwnerList[" + i + "].assoOtype") == "P";
				} else {
					$(this).set("hidden:eq(0)").attr("name",
							"tableOwnerList[" + i + "].assoOtype") == "S";
				}

				$("#srNoId_" + i).text(i + 1);
				"+i+"
				// re-ordering path binding
				$(this).find("select:eq(0)").attr("name",
						"tableOwnerList[" + i + "].assoGender");
				$(this).find("input:text:eq(0)").attr("name",
						"tableOwnerList[" + i + "].assoOwnerName");
				$(this).find("input:text:eq(1)").attr("name",
						"tableOwnerList[" + i + "].assoFathusName");
				$(this).find("input:text:eq(2)").attr("name",
						"tableOwnerList[" + i + "].assoMobileno");
				$(this).find("input:text:eq(3)").attr("name",
						"tableOwnerList[" + i + "].assoAddharno");

				// $(this).find("input:text:eq(4)").attr("name","tableDataList["+i+"].assdrent");

			});

}
