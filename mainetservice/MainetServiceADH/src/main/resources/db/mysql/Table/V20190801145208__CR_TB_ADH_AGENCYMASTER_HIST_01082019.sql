--liquibase formatted sql
--changeset Anil:V20190801145208__CR_TB_ADH_AGENCYMASTER_HIST_01082019.sql
drop table if exists TB_ADH_AGENCYMASTER_HIST;
--liquibase formatted sql
--changeset Anil:V20190801145208__CR_TB_ADH_AGENCYMASTER_HIST_010820191.sql
CREATE TABLE TB_ADH_AGENCYMASTER_HIST(
  AGN_ID bigint(12) NOT NULL COMMENT 'Primary key',
  AGN_LIC_NO varchar(40) NOT NULL COMMENT 'New License No',
  AGN_NAME varchar(200) NOT NULL COMMENT 'Agency Name',
  AGN_CATEGORY bigint(11) NOT NULL COMMENT 'Agency Category ID',
  AGN_OWNERS varchar(200) NOT NULL COMMENT 'Agency Owner Details',
  AGN_EMAIL varchar(50) NOT NULL COMMENT 'Email ID',
  PAN_NUMBER varchar(20) DEFAULT NULL COMMENT 'PAN',
  UID_NO bigint(12) DEFAULT NULL COMMENT 'Aadhar No',
  GST_NO bigint(15) DEFAULT NULL COMMENT 'GST No',
  AGN_CONTACT_NO varchar(40) NOT NULL COMMENT 'Mobile No',
  AGN_ADD varchar(400) NOT NULL COMMENT 'Address',
  AGN_LIC_ISSUE_DATE date DEFAULT NULL COMMENT 'Registration date',
  AGN_LIC_FROM_DATE date DEFAULT NULL COMMENT 'From Date',
  AGN_LIC_TO_DATE date DEFAULT NULL COMMENT 'To Date',
  AGN_REMARK varchar(400) DEFAULT NULL COMMENT 'Remarks',
  AGN_OLD_LIC_NO varchar(40) DEFAULT NULL COMMENT 'Old Legacy license number',
  APM_APPLICATION_ID bigint(16) DEFAULT NULL COMMENT 'Service application reference ID',
  AGN_STATUS char(1) NOT NULL COMMENT 'O-Open/C-Close',
  ORGID bigint(12) NOT NULL COMMENT 'Organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'User Identity',
  CREATED_DATE datetime NOT NULL COMMENT 'Last Modification Date',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'Client Machine Login Name|IP Address|Physical Address',
  LANG_ID bigint(12) NOT NULL COMMENT 'Language Identity',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'Updated User Identity',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'Updated Modification Date',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'Updated Client Machine Login Name|IP Address|Physical Address',
  PRIMARY KEY (AGN_ID)
  )ENGINE=InnoDB DEFAULT CHARSET=utf8;
