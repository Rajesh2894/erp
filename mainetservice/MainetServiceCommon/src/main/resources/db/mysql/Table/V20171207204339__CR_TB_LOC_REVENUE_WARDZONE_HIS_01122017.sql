--liquibase formatted sql
--changeset nilima:V20171207204339__CR_TB_LOC_REVENUE_WARDZONE_HIS_01122017.sql
Drop table if exists TB_LOCATION_REVENUE_WARDZONE_HIS ;

--liquibase formatted sql
--changeset nilima:V20171207204339__CR_TB_LOC_REVENUE_WARDZONE_HIS_011220171.sql
CREATE TABLE TB_LOC_REVENUE_WARDZONE_HIS(
  LOCRWZMP_ID_H bigint(12),
  LOCRWZMP_ID bigint(12),
  ORGID int(11) COMMENT 'Organisation id',
  LOC_ID bigint(12),
  COD_ID_REV_LEVEL1 bigint(12),
  COD_ID_REV_LEVEL2 bigint(12),
  COD_ID_REV_LEVEL3 bigint(12),
  COD_ID_REV_LEVEL4 bigint(12),
  COD_ID_REV_LEVEL5 bigint(12),
  CREATED_BY int(11) COMMENT 'User Identity',
  CREATED_DATE datetime COMMENT 'Last Modification Date',
  UPDATED_BY int(11) COMMENT 'User id who update the data',
  UPDATED_DATE datetime COMMENT 'Date on which data is going to update',
  LG_IP_MAC varchar(100) COMMENT 'Client Machine?s Login Name | IP Address | Physical Address',
  LG_IP_MAC_UPD varchar(100) COMMENT 'Updated Client Machine?s Login Name | IP Address | Physical Address',
  H_STATUS CHAR(1)) 
  ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='This table stores mapping Entry for Location and Revenue War';
