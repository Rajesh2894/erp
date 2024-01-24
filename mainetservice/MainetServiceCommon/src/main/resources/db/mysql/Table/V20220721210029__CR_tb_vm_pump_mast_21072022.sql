--liquibase formatted sql
--changeset Kanchan:V20220721210029__CR_tb_vm_pump_mast_21072022.sql
CREATE TABLE tb_vm_pump_mast (
  FPM_ID bigint(12) primary key	NOT NULL,
  PU_PUTYPE bigint(12) NOT NULL,
  PU_PUMPNAME varchar(100) NOT NULL,
  PU_ADDRESS varchar(300) NOT NULL,
  VM_VENDORID bigint(12) DEFAULT NULL,
  PU_ACTIVE char(1) NOT NULL DEFAULT,
  ORGID bigint(12) NOT NULL,
  CREATED_BY bigint(12) NOT NULL,
  CREATED_DATE datetime NOT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) NOT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  PRIMARY KEY (FPM_ID)
);
--liquibase formatted sql
--changeset Kanchan:V20220721210029__CR_tb_vm_pump_mast_210720221.sql
CREATE TABLE tb_vm_pump_fuldet (
  vfu_id bigint(12) NOT NULL,
  fpm_id bigint(12) NOT NULL,
  pu_fuid bigint(12) NOT NULL,
  pu_fuunit bigint(12) NOT NULL,
  PU_ACTIVE char(1) DEFAULT NULL,
  ORGID bigint(12) NOT NULL,
  CREATED_BY bigint(12) NOT NULL,
  CREATED_DATE datetime NOT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) NOT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  PRIMARY KEY (vfu_id),
  KEY FK_FPM_ID_idx (fpm_id),
  FOREIGN KEY (fpm_id) REFERENCES tb_vm_pump_mast (FPM_ID)
);