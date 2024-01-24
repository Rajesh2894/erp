--liquibase formatted sql
--changeset Anil:V20191205122351__CR_tb_adh_mas_hist_05122019.sql
drop table if exists tb_adh_mas_hist;
--liquibase formatted sql
--changeset Anil:V20191205122351__CR_tb_adh_mas_hist_051220191.sql
CREATE TABLE tb_adh_mas_hist (
  ADH_ID_H bigint(12) NOT NULL COMMENT 'Primary key',
  ADH_ID bigint(12) NOT NULL COMMENT 'Id',
  APM_APPLICATION_ID bigint(16) DEFAULT NULL COMMENT 'Application Number',
  APLCNT_CATID bigint(12) NOT NULL COMMENT 'Citizen/Agency',
  AGN_ID bigint(12) NOT NULL COMMENT 'Agency ID/Citizen ID',
  LOC_CATID char(1) NOT NULL COMMENT 'Existing Location-E/New Location-N',
  ADH_LICTYPE bigint(12) NOT NULL COMMENT 'Permanent/Temporary',
  ADH_LICNO varchar(50) DEFAULT NULL COMMENT 'New license number',
  ADH_OLDLICNO varchar(100) DEFAULT NULL COMMENT 'legacy system license number',
  ADH_LICFROM_DATE date DEFAULT NULL COMMENT 'License from date',
  ADH_LICTO_DATE date DEFAULT NULL COMMENT 'License to date',
  ADH_LICISDATE date DEFAULT NULL COMMENT 'license issue date',
  ADH_LOCID bigint(12) NOT NULL COMMENT 'location id',
  ADH_ZONE1 bigint(12) NOT NULL COMMENT 'Prefix id',
  ADH_ZONE2 bigint(12) DEFAULT NULL COMMENT 'Prefix id',
  ADH_ZONE3 bigint(12) DEFAULT NULL COMMENT 'Prefix id',
  ADH_ZONE4 bigint(12) DEFAULT NULL COMMENT 'Prefix id',
  ADH_ZONE5 bigint(12) DEFAULT NULL COMMENT 'Prefix id',
  PROPERTY_TYPID bigint(12) NOT NULL COMMENT 'Private/Govt',
  PROPERTY_ID varchar(20) DEFAULT NULL COMMENT 'Property No',
  TRD_LIC_NO varchar(50) DEFAULT NULL COMMENT 'Trade/Shop license No',
  PT_OWNER_NAME varchar(100) DEFAULT NULL COMMENT 'Property owner name',
  ADH_STATUS char(1) NOT NULL COMMENT 'ADH status- A-Active,T-Terminate,C-Closed',
  ORGID bigint(12) NOT NULL COMMENT 'Organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'User Identity',
  CREATED_DATE datetime NOT NULL COMMENT 'Last Modification Date',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'Client Machine Login Name|IP Address|Physical Address',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'Updated User Identity',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'Updated Modification Date',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'Updated Client Machine Login Name|IP Address|Physical Address',
  H_STATUS char(1) DEFAULT NULL,
  PRIMARY KEY (ADH_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
