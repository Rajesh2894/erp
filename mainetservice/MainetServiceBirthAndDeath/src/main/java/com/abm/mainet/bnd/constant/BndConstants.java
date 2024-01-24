package com.abm.mainet.bnd.constant;

public interface BndConstants {
        String BND_RATE_MASTER = "BNDRateMaster";

//      String BND_RATE_MASTER_URL ="http://192.168.100.33:8090/mainetbpmservice/rest/bnd/getBNDCharge";       // local url
       // String BND_RATE_MASTER_URL ="http://103.209.145.36:8089/mainetbpmservice/rest/bnd/getBNDCharge";

        //String BND_RATE_MASTER_URL ="http://192.168.100.33:8090/mainetbpmservice/rest/bnd/getBNDCharge";
        String BND_RATE_MASTER_URL ="http://103.209.145.36:8089/mainetbpmservice/rest/bnd/getBNDCharge";

        String IBC = "IBC"; //Issuance of birth certificates
        String IDC = "IDC";  //Issuance of Death certificate
        String 	DRC="DRC";   //Death Registration correction
        String INC="INC" ;//inclusion of child 
        String BR="BRG";  //birth Registration
        String DR ="DR"; //death registration
        String STATUS="O";
        String BRI="BRI"; //
        String BRC="BRC";
        String BNDCHARGES="certificateCharge";
        String CAA = "CAA";
        String BIRTH_DEATH = "BND";
        String BIRT_REPORT_URL="http://192.168.100.33:8090/birt/frameset?__report";
        String CHARGES_AMOUNT="0.0";
        String CHARGES_AMOUNT_FLG="N";
        String 	ROLE_CODE="BND_APPROVER";
        String  DESIGN_PRIFIX="DLO";
        String REJECTED="REJECTED";
        String APPROVED="APPROVED";
        String PENDING="PENDING";
        String CLOSED="CLOSED";
        String OPEN="OPEN";
        String BIRTH_REG_URL_FOR_WORLFLOW_INIT="BirthRegistrationForm.html";
        String ISSUANCE_BIRTH_URL_FOR_WORLFLOW_INIT="IssuanceBirthCertificate.html";
        String ISSUANCE_DEATH_URL_FOR_WORLFLOW_INIT="IssuanceDeathCertificate.html";
        String DEATH_REG_URL_FOR_WORLFLOW_INIT="DeathRegistration.html";
        String BIRTH_STATUS_APPROVED="A";
        String DEATH_STATUS_APPROVED="Y";
        String CHARGES_APPLY_STATUS="CA";
        String STRART_DATE="01/01/";
        String TO_DATE="31/12/";
        String FLAT_RATE="flatRate";
        String NBR="NBR";
		String NDR="NDR";		
        public enum ServiceDesc {
        	DEATH_REG("Death Registration Service"),
        	DEATH_CORR("Death Correction Service"),
        	BIRTH_REG("Birth Registration Service"),
        	BIRTH_CORR("Birth Correction Service"),
            BIRTH_ISSUE("Issuence of Birth Certificate"),
            DEATH_ISSUE("Issuence of Death Certificate"),
            CHILD_INCL("Child Inclusion Service");
        	
        	private String serviceDesc;

        	ServiceDesc(String serviceDesc) {
                this.serviceDesc = serviceDesc;
            }

            public String getServiceDesc() {
                return serviceDesc;
            }
        }
        
        
       // String ROLE_CODE = "RTS_APPROVER";
    	
    	String RTS = "BND";
    	String RDC = "ADC";
    	String BNDRateMaster = "BNDRateMaster";
    	String ChecklistModel = "ChecklistModel";
    	String GeneralMessage = "GM";
    	String APPLYFORDEATHCERTIFICATE = "applyForDeathCertificates.html";
    	String APPLY_FOR_DEATHCERTIFICATE_SERVICE = "Apply Death Certificates";
    	String RBC = "ABC";//Issuance of birth certificates
    	String APPLY_FOR_BIRTH_CERTIFICATE = "ApplyforBirthCertificates.html";
    	String APPLY_FOR_BIRTH_CERTIFICATE_SERVICE = "Apply For  Birth Certificate";
    	String CHARGESAPPLICABLE = "CA";
    	String CHECKLISTAPPLICABLE = "A";
    	String APMAPPLICATIOREJFLAG = "R";
    	String APMAPPLICATIONCLOSEDFLAG = "C";
    	String APMAPPLICATIONPENDINGFLAG = "P";
    	String APMAPPLICATIONCLOSEDOPENFLAG = "O";
    	String FLAGF = "F";
    	String PAYSTSTUSFREE = "F";
    	String PAYSTATUSCHARGE = "Y";
    	String STATUSOPEN = "O";
    	String BIRTH_CERTIFICATE = "BndBirthCertificate";
    	String BRSTATUSAPPROVED = "A";
    	String BIRTHCERTIFICATEAPPROVAL = "BirthCertificateApprovals.html";
    	String BIRTH_CERTIFICATE_APPROVAL = "BndBirthCertificateApproval";
    	String BIRTHWFSTATUS = "BirthWfStatus";
    	String DEATH_CERTIFICATE = "BndDeathCertificate";
    	String DEATHAPPROVAL_URL = "ApplyDeathCertificateLevels.html";
    	String DEATHAPPROVAL = "DeathCertificateApproval";
    	String VIEWMODE = "V";
    	String APPLY_DRAINAGE_CONNECTION = "Apply For  Drainage  Connection";
    	String BIRTH_CERTIFICATE_FORMNAME = "Birth Certificate";
    	String DEATH_CERTIFICATE_FORMNAME = "Death Certificate";
		String CHECKLIST_MODEL="ChecklistModel";
		String NBR_SERVICE="NAC for Birth Registration";
		String NDR_SERVICE="NAC for Death Registration";
		String NON_AVAIL_CERT="NON-AVAILABILITY CERTIFICATE";
    	String DRAINAGE_CONNECTION_FORMNAME = "Drainage Connection";
    	String DRN_ACK_PAGE = "BndrtsAcknowldgeMentStatusForm";
        String BND = "BND";
        String TB_BD_CERT_COPY ="TB_BD_CERT_COPY";
        String CERT_NO ="CERT_NO";
        String TB_BIRTHREG ="TB_BIRTHREG";
        String BR_CERT_NO ="BR_CERT_NO";
        String TB_DEATHREG ="tb_deathreg";
        String DR_CERT_NO ="DR_CERT_NO";
        String DEATH_REG_CORR_URL = "DeathRegistrationCorrection.html";
        String BIRTH_REG_CORR_URL = "BirthCorrectionForm.html";
        String INCLUSION_CHILD_URL = "InclusionOfChildName.html";
        String DEATH_CORR_APPR_URL = "DeathRegistrationCorrectionApproval.html";
        String BIRTH_CORR_APPR_URL = "BirthRegistrationCorrectionApproval.html";
        String ISSUE_DEATH_APPR_URL = "IssuanceDeathCertificateApproval.html";
        String ISSUE_BIRTH_APPR_URL = "IssuanceBirthCertificateApproval.html";
        String INCLUSION_OF_CHILD_APPR_URL = "InclusionOfChildNameApproval.html";
        String NAC_BIRTHREG = "NacForBirthReg.html";
        String NAC_DEATH_REG = "NacForDeathReg.html";
		String DED = "DED"; //data entry for death registration
		String DEB = "DEB"; //data entry for birth registration
}
