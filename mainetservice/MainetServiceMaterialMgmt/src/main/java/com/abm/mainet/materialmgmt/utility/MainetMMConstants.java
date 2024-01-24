package com.abm.mainet.materialmgmt.utility;

public interface MainetMMConstants {

interface MmItemMaster {
    public String MM_ITEMCODE = "MM_ITEMCODE";
    public String MM_ITEMMASTER = "MM_ITEMMASTER";
    public String ITM = "ITM";
    public String KEY_TEST = "keyTest";
    public String ITEM_NO = "Item No: ";
    public String RECORD_SAVE_SUCCESS = " has been saved successfully";
    public String RECORD_UPDATE_SUCCESS = " has been updated successfully";
    public String FALSE = "false";
    public String MODVIEW = "modeView";
    public String TRUE = "true";
    public String MODE = "MODE_DATA";
	public String MM_STOREMASTER = "MM_STOREMASTER";
    public String MM_STORECODE = "MM_STORECODE";
    public String MM_PURREQTABLE = "MM_REQUISITION";
    public String FYR="2122";
    public String STR="STR";
    public String ITP = "ITP";
    public String ITEM_MASTER_EXCEL_NAME="ItemMaster";
    public String ENTRY_FLAG_CREATE="M";
    public String ENTRY_FLAG_EXCEL_UPLOAD="U";
    public String ACTIVE_STATUS="A";
    public String ACTIVE_INACTIVE_STATUS_PREFIX = "ACN";
    public String ITEM_GROUP_PREFIX="ITG";
    public String ITEM_CATEGORY_PREFIX="ICT";
    public String ITEM_TYPE_PREFIX="ITP";
    public String ITEM_VALUATION_PREFIX="IVM";
    public String ITEM_MANAGEMENT_PREFIX="IMM";
    public String ITEM_CLASSIFICATION_PREFIX="ASC";
    public String ITEM_EXPIRY_TYPE_PREFIX="EXP";
    public String UOM_PREFIX="UOM";
    public String ASSET="Y";
    public String EXPIRY="Y";
    public int LEVEL1=1;
    public int LEVEL2=2;
    public String MAIN_ENTITY_NAME = "tbMGItemMaster";
    public String ITEM_MASTER = "itemMasterBean";
    String MODE_CREATE = "create";
    String MODE_UPDATE = "update";
    public String IND = "IND";
    public String MM_PURCHASEORDER = "MM_PURCHASEORDER";

}

interface BIN_MASTER{
	public String BD = "BD";
	public String BL = "BL";
	public String V = "V";
}

interface ITEM_MASTER_TEMPLATE {
   
    String ITEM_MASTER_TEMPLATE = "itemMasterTemplate";
   
}
}