--liquibase formatted sql
--changeset ajit:V20180221193943__CR_1_RTI_APPLICATION_MAS_MYSQL.sql
drop table IF EXISTS tb_rti_application; 

--changeset ajit:V20180221193943__CR_1_RTI_APPLICATION_MAS_MYSQL1.sql
 CREATE TABLE tb_rti_application
 (RTI_ID Bigint 
  COMMENT 'RTI ID Primary Key' ,
  RTI_NO VARCHAR(20) 
  COMMENT 'RTI NO',
  APM_APPLICATION_ID Bigint(16) 
  COMMENT 'Application Id',
  APM_APPLICATION_DATE DATETIME 
  COMMENT 'Application Date',
  SM_SERVICE_ID bigint 
  COMMENT 'Service Id',
  ORGID bigint NOT NULL 
  COMMENT 'Organisation Id',
  APPL_REFERENCE_MODE Int 
  COMMENT 'Application Reference Mode Direct,Form E, Stamp',
  RTI_DEPTID Int 
  COMMENT 'Department Id',
  RTI_LOCATION_ID Int 
  COMMENT 'Location Id',
  RTI_SUBJECT VARCHAR(500) 
  COMMENT 'RTI SUBJECT',
  RTI_DETAILS VARCHAR(2000) 
  COMMENT 'RTI Details',
  LOIAPPLICABLE CHAR(1) 
  COMMENT 'flag to maintain loi applicable',
  REASON_FOR_LOI_NA VARCHAR(1000) 
  COMMENT 'reason coulumn if loi is not applicable',
  FINALDISPATCHMODE Int 
  COMMENT 'Final dispatch mode',
  DISPATCHDATE DATETIME 
  COMMENT 'Dispatch Date',
  STAMP_NO VARCHAR(50) 
  COMMENT 'Stamp No.',
  STAMP_AMT DOUBLE 
  COMMENT 'Stamp Amount',
  STAMP_DOC_PATH VARCHAR(500) 
  COMMENT 'Stamp Doc Path',
  RTI_ACTION Int 
  COMMENT 'Status information from RRS prefix',
  REASON_ID Int 
  COMMENT 'Foreign reference from common reason master',
  RTI_REMARK NVARCHAR(2000) 
  COMMENT 'Remark',
  PARTIAL_INFO_FLAG CHAR(1) 
  COMMENT 'flag for maintianing full or partial provided information',
  INWARD_TYPE Int 
  COMMENT 'Inward Type Ex. Form E',
  INW_REFERENCE_NO NVARCHAR(100) 
  COMMENT 'Inward Reference No. In case of FORM E',
  INW_REFERENCE_DATE DATETIME 
  COMMENT 'Inward Reference Date',
  INW_AUTHORITY_DEPT NVARCHAR(100) 
  COMMENT 'Inward Reference Authority Department in case of FORM E',
  INW_AUTHORITY_NAME NVARCHAR(100) 
  COMMENT 'Inward - name of the applicant authority- In case of FORM E',
  INW_AUTHORITY_ADDRESS NVARCHAR(1000) 
  COMMENT 'Inward - address of the applicant authority- In case of FORM E',
  OTH_FORWARD_PIO_ADDRESS NVARCHAR(1000) 
  COMMENT 'Forwarded To Other Organisation PIO Address',
  OTH_FORWARD_PIO_NAME NVARCHAR(100) 
  COMMENT 'Forwarded To Other Organisation - PIO Name',
  OTH_FORWARD_DEPT_NAME NVARCHAR(100) 
  COMMENT 'Forwarded To Other Organisation - Department Name',
  OTH_FORWARD_PIO_EMAIL NVARCHAR(100) 
  COMMENT 'Forwarded To Other Organisation - PIO Email Id',
  OTH_FORWARD_PIO_MOB_NO Int 
  COMMENT 'Forwarded To Other Organisation - PIO Email Id',
  LANG_ID Int NOT NULL 
  COMMENT 'Language Id',
  USER_ID Bigint NOT NULL 
  COMMENT 'User Id',
  LMODDATE DATETIME NOT NULL 
  COMMENT 'Entry Date',
  LG_IP_MAC VARCHAR(100) 
  COMMENT 'Client Machines Login Name | IP Address | Physical Address',
  UPDATED_BY Bigint 
  COMMENT 'Last Updated By',
  UPDATED_DATE DATETIME 
  COMMENT 'Last Updated Date',
  LG_IP_MAC_UPD VARCHAR(100) 
  COMMENT 'last Updated Client Machines Login Name | IP Address | Physical Address',
  ISDELETED Int 
  COMMENT 'Flag to identify whether the record is deleted or not. 1 for deleted (Inactive) and 0 for not deleted (Active) record');

--changeset ajit:V20180221193943__CR_1_RTI_APPLICATION_MAS_MYSQL2.sql
CREATE INDEX `IX_RTI_NO` ON `tb_rti_application` (`RTI_NO`);

--changeset ajit:V20180221193943__CR_1_RTI_APPLICATION_MAS_MYSQL3.sql
ALTER TABLE `tb_rti_application` ADD CONSTRAINT PK_RTI_APP_MAS_RTI_ID PRIMARY KEY (`RTI_ID`);

--changeset ajit:V20180221193943__CR_1_RTI_APPLICATION_MAS_MYSQL4.sql
ALTER TABLE `tb_rti_application` ADD CONSTRAINT FK_RTI_APPLICATION_ID_APP_ID FOREIGN KEY (`APM_APPLICATION_ID`) REFERENCES tb_cfc_application_mst (`APM_APPLICATION_ID`);
