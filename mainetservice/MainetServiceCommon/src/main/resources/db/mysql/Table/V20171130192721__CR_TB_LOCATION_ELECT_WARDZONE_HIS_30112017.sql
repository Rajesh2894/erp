--liquibase formatted sql
--changeset nilima:V20171130192721__CR_TB_LOCATION_ELECT_WARDZONE_HIS_30112017.sql
Drop table if exists tb_location_elect_wardzone_hist;

--liquibase formatted sql
--changeset nilima:V20171130192721__CR_TB_LOCATION_ELECT_WARDZONE_HIS_301120171.sql
CREATE TABLE tb_location_elect_wardzone_his (
  LOCEWZMP_ID_H bigint(12) DEFAULT NULL,
  LOCEWZMP_ID bigint(12) DEFAULT NULL,
  ORGID int(11) DEFAULT NULL,
  LOC_ID bigint(12) DEFAULT NULL,
  COD_ID_ELEC_LEVEL1 bigint(12) DEFAULT NULL,
  COD_ID_ELEC_LEVEL2 bigint(12) DEFAULT NULL,
  COD_ID_ELEC_LEVEL3 bigint(12) DEFAULT NULL,
  COD_ID_ELEC_LEVEL4 bigint(12) DEFAULT NULL,
  COD_ID_ELEC_LEVEL5 bigint(12) DEFAULT NULL,
  CREATED_BY int(11) DEFAULT NULL COMMENT 'User Identity',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'Last Modification Date',
  UPDATED_BY int(11) DEFAULT NULL COMMENT 'User id who update the data',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'Date on which data is going to update',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'Client Machine?s Login Name | IP Address | Physical Address',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'Updated Client Machine?s Login Name | IP Address | Physical Address',
  H_STATUS char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='This table stores mapping Entry for Location and Electoral W';




