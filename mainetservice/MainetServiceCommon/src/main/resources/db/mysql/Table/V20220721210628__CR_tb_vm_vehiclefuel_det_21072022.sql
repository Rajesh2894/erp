--liquibase formatted sql
--changeset Kanchan:V20220721210628__CR_tb_vm_vehiclefuel_det_21072022.sql
CREATE TABLE tb_vm_vehiclefuel_det (
  VEFD_ID bigint(12) primary key	NOT NULL,
  VEHF_ID bigint(12) DEFAULT NULL,
  VFU_ID bigint(12) DEFAULT NULL,
  VEFD_QUANTITY decimal(3,2) DEFAULT NULL,
  VEFD_UNIT bigint(12) DEFAULT NULL,
  VEFD_COST decimal(15,2) DEFAULT NULL,
  ORGID bigint(12) DEFAULT NULL,
  CREATED_BY bigint(12) DEFAULT NULL,
  CREATED_DATE datetime DEFAULT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) DEFAULT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  DELETE_FLAG char(1) DEFAULT NULL,
  KEY FK_VEHF_ID_idx (VEHF_ID),
  KEY FK_VFU_ID_idx (VFU_ID),
  FOREIGN KEY (VEHF_ID) REFERENCES tb_vm_vehiclefuel_mast (VEHF_ID),
  FOREIGN KEY (VFU_ID) REFERENCES tb_vm_pump_fuldet (vfu_id)
);