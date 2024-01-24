--liquibase formatted sql
--changeset Kanchan:V20220919160924__CR_tb_vm_vehicle_log_book_19092022.sql
CREATE TABLE tb_vm_vehicle_log_book (
  ve_ID bigint(12) NOT NULL,
  OUT_DATE date NOT NULL,
  IN_DATE date DEFAULT NULL,
  ve_Out_Time time NOT NULL,
  ve_In_Time time DEFAULT NULL,
  ve_Journey_From varchar(120) NOT NULL,
  ve_Journey_To varchar(120) DEFAULT NULL,
  day_End_Meter_Read double(20,5) DEFAULT NULL,
  day_Start_Meter_Read double(20,5) NOT NULL,
  fuel_In_Litre double(15,5) DEFAULT NULL,
  date_Of_Fueling datetime DEFAULT NULL,
  day_Visit_Desc varchar(300) NOT NULL,
  driver_Id bigint(12) NOT NULL,
  reason varchar(300) NOT NULL,
  ORGID bigint(12) NOT NULL,
  CREATED_BY bigint(12) NOT NULL,
  CREATED_DATE datetime NOT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) NOT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  VNO_ID bigint(12) DEFAULT NULL,
  ve_type bigint(12) DEFAULT NULL,
  PRIMARY KEY (ve_ID)
) ;