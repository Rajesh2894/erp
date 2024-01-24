package com.abm.mainet.vehiclemanagement.Constants;

public interface Constants {
	String SHORT_CODE = "VM";
	String OPEN_STATUS = "O";
	String OPEN ="OPEN";
	String ZERO = "0";
	String DRAFT_STATUS = "D";
	String VEHICLE_DRPT_CODE="VM";
	String FEUL_CALL_REG_TABLE="tb_fm_petrol_requisition";
	String FEUL_CALL_REG_TABLE_COLUMN="REQ_NO";
	String ROLE_CODE = null;
	String DESIGN_PRIFIX = null;
	String FUEL_SERVICE_CODE="FRF";
	String str="_";
	String EDIT_VEHICLESCHEDULING = "EditVehicleScheduling";
		
	// vehicle mgmt Maintenance
	String MAINT_SERVICE_CODE="MOV";
	String VEHICLE_MAINT_DRPT_CODE="MNT";
	String VEHICLE_MAINT_CATEGORY="MNC";
	String MAINT_CALL_REG_TABLE="TB_VM_VEREMEN_MAST";
	String MOV ="MOV";
	String MAINT_BY_AMC ="AMC";
	String MAINT_BY_WORKSHOP ="WRK";
	String SEQ_WORKSHOP ="WS";
	String MAINT_INSP_APPR_URL = "vehicleMaintenanceInspectionApproval.html";
	String VEHICLE_MAINT_MOBILES_EMAILS = "VME";
	
	
	// vehicle mgmt oem warranty
	
	
	String OEMWarranty_FORM = "OEMWarrantyForm";
	String ADD_OEMWARRANTY= "AddOEMWarrenty";
	
	String ADD_InsuranceDetails= "AddInsuranceDetailsForm";
	String InsuranceDetails_FORM = "InsuranceDetailForm";
	
	//vehicle mgmt oem warranty
	
	
	
	int LEVEL_ONE = 1;
    int LEVEL_TWO = 2;
    String PROJECT_ADD = "P";
    String ADD = "A";
    String EDIT = "E";
    String CREATE = "C";
    String VIEW = "V";
    String DELETE = "D";
    String VMC = "VMC";
    String CHECKLIST_SWMRATEMASTER = "ChecklistModel|SWMRateMaster";
    String CHECK_LIST_MODEL = "ChecklistModel";
    String SWMRATEMASTER_MODEL = "SWMRateMaster";
    String DEPT_WASTE_COLLECTOR_SHORT_CODE = "CDG";
    // String TAX_CODE_WASTE_COLLECTOR_SHORT_CODE = "SWM1";
    String SW_TABLE = "SW";
    // solid_waste_management_vehicle

    String OEMWARRANTYFORM_FORM = "VehicleSchedulingForm";
    String VIEW_OEMWARRANTYFORM_FORM = "viewWarrantyForm";
    String EDIT_OEMWARRANTYFORM_FORM="editWarrantyForm";
    
    String VEHICLESCHEDULING_FORM = "VehicleScheduleForm";
    String VEHICLESCHEDULING_GRID = "VehicleSchGrid";
    String VEHICLESCHEDULING_SEARCH = "VehicleScheduleSearch";
    String ADD_VEHICLESCHEDULING = "AddVehicleScheduling";
    String VIEW_VEHICLESCHEDULING = "ViewVehicleScheduling";
    String DELETE_VEHICLESCHEDULING = "DeleteVehicleScheduling";
    String SEARCH_VEHICLESCHEDULING = "SearchVehicleScheduling";
    String DELETE_VEHICLESCHEDULING_DET = "DeleteVehicleSchedulingDet";
    String WASTETYPE = "WTY";
    String WETWASTE = "WW";
    String BULK_DELETE = "BulkDelete";
    interface SaveMode {
        String EDIT = "E";
        String CREATE = "C";
        String VIEW = "V";
        String DELETE = "D";
        String SEARCH = "S";
    }
    String VECH_MASTER = "VehMaster";
    String LOG_BOOK = "LogBook";
}
