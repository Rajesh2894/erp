--liquibase formatted sql
--changeset Kanchan:V20220721203726__CR_tb_vm_vehiclefuel_mast_21072022.sql
CREATE TABLE tb_vm_vehiclefuel_mast (
  VEHF_ID bigint(12) primary key	NOT NULL,
  VEF_DATE date NOT NULL,
  VE_VETYPE bigint(12) NOT NULL,
  VE_ID bigint(12) NOT NULL,
  VEF_READING bigint(12) NOT NULL,
  VEF_DRIVERNAME varchar(50) NOT NULL,
  PU_ID bigint(12) NOT NULL,
  VEF_DMNO bigint(12) NOT NULL,
  VEF_DMDATE date NOT NULL,
  VEF_RECEIPTNO bigint(12) DEFAULT NULL,
  VEF_RECEIPTDATE date DEFAULT NULL,
  VEF_RMAMOUNT decimal(15,2) DEFAULT NULL,
  ORGID bigint(12) NOT NULL,
  CREATED_BY bigint(12) NOT NULL,
  CREATED_DATE datetime NOT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) NOT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL
);