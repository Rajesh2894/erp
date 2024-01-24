--liquibase formatted sql
--changeset Kanchan:V20220825123842__CR_tb_vm_veremen_mast_25082022.sql
CREATE TABLE tb_vm_veremen_mast (
  VEM_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  VEM_METYPE bigint(12) DEFAULT NULL,
  VEM_DATE date DEFAULT NULL ,
  VE_VETYPE bigint(12) DEFAULT NULL,
  VE_ID bigint(12) DEFAULT NULL,
  VEM_DOWNTIME bigint(3) DEFAULT NULL,
  VEM_DOWNTIMEUNIT bigint(12) DEFAULT NULL,
  VEM_READING decimal(22,1) DEFAULT NULL,
  VEM_EXPHEAD bigint(12) DEFAULT NULL,
  VEM_COSTINCURRED decimal(15,2) DEFAULT NULL,
  VEM_RECEIPTNO bigint(12) DEFAULT NULL,
  VEM_RECEIPTDATE datetime DEFAULT NULL,
  VEM_REASON varchar(100) DEFAULT NULL,
  ORGID bigint(12) DEFAULT NULL,
  CREATED_BY bigint(12) DEFAULT NULL,
  CREATED_DATE datetime DEFAULT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL,
  LG_IP_MAC varchar(100) DEFAULT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  PRIMARY KEY (VEM_ID)
) ;