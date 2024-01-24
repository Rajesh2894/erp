--liquibase formatted sql
--changeset Kanchan:V20221121165411__CR_tb_sfac_ia_master_21112022.sql
 CREATE TABLE tb_sfac_ia_master (
  ia_id bigint(20) NOT NULL,
  IA_NAME varchar(200) NOT NULL,
  IA_ONBOARDING_YEAR bigint(20) DEFAULT NULL,
  ORGID bigint(20) NOT NULL,
  CREATED_BY bigint(20) NOT NULL,
  CREATED_DATE datetime NOT NULL,
  UPDATED_BY bigint(20) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) NOT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  IA_ADDRESS varchar(350) DEFAULT NULL,
  IA_PIN_CODE varchar(20) DEFAULT NULL,
  PRIMARY KEY (ia_id)
) ;
--liquibase formatted sql
--changeset Kanchan:V20221121165411__CR_tb_sfac_ia_master_211120221.sql
CREATE TABLE tb_sfac_ia_master_det (
  IAD_ID bigint(20) NOT NULL,
  IA_ID bigint(20) NOT NULL,
  DSGID bigint(20) DEFAULT NULL,
  TITLE_ID bigint(20) DEFAULT NULL,
  F_NAME varchar(250) DEFAULT NULL,
  M_NAME varchar(250) DEFAULT NULL,
  L_NAME varchar(250) DEFAULT NULL,
  EMAIL_ID varchar(250) DEFAULT NULL,
  CONTACT_NO varchar(20) DEFAULT NULL,
  ORGID bigint(20) NOT NULL,
  CREATED_BY bigint(20) NOT NULL,
  CREATED_DATE datetime NOT NULL,
  UPDATED_BY bigint(20) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) NOT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  STATUS char(1) DEFAULT NULL,
  EMAIL_ID_SEC varchar(50) DEFAULT NULL,
  remarks varchar(500) DEFAULT NULL,
  PRIMARY KEY (IAD_ID),
  KEY FK_IA_ID1 (IA_ID)
) ;