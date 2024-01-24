--liquibase formatted sql
--changeset nilima:V20181211180553__AL_tb_wms_scheme_mast_07122018.sql
ALTER TABLE tb_wms_scheme_mast
ADD COLUMN SCH_SCODID2 BIGINT(12) NULL DEFAULT NULL COMMENT 'SchemeCodeid' ,
ADD COLUMN SCH_SCODID1 BIGINT(12) NULL AFTER `SCH_CODE`;

--liquibase formatted sql
--changeset nilima:V20181211180553__AL_tb_wms_scheme_mast_071220181.sql
drop table if exists tb_wms_scheme_mast_hist;

--liquibase formatted sql
--changeset nilima:V20181211180553__AL_tb_wms_scheme_mast_071220182.sql
CREATE TABLE tb_wms_scheme_mast_hist (
  SCH_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  SCH_ID bigint(12)  COMMENT 'Primary Key',
  SCH_CODE varchar(10)  COMMENT 'Scheme Code',
  SCH_NAME_ENG varchar(250)  COMMENT 'Scheme Name in English',
  SCH_NAME_REG varchar(250)  COMMENT 'Scheme Name in Regional',
  SCH_DESCRIPTION varchar(1000)  COMMENT 'Scheme Description',
  SCH_START_DATE date  COMMENT 'Scheme Start Date',
  SCH_END_DATE date  COMMENT 'Scheme End Date',
  SCH_FUND bigint(12)  COMMENT 'Scheme fund',
  SCH_FUNDNAME varchar(50) ,
  SCH_PROJECTED_REVENU decimal(15,2)  COMMENT 'Scheme Projected Revenu',
  SCH_ACTIVE char(1) ,
  H_STATUS char(1) ,
  ORGID bigint(12)  COMMENT 'organization id',
  CREATED_BY bigint(12)  COMMENT 'user id who created the record',
  CREATED_DATE date  COMMENT 'record creation date',
  UPDATED_BY bigint(12)  COMMENT 'user id who updated the record',
  UPDATED_DATE datetime  COMMENT 'user id who updated the record',
  LG_IP_MAC varchar(100)  COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100)  COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (`SCH_ID_H`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Scheme Master History';


--liquibase formatted sql
--changeset nilima:V20181211180553__AL_tb_wms_scheme_mast_071220183.sql
ALTER TABLE tb_wms_scheme_mast_hist
ADD COLUMN SCH_SCODID2 BIGINT(12) NULL DEFAULT NULL COMMENT 'SchemeCodeid' ,
ADD COLUMN SCH_SCODID1 BIGINT(12) NULL AFTER `SCH_CODE`;



