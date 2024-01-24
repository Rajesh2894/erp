--liquibase formatted sql
--changeset Kanchan:V20220919194159__CR_tb_vm_petrol_requisition_19092022.sql
CREATE TABLE tb_vm_petrol_requisition (
  request_Id bigint(12) NOT NULL,
  date date DEFAULT NULL,
  time time NOT NULL,
  vehicle_No varchar(20) DEFAULT NULL,
  vehicle_Type bigint(12) NOT NULL,
  fuel_Type bigint(12) NOT NULL,
  request_Status varchar(15) DEFAULT NULL,
  fuel_Quantity decimal(15,4) NOT NULL,
  dept_id bigint(12) NOT NULL,
  vehicle_driver bigint(12) NOT NULL,
  orgid bigint(12) NOT NULL,
  created_by bigint(12) NOT NULL,
  created_date datetime NOT NULL,
  updated_by bigint(12) DEFAULT NULL,
  updated_date datetime DEFAULT NULL,
  lg_ip_mac varchar(100) NOT NULL,
  lg_ip_mac_upd varchar(100) DEFAULT NULL,
  WF_STATUS varchar(20) DEFAULT NULL,
  Fuel_Request_No varchar(25) DEFAULT NULL,
  VE_ID bigint(12) NOT NULL,
  meter_reading decimal(10,2) DEFAULT NULL,
  fuel_Quant_Unit bigint(12) NOT NULL,
  PRIMARY KEY (request_Id)
);