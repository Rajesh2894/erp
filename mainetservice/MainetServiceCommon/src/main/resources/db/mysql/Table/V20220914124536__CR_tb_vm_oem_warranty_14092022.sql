--liquibase formatted sql
--changeset Kanchan:V20220914124536__CR_tb_vm_oem_warranty_14092022.sql
CREATE TABLE tb_vm_oem_warranty (
  OEM_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  DEPT_ID bigint(12) DEFAULT NULL,
  VE_VETYPE bigint(12) DEFAULT NULL,
  VE_NO bigint(12) DEFAULT NULL,
  Remarks varchar(250) DEFAULT NULL,
  ORGID bigint(12) DEFAULT NULL,
  CREATED_BY bigint(12) DEFAULT NULL,
  CREATED_DATE datetime DEFAULT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) DEFAULT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  PRIMARY KEY (OEM_ID)
);
--liquibase formatted sql
--changeset Kanchan:V20220914124536__CR_tb_vm_oem_warranty_140920221.sql
CREATE TABLE tb_vm_oem_warrantydet (
  OEMD_ID bigint(12)  NOT NULL,
  OEM_ID bigint(12) NOT NULL,
  part_type bigint(12) DEFAULT NULL,
  part_position bigint(12) DEFAULT NULL,
  part_name varchar(50) DEFAULT NULL,
  warranty_period bigint(12) DEFAULT NULL,
  part_puchaseDT date DEFAULT NULL,
  last_dt_warranty date DEFAULT NULL,
  ORGID bigint(12) NOT NULL,
  CREATED_BY bigint(12) NOT NULL,
  CREATED_DATE datetime NOT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) NOT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  unit bigint(12) DEFAULT NULL,
  PRIMARY KEY (OEMD_ID),
  KEY FK_OEM_ID_idx (OEM_ID),
  CONSTRAINT FK_OEM_ID FOREIGN KEY (OEM_ID) REFERENCES tb_vm_oem_warranty (OEM_ID)
) ;