;(function($){
/**
 * jqGrid English Translation
 * Tony Tomov tony@trirand.com
 * http://trirand.com/blog/ 
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html
**/
$.jgrid = $.jgrid || {};
$.extend($.jgrid,{
	defaults : {
		recordtext: "{0} - {1} च्या {2} पहा",
		emptyrecords: "पाहण्यासाठी तपशील नाही",
		loadtext: "लोड करीत आहे ...",
		pgtext : "{1} चे पृष्ठ {0}"
	},
	search : {
		caption: "शोधा ...",
		Find: "शोधण्यासाठी",
		Reset: "रीसेट करा",
		odata: [{ oper:'eq', text:'समान'},{ oper:'ne', text:'अगदी नाही'},{ oper:'lt', text:'कमी'},{ oper:'le', text:'कमी किंवा समान'},{ oper:'gt', text:'ग्रेटर'},{ oper:'ge', text:'अधिक किंवा समान'},{ oper:'bw', text:'ने सुरू होते'},{ oper:'bn', text:'ने सुरू होत नाही'},{ oper:'in', text:'मध्ये आहे'},{ oper:'ni', text:'मध्ये नाही'},{ oper:'ew', text:'सह समाप्त होते'},{ oper:'en', text:'संपत नाही'},{ oper:'cn', text:'समाविष्ट'},{ oper:'nc', text:'समाविष्ट नाही'}],
		groupOps: [	{ op: "AND", text: "सर्व" },	{ op: "OR",  text: "कोणतीही" }	]
	},
	edit : {
		addCaption: "रेकॉर्ड जोडा",
		editCaption: "रेकॉर्ड संपादित करा",
		bSubmit: "सबमिट करा",
		bCancel: "रद्द करणे",
		bClose: "बंद करा",
		saveData: "डेटा बदलला आहे! बदल जतन करायचे?",
		bYes : "होय",
		bNo : "नाही",
		bExit : "रद्द करणे",
		msg: {
			required:"फील्ड आवश्यक आहे",
			number:"कृपया, एक वैध संख्या प्रविष्ट करा",
			minValue:"मूल्य मोठे किंवा त्यापेक्षा मोठे असणे आवश्यक आहे",
			maxValue:"मूल्य कमी किंवा समान असले पाहिजे",
			email: "वैध ईमेल नाही",
			integer: "कृपया, एक वैध पूर्णांक मूल्य प्रविष्ट करा",
			date: "कृपया वैध तारीख मूल्य प्रविष्ट करा",
			url: "वैध यूआरएल नाही. उपसर्ग आवश्यक आहे ('http://' or 'https://')",
			nodefined : "परिभाषित नाही",
			novalue : "रिटर्न व्हॅल्यू आवश्यक!",
			customarray : "सानुकूल फंक्शन अ‍ॅरे परत करणे आवश्यक आहे!",
			customfcheck : "सानुकूल तपासणीच्या बाबतीत सानुकूल कार्य उपस्थित असणे आवश्यक आहे!"
			
		}
	},
	view : {
		caption: "रेकॉर्ड पहा",
		bClose: "बंद करा"
	},
	del : {
		caption: "काढण्यासाठी",
		msg: "निवडलेली रेकॉर्ड हटवायची?",
		bSubmit: "काढण्यासाठी",
		bCancel: "रद्द करणे"
	},
	nav : {
		edittext: "",
		edittitle: "निवडलेली पंक्ती संपादित करा",
		addtext:"",
		addtitle: "नवीन पंक्ती जोडा",
		deltext: "",
		deltitle: "निवडलेली पंक्ती हटवा",
		searchtext: "",
		searchtitle: "रेकॉर्ड शोधा",
		refreshtext: "",
		refreshtitle: "ग्रीड रीलोड करा",
		alertcap: "चेतावणी",
		alerttext: "कृपया पंक्ती निवडा",
		viewtext: "",
		viewtitle: "निवडलेली पंक्ती पहा"
	},
	col : {
		caption: "स्तंभ निवडा",
		bSubmit: "ठीक आहे",
		bCancel: "रद्द करणे"
	},
	errors : {
		errcap : "त्रुटी",
		nourl : "कोणतीही यूआरएल सेट केलेली नाही",
		norecords: "प्रक्रियेसाठी कोणतीही नोंद नाही",
		model : "Length of colNames <> colModel!"
	},
	formatter : {
		integer : {thousandsSeparator: ",", defaultValue: '0'},
		number : {decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2, defaultValue: '0.00'},
		currency : {decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2, prefix: "", suffix:"", defaultValue: '0.00'},
		date : {
			dayNames:   [
				"Sun", "Mon", "Tue", "Wed", "Thr", "Fri", "Sat",
				"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
			],
			monthNames: [
				"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
				"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
			],
			AmPm : ["am","pm","AM","PM"],
			S: function (j) {return j < 11 || j > 13 ? ['st', 'nd', 'rd', 'th'][Math.min((j - 1) % 10, 3)] : 'th';},
			srcformat: 'Y-m-d',
			newformat: 'n/j/Y',
			parseRe : /[Tt\\\/:_;.,\t\s-]/,
			masks : {
				// see http://php.net/manual/en/function.date.php for PHP format used in jqGrid
				// and see http://docs.jquery.com/UI/Datepicker/formatDate
				// and https://github.com/jquery/globalize#dates for alternative formats used frequently
				// one can find on https://github.com/jquery/globalize/tree/master/lib/cultures many
				// information about date, time, numbers and currency formats used in different countries
				// one should just convert the information in PHP format
				ISO8601Long:"Y-m-d H:i:s",
				ISO8601Short:"Y-m-d",
				// short date:
				//    n - Numeric representation of a month, without leading zeros
				//    j - Day of the month without leading zeros
				//    Y - A full numeric representation of a year, 4 digits
				// example: 3/1/2012 which means 1 March 2012
				ShortDate: "n/j/Y", // in jQuery UI Datepicker: "M/d/yyyy"
				// long date:
				//    l - A full textual representation of the day of the week
				//    F - A full textual representation of a month
				//    d - Day of the month, 2 digits with leading zeros
				//    Y - A full numeric representation of a year, 4 digits
				LongDate: "l, F d, Y", // in jQuery UI Datepicker: "dddd, MMMM dd, yyyy"
				// long date with long time:
				//    l - A full textual representation of the day of the week
				//    F - A full textual representation of a month
				//    d - Day of the month, 2 digits with leading zeros
				//    Y - A full numeric representation of a year, 4 digits
				//    g - 12-hour format of an hour without leading zeros
				//    i - Minutes with leading zeros
				//    s - Seconds, with leading zeros
				//    A - Uppercase Ante meridiem and Post meridiem (AM or PM)
				FullDateTime: "l, F d, Y g:i:s A", // in jQuery UI Datepicker: "dddd, MMMM dd, yyyy h:mm:ss tt"
				// month day:
				//    F - A full textual representation of a month
				//    d - Day of the month, 2 digits with leading zeros
				MonthDay: "F d", // in jQuery UI Datepicker: "MMMM dd"
				// short time (without seconds)
				//    g - 12-hour format of an hour without leading zeros
				//    i - Minutes with leading zeros
				//    A - Uppercase Ante meridiem and Post meridiem (AM or PM)
				ShortTime: "g:i A", // in jQuery UI Datepicker: "h:mm tt"
				// long time (with seconds)
				//    g - 12-hour format of an hour without leading zeros
				//    i - Minutes with leading zeros
				//    s - Seconds, with leading zeros
				//    A - Uppercase Ante meridiem and Post meridiem (AM or PM)
				LongTime: "g:i:s A", // in jQuery UI Datepicker: "h:mm:ss tt"
				SortableDateTime: "Y-m-d\\TH:i:s",
				UniversalSortableDateTime: "Y-m-d H:i:sO",
				// month with year
				//    Y - A full numeric representation of a year, 4 digits
				//    F - A full textual representation of a month
				YearMonth: "F, Y" // in jQuery UI Datepicker: "MMMM, yyyy"
			},
			reformatAfterEdit : false
		},
		baseLinkUrl: '',
		showAction: '',
		target: '',
		checkbox : {disabled:true},
		idName : 'id'
	}
});
})(jQuery);
