--liquibase formatted sql
--changeset nilima:V20181126133009__CR_TB_AS_TRY_26112018.sql
CREATE TABLE TB_AS_TRY (
  TRY_ID bigint(12) NOT NULL,
  TRY_DIS_NAME varchar(200)  COMMENT 'District Name',
  TRY_DIS_CODE varchar(50)  COMMENT 'District Code',
  TRY_TEHSIL_NAME varchar(200)  COMMENT 'Tehsil Name',
  TRY_TEHSIL_CODE varchar(50)  COMMENT 'Tehsil code',
  TRY_VILL_NAME varchar(200)  COMMENT 'Village Name',
  TRY_VILL_CODE varchar(50)  COMMENT 'Village Code',
  TRY_HALKA_NAME varchar(200)  COMMENT 'Halka Name',
  TRY_HALKA_CODE varchar(50)  COMMENT 'Halka Code',
  TRY_RI_NAME varchar(200)  COMMENT 'RI Name',
  TRY_RI_CODE varchar(50)  COMMENT 'RI Code',
  TRY_WARD_NAME varchar(200) ,
  TRY_WARD_CODE varchar(50) ,
  TRY_SHEET_ID varchar(50)  COMMENT 'Sheet Id',
  TRY_SHEET_NO varchar(50)  COMMENT 'Sheet No',
  TRY_RECORDCODE varchar(50)  COMMENT 'Record Code',
  TRY_PROP_TYPE varchar(50)  COMMENT 'Property Type',
  PRIMARY KEY (TRY_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
