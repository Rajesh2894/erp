--liquibase formatted sql
--changeset Kanchan:V20221122175456__CR_tb_sfac_state_info_mast_22112022.sql
CREATE TABLE tb_sfac_state_info_mast (
  ST_ID bigint(20) NOT NULL,
  STATE bigint(20) NOT NULL,
  STATE_CODE bigint(20) DEFAULT NULL,
  ST_SHORT_CODE varchar(5) DEFAULT NULL,
  DISTRICT bigint(20) NOT NULL,
  DIST_CODE bigint(20) DEFAULT NULL,
  AREA_TYPE bigint(20) DEFAULT NULL,
  ZONE bigint(20) DEFAULT NULL,
  ODOP bigint(20) DEFAULT NULL,
  ASPIRATIONAL_DIST bigint(20) DEFAULT NULL,
  TRIBAL_DIST bigint(20) DEFAULT NULL,
  ORGID bigint(20) NOT NULL,
  CREATED_BY bigint(20) NOT NULL,
  CREATED_DATE datetime NOT NULL,
  UPDATED_BY bigint(20) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) NOT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  PRIMARY KEY (ST_ID)
) ;