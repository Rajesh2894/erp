--liquibase formatted sql
--changeset Kanchan:V20230220201126__CR_tb_contract_part2_detail_hist_20022023.sql
CREATE TABLE tb_contract_part2_detail_hist (
  CONTP2_ID_H bigint(20) NOT NULL,
  CONTP2_ID bigint(20) NOT NULL,
  CONT_ID bigint(20) NOT NULL,
  CONTP2V_TYPE bigint(20) DEFAULT NULL,
  VM_VENDORID bigint(20) DEFAULT NULL,
  CONTP2_NAME varchar(400) DEFAULT NULL,
  CONTP2_ADDRESS varchar(1000) DEFAULT NULL,
  CONTP2_PROOF_ID_NO varchar(100) DEFAULT NULL,
  CONTV_ACTIVE char(1) NOT NULL,
  CONTP2_PARENT_ID bigint(20) DEFAULT NULL,
  CONTP2_TYPE char(1) NOT NULL,
  ORGID bigint(20) NOT NULL,
  CREATED_BY bigint(20) NOT NULL,
  CREATED_DATE datetime DEFAULT NULL,
  UPDATED_BY bigint(20) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) NOT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  CONTP2_PRIMARY varchar(1) DEFAULT NULL,
  CONTP2_PHOTO_FILE_NAME varchar(200) DEFAULT NULL,
  CONTP2_THUMB_FILE_NAME varchar(200) DEFAULT NULL,
  CONTP2_PHOTO_FILE_PATH_NAME varchar(500) DEFAULT NULL,
  CONTP2_THUMB_FILE_PATH_NAME varchar(500) DEFAULT NULL,
  PRIMARY KEY (CONTP2_ID_H),
  KEY FK_P2_CONT_ID (CONT_ID),
  KEY FK_VENDER_ID (VM_VENDORID)
);
--liquibase formatted sql
--changeset Kanchan:V20230220201126__CR_tb_contract_part2_detail_hist_200220231.sql
CREATE TABLE tb_contract_instalment_detail_hist (
  CONIT_ID_H bigint(20) NOT NULL,
  CONIT_ID bigint(20) NOT NULL ,
  CONT_ID bigint(20) NOT NULL,
  CONIT_AMT_TYPE bigint(20) NOT NULL,
  CONIT_VALUE bigint(20) NOT NULL,
  CONIT_DUE_DATE date NOT NULL,
  CONIT_MILESTONE varchar(1000) DEFAULT NULL,
  CONTT_ACTIVE char(1) NOT NULL,
  CONIT_PR_FLAG char(1) DEFAULT NULL,
  ORGID bigint(20) NOT NULL,
  CREATED_BY bigint(20) NOT NULL,
  CREATED_DATE datetime NOT NULL,
  UPDATED_BY bigint(20) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) NOT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  TAX_ID bigint(20) DEFAULT NULL,
  CONIT_AMT decimal(15,2) DEFAULT NULL,
  CONIT_START_DATE date DEFAULT NULL,
  PRIMARY KEY (CONIT_ID_H),
  KEY FK_INSTALL_CONT_ID (CONT_ID)
);