--liquibase formatted sql
--changeset nilima:V20181004004110__AL_tb_csmr_info_03102018.sql
ALTER TABLE tb_csmr_info 
ADD COLUMN CS_SEWARAGE_ID VARCHAR(45) NULL AFTER CS_BCITY_NAME,
ADD COLUMN CS_REASON VARCHAR(200) NULL AFTER CS_SEWARAGE_ID,
ADD COLUMN CS_SERVICE_CHARGE DECIMAL(15,2) NULL AFTER CS_REASON;

--liquibase formatted sql
--changeset nilima:V20181004004110__AL_tb_csmr_info_031020181.sql
ALTER TABLE tb_csmr_info CHANGE COLUMN CS_CCNSTATUS CS_CCNSTATUS BIGINT(12) NULL DEFAULT NULL COMMENT 'CPD ID of connection status' ;

--liquibase formatted sql
--changeset nilima:V20181004004110__AL_tb_csmr_info_031020182.sql
ALTER TABLE tb_csmr_info 
ADD COLUMN CS_EMAIL VARCHAR(50) NULL COMMENT 'EMAIL' AFTER CS_BCITY_NAME,
ADD COLUMN CS_ESWATERREQ DECIMAL(10,2) NULL AFTER CS_EMAIL,
ADD COLUMN CS_DISCAP DECIMAL(10,2) NULL AFTER CS_ESWATERREQ,
ADD COLUMN LOC_ID BIGINT(12) NULL AFTER CS_DISCAP;

--liquibase formatted sql
--changeset nilima:V20181004004110__AL_tb_csmr_info_031020183.sql
drop table if exists tb_csmr_info_hist;

--liquibase formatted sql
--changeset nilima:V20181004004110__AL_tb_csmr_info_031020184.sql
CREATE TABLE tb_csmr_info_hist (
  H_CSIDN bigint(12) NOT NULL DEFAULT '0' COMMENT 'Primary Key ',
  CS_IDN bigint(12)  COMMENT 'Id no. Of the Consumer',
  CS_CCN varchar(40)  COMMENT 'Connection Code No.',
  CS_APLDATE datetime  COMMENT 'date of application for new water connection',
  CS_OLDCCN varchar(60)  COMMENT 'Old CCN',
  PM_PROP_NO varchar(20)  COMMENT 'ID OF TB_PROP_MAS TABLE',
  CS_TITLE varchar(30)  COMMENT 'Consumer Title',
  CS_NAME varchar(600)  COMMENT 'Name Of the Consumer',
  CS_MNAME varchar(600)  COMMENT 'Middle Name of Consumer',
  CS_LNAME varchar(600)  COMMENT 'Last name of consumer',
  CS_ORG_NAME varchar(200)  COMMENT 'organisation name',
  CS_ADD varchar(1000)  COMMENT 'Address of the consumer',
  CS_FLATNO varchar(20)  COMMENT 'Flat Number of consumer',
  CS_BLDPLT varchar(300)  COMMENT 'Building / Plot no. Of the Consumer',
  CS_LANEAR varchar(100)  COMMENT 'Lane / Area Of the Consumer',
  CS_RDCROSS varchar(200)  COMMENT 'Road Cross Of the Consumer',
  CS_CONTACTNO varchar(100)  COMMENT 'Contact number of consumer',
  CS_OTITLE varchar(30)  COMMENT 'Title of owner',
  CS_ONAME varchar(600)  COMMENT 'Owner name Of the Consumer',
  CS_OMNAME varchar(600)  COMMENT 'Middle name of owner',
  CS_OLNAME varchar(600)  COMMENT 'Lasr name of owner',
  CS_OORG_NAME varchar(200)  COMMENT 'Orgnaisation name for owner',
  CS_OADD varchar(1000)  COMMENT 'Address of property owner',
  CS_OFLATNO varchar(20)  COMMENT 'Flat number of property owner',
  CS_OBLDPLT varchar(300)  COMMENT 'Owner Building / Plot no. Of the Consumer',
  CS_OLANEAR varchar(100)  COMMENT 'Owner lane Of the Consumer',
  CS_ORDCROSS varchar(200)  COMMENT 'Owner Road Of the Consumer',
  CS_OCONTACTNO varchar(100)  COMMENT 'Contact number of property owner',
  CS_HOUSETYPE bigint(12)  COMMENT 'House type Of the Consumer',
  CS_CCNTYPE bigint(12)  COMMENT 'Connection type - Bulk or Semi - Bulk or Regular ID',
  CS_NOOFUSERS int(11)  COMMENT 'No of users Of in the family',
  CS_REMARK varchar(400)  COMMENT 'Remark for this consumer',
  TRD_PREMISE bigint(12)  COMMENT 'Premise type for Tariff group',
  CS_NOOFTAPS bigint(12)  COMMENT 'Tariff Group',
  CS_METEREDCCN bigint(12)  COMMENT 'Metered/Nonmeterd Flag WMN Perfix',
  PC_FLG varchar(2)  COMMENT 'Physical connection Flag',
  PC_DATE datetime  COMMENT 'Date of Physical connection',
  PLUM_ID bigint(12)  COMMENT 'ID of tb_wt_plum_mas',
  CS_CCNSTATUS varchar(2)  COMMENT 'CPD ID of connection status',
  CS_FROMDT datetime  COMMENT 'If connection is temprory then from date',
  CS_TODT datetime  COMMENT 'If connection is temprory then to date',
  ORGID bigint(12)  COMMENT 'Org ID',
  Created_By bigint(12)  COMMENT 'User ID',
  Created_date datetime  COMMENT 'Last Modification Date',
  UPDATED_BY bigint(12)  COMMENT 'User id who update the data',
  UPDATED_DATE datetime  COMMENT 'Date on which data is going to update',
  CS_PREMISEDESC varchar(500)  COMMENT 'Premises description',
  CS_BBLDPLT varchar(300)  COMMENT 'Billing address plot',
  CS_BLANEAR varchar(100)  COMMENT 'Billing address nearer to',
  CS_BRDCROSS varchar(200)  COMMENT 'Billing address road cross',
  CS_BADD varchar(1000)  COMMENT 'Billing address ',
  REGNO varchar(100)  COMMENT 'Registration Number',
  METERREADER bigint(12) ,
  PORTED char(1)  COMMENT 'Y-Data created thru Data entry form or Data Upload',
  ELECTORAL_WARD varchar(10)  COMMENT 'Electoral ward',
  CS_LISTATUS bigint(12)  COMMENT 'Connection Status',
  COD_DWZID1 bigint(12)  COMMENT 'Hierarchy for Ward, Zone',
  COD_DWZID2 bigint(12)  COMMENT 'Hierarchy for Ward, Zone',
  COD_DWZID3 bigint(12)  COMMENT 'Hierarchy for Ward, Zone',
  COD_DWZID4 bigint(12)  COMMENT 'Hierarchy for Ward, Zone',
  COD_DWZID5 bigint(12)  COMMENT 'Hierarchy for Ward, Zone',
  CS_POWNER char(1)  COMMENT 'Logical field',
  CPA_CSCID1 bigint(12)  COMMENT 'Connection owner address hierarchy',
  CPA_CSCID2 bigint(12)  COMMENT 'Connection owner address hierarchy',
  CPA_CSCID3 bigint(12)  COMMENT 'Connection owner address hierarchy',
  CPA_CSCID4 bigint(12)  COMMENT 'Connection owner address hierarchy',
  CPA_CSCID5 bigint(12)  COMMENT 'Connection owner address hierarchy',
  CPA_OCSCID1 bigint(12)  COMMENT 'Property Owner address hierarchy details',
  CPA_OCSCID2 bigint(12)  COMMENT 'Property Owner address hierarchy details',
  CPA_OCSCID3 bigint(12)  COMMENT 'Property Owner address hierarchy details',
  CPA_OCSCID4 bigint(12)  COMMENT 'Property Owner address hierarchy details',
  CPA_OCSCID5 bigint(12)  COMMENT 'Property Owner address hierarchy details',
  CPA_BCSCID1 bigint(12)  COMMENT 'Building address hierarchy details',
  CPA_BCSCID2 bigint(12)  COMMENT 'Building address hierarchy details',
  CPA_BCSCID3 bigint(12)  COMMENT 'Building address hierarchy details',
  CPA_BCSCID4 bigint(12)  COMMENT 'Building address hierarchy details',
  CPA_BCSCID5 bigint(12)  COMMENT 'Building address hierarchy details',
  TRM_GROUP1 bigint(12)  COMMENT 'Tariff group hierarchy',
  TRM_GROUP2 bigint(12)  COMMENT 'Tariff group hierarchy',
  TRM_GROUP3 bigint(12)  COMMENT 'Tariff group hierarchy',
  TRM_GROUP4 bigint(12)  COMMENT 'Tariff group hierarchy',
  TRM_GROUP5 bigint(12)  COMMENT 'Tariff group hierarchy',
  CS_CCNCATEGORY1 bigint(12)  COMMENT 'Connection Category hierarchy',
  CS_CCNCATEGORY2 bigint(12)  COMMENT 'Connection Category hierarchy',
  CS_CCNCATEGORY3 bigint(12)  COMMENT 'Connection Category hierarchy',
  CS_CCNCATEGORY4 bigint(12)  COMMENT 'Connection Category hierarchy',
  CS_CCNCATEGORY5 bigint(12)  COMMENT 'Connection Category hierarchy',
  LG_IP_MAC varchar(100)  COMMENT 'Client MachineÃ¢â¿¬â¿¢s Login Name | IP Address | Physical Address',
  LG_IP_MAC_UPD varchar(100)  COMMENT 'Updated Client MachineÃ¢â¿¬â¿¢s Login Name | IP Address | Physical Address',
  WT_V1 varchar(200)  COMMENT 'Additional nvarchar2 WT_V1 to be used in future',
  CS_CFC_WARD bigint(15)  COMMENT 'User Ward',
  WT_N2 bigint(15)  COMMENT 'Additional number WT_N2 to be used in future',
  WT_D1 datetime  COMMENT 'Additional Date WT_D1 to be used in future',
  WT_LO1 char(1)  COMMENT 'Additional Logical field WT_LO1 to be used in future',
  CS_BHANDWALI_FLAG char(1) ,
  CS_OLDPROPNO varchar(60)  COMMENT 'Old Property Number',
  CS_SEQNO bigint(12)  COMMENT 'Sequence Number',
  CS_ENTRY_FLAG varchar(1)  COMMENT 'D-Data created through Data entry form, U-Data created through upload',
  CS_OPEN_SECDEPOSIT_AMT bigint(12) ,
  CS_BULK_ENTRY_FLAG char(1)  COMMENT 'Indicates whether Connection created through Bulk Data Entry',
  GIS_REF varchar(40)  COMMENT 'Stores GIS reference (for GIS Web Services)',
  CS_UID bigint(12)  COMMENT 'For adhar number provision',
  APM_APPLICATION_ID bigint(16)  COMMENT 'Application Id',
  CS_P_T_FLAG varchar(1)  COMMENT 'Permanent or Temporary Connection flag',
  T_FROM_DATE datetime  COMMENT 'If Temporary flag active then enter from date',
  T_TO_DATE datetime  COMMENT 'If Temporary flag active then enter to date',
  BPL_FLAG varchar(1)  COMMENT 'Flag for to identify BPL Provision Applicable or not',
  BPL_NO varchar(20)  COMMENT 'BPL No. of Citizen',
  CS_CCNSIZE bigint(10)  COMMENT 'Connection Size',
  CS_NO_OF_FAMILIES int(11)  COMMENT 'No.of Families',
  CS_CPD_APT bigint(10)  COMMENT 'comes from APT prefix',
  CS_OGENDER bigint(10)  COMMENT 'GENDER OF OWNER GEN PREFIX',
  CS_IS_BILLING_ACTIVE varchar(1)  COMMENT 'Billing is Active or Not ',
  CS_BCITY_NAME varchar(200)  COMMENT 'Billing VILLAGE/TOWN/CITY NAME',
  CS_EMAIL varchar(50)  COMMENT 'EMAIL',
  CS_ESWATERREQ decimal(10,2) ,
  CS_DISCAP decimal(10,2) ,
  LOC_ID bigint(12) ,
  CS_SEWARAGE_ID varchar(45) ,
  CS_REASON varchar(200) ,
  CS_SERVICE_CHARGE decimal(15,2) ,
  H_STATUS varchar(1)  COMMENT 'History Status',
  PRIMARY KEY (H_CSIDN)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='This table stores Consumer Information.';

--liquibase formatted sql
--changeset nilima:V20181004004110__AL_tb_csmr_info_031020185.sql
ALTER TABLE tb_csmr_info 
CHANGE COLUMN APM_APPLICATION_ID APM_APPLICATION_ID BIGINT(16) NULL COMMENT 'Application Id' ;

--liquibase formatted sql
--changeset nilima:V20181004004110__AL_tb_csmr_info_031020186.sql
ALTER TABLE tb_csmr_info 
CHANGE COLUMN CS_APLDATE CS_APLDATE DATETIME NULL COMMENT 'date of application for new water connection' ,
CHANGE COLUMN CS_TITLE CS_TITLE VARCHAR(30) NULL COMMENT 'Consumer Title' ,
ADD COLUMN CS_PINCODE VARCHAR(45) NULL AFTER CS_SERVICE_CHARGE,
ADD COLUMN CS_BPINCODE VARCHAR(45) NULL AFTER CS_PINCODE;

