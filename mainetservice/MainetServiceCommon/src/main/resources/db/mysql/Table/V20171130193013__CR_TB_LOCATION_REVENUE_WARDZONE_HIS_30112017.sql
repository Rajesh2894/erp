--liquibase formatted sql
--changeset nilima:V20171130193013__CR_TB_LOCATION_REVENUE_WARDZONE_HIS_30112017.sql
Drop Table if exists tb_location_revenue_wardzone_hist;

--liquibase formatted sql
--changeset nilima:V20171130193013__CR_TB_LOCATION_REVENUE_WARDZONE_HIS_301120171.sql
CREATE TABLE tb_location_revenue_wardzone_his (
  LOCRWZMP_ID_H bigint(12) DEFAULT NULL,
  LOCRWZMP_ID bigint(12) DEFAULT NULL,
  ORGID int(11) DEFAULT NULL COMMENT 'Organisation id',
  LOC_ID bigint(12) DEFAULT NULL,
  COD_ID_REV_LEVEL1 bigint(12) DEFAULT NULL,
  COD_ID_REV_LEVEL2 bigint(12) DEFAULT NULL,
  COD_ID_REV_LEVEL3 bigint(12) DEFAULT NULL,
  COD_ID_REV_LEVEL4 bigint(12) DEFAULT NULL,
  COD_ID_REV_LEVEL5 bigint(12) DEFAULT NULL,
  CREATED_BY int(11) DEFAULT NULL COMMENT 'User Identity',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'Last Modification Date',
  UPDATED_BY int(11) DEFAULT NULL COMMENT 'User id who update the data',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'Date on which data is going to update',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'Client Machine?s Login Name | IP Address | Physical Address',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'Updated Client Machine?s Login Name | IP Address | Physical Address',
  H_STATUS char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='This table stores mapping Entry for Location and Revenue War';