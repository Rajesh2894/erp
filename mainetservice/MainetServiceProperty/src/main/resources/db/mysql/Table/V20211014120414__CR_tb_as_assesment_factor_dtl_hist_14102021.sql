--liquibase formatted sql
--changeset Kanchan:V20211014120414__CR_tb_as_assesment_factor_dtl_hist_14102021.sql
CREATE TABLE tb_as_assesment_factor_dtl_hist (
  MN_fact_his_id bigint(12) NOT NULL,
  MN_assd_id bigint(12) NOT NULL,
  MN_ASSF_FACTOR bigint(20) DEFAULT NULL,
  MN_ASSF_FACTOR_ID bigint(20) NOT NULL,
  MN_ASSF_FACTOR_VALUE_ID bigint(20) NOT NULL,
  MN_ASSF_ACTIVE char(1) NOT NULL DEFAULT 'Y',
  ORGID bigint(12) NOT NULL,
  CREATED_BY bigint(12) NOT NULL,
  CREATED_DATE datetime NOT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) DEFAULT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  MN_ASSO_START_DATE date DEFAULT NULL,
  MN_ASSO_END_DATE date DEFAULT NULL,
  MN_ASSF_FACTOR_DATE datetime DEFAULT NULL,
  MN_ASSF_FACTOR_REMARK varchar(100) DEFAULT NULL,
  PRIMARY KEY (MN_fact_his_id)
) ;
--liquibase formatted sql
--changeset Kanchan:V20211014120414__CR_tb_as_assesment_factor_dtl_hist_141020211.sql
CREATE TABLE tb_as_assesment_room_detail_hist (
  MN_HIS_ROOMID bigint(12) NOT NULL,
  MN_assd_id bigint(12) DEFAULT NULL,
  CPD_RMTYPEID bigint(12) DEFAULT NULL,
  PR_RMLENGTH decimal(12,2) DEFAULT NULL,
  PR_RMWIDTH decimal(12,2) DEFAULT NULL,
  PR_RMAREA decimal(12,2) DEFAULT NULL,
  ORGID bigint(12) DEFAULT NULL,
  USER_ID bigint(12) DEFAULT NULL,
  LANG_ID bigint(12) DEFAULT NULL,
  LMODDATE datetime DEFAULT NULL,
  MN_ROOM_NO bigint(12) DEFAULT NULL,
  IS_ACTIVE varchar(20) DEFAULT NULL,
  PRIMARY KEY (MN_HIS_ROOMID)
) ;
