--liquibase formatted sql
--changeset Kanchan:V20210115164939__CR_tb_as_assesment_detail_hist_15012021.sql
CREATE TABLE tb_as_assesment_detail_hist (
  MN_assd_his_id bigint(12) NOT NULL,
  MN_assd_id bigint(12) NOT NULL,
  MN_ass_id bigint(12) NOT NULL,
  MN_ASSD_UNIT_TYPE_ID bigint(20) DEFAULT NULL COMMENT '	UNIT TYPE	',
  MN_assd_floor_no bigint(3) NOT NULL,
  MN_assd_buildup_area decimal(15,2) NOT NULL,
  MN_assd_usagetype1 bigint(12) DEFAULT NULL,
  MN_assd_usagetype2 bigint(12) DEFAULT NULL,
  MN_assd_usagetype3 bigint(12) DEFAULT NULL,
  MN_assd_usagetype4 bigint(12) DEFAULT NULL,
  MN_assd_usagetype5 bigint(12) DEFAULT NULL,
  MN_assd_constru_type bigint(12) NOT NULL,
  MN_assd_year_construction datetime NOT NULL,
  MN_assd_occupancy_type bigint(12) NOT NULL,
  MN_assd_assesment_date datetime NOT NULL,
  MN_assd_annual_rent decimal(15,2) DEFAULT NULL,
  MN_assd_std_rate decimal(15,2) DEFAULT NULL,
  MN_assd_alv decimal(15,2) DEFAULT NULL,
  MN_assd_rv decimal(15,2) DEFAULT NULL,
  MN_assd_cv decimal(15,2) DEFAULT NULL,
  MN_assd_active char(1) NOT NULL DEFAULT 'Y',
  MN_assd_road_factor bigint(12) DEFAULT NULL COMMENT '	ROAD FACTOR	',
  MN_MAINTAINCE_CHARGE decimal(15,2) DEFAULT NULL COMMENT '	UNIT TYPE	',
  MN_assd_unit_no bigint(12) DEFAULT NULL COMMENT '	UNIT NUMBER	',
  MN_assd_occupier_name varchar(500) DEFAULT NULL COMMENT '	occupier name	',
  MN_assd_monthly_rent decimal(15,2) DEFAULT NULL COMMENT '	Monthly Rent	',
  MN_PARENT_PROP varchar(20) DEFAULT NULL COMMENT '	PROPERTY ID OF PARENT PROPERTY IN CASE OF NEW PROPERTY CREATED DUE TO MUTATION OR BIFERCATION	',
  BASERATE varchar(500) DEFAULT NULL COMMENT ' BASERATE ',
  RULEID varchar(1000) DEFAULT NULL COMMENT ' RULEID ',
  natureOfProperty1 mediumtext COMMENT '--  X-- -',
  natureOfProperty2 mediumtext COMMENT '--  X-- -',
  natureOfProperty3 mediumtext COMMENT '--  X-- -',
  natureOfProperty4 mediumtext COMMENT '--  X-- -',
  natureOfProperty5 mediumtext COMMENT '--  X-- -',
  orgid bigint(12) NOT NULL,
  created_by bigint(12) NOT NULL,
  created_date datetime NOT NULL,
  updated_by bigint(12) DEFAULT NULL,
  updated_date datetime DEFAULT NULL,
  lg_ip_mac varchar(100) DEFAULT NULL,
  lg_ip_mac_upd varchar(100) DEFAULT NULL,
  MN_FA_YEARID bigint(12) DEFAULT NULL,
  mn_assd_fassesment_date date DEFAULT NULL,
  mn_assd_nounit bigint(12) DEFAULT NULL,
  H_STATUS char(1) DEFAULT NULL,
  PRIMARY KEY (MN_assd_his_id),
  KEY FK_DETAIL_MN_ASS_id (MN_ass_id),
  KEY INDX_ASSESMENT_DET_ORGID (orgid),
  KEY INDX_ASSESMENT_DET_USAGETYPE1 (MN_assd_usagetype1),
  KEY INDX_ASSESMENT_DET_CONSTRU_TYPE (MN_assd_constru_type),
  KEY INDX_ASSESMENT_DET_OCCUPANCY_TYPE (MN_assd_occupancy_type),
  KEY INDX_ASSESMENT_DET_MN_FA_YEARID (MN_FA_YEARID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
