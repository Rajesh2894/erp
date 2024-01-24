--liquibase formatted sql
--changeset Kanchan:V20220203181824__CR_tb_vm_insurance_detail_03022022.sql
CREATE TABLE tb_vm_insurance_detail (
  insurance_Id bigint(12) NOT NULL COMMENT 'primary key',
  vehicle_No bigint(12) DEFAULT NULL COMMENT 'Insurance Details Vehicle number',
  vehicle_Type bigint(12) NOT NULL COMMENT 'Insurance Details Vehicle Type',
  dept_id bigint(12) NOT NULL COMMENT 'Insurance Details Department',
  insured_by bigint(12) NOT NULL COMMENT 'Insurance Details insured by',
  issue_date date NOT NULL COMMENT 'Insurance Details insurance issue date',
  end_date date NOT NULL COMMENT 'Insurance Details insurance end date',
  insured_amt double(15,2) NOT NULL COMMENT 'Insurance insured amount',
  insured_fees double(15,2) DEFAULT NULL,
  orgid bigint(12) NOT NULL COMMENT 'organization id',
  created_by bigint(12) NOT NULL COMMENT 'login user id',
  created_date datetime NOT NULL COMMENT 'login user date',
  updated_by bigint(12) DEFAULT NULL COMMENT 'login user id after modify record',
  updated_date datetime DEFAULT NULL COMMENT 'login user date after modify record',
  lg_ip_mac varchar(100) NOT NULL COMMENT 'login user machine id',
  lg_ip_mac_upd varchar(100) DEFAULT NULL COMMENT 'login user machine id after modify record',
  PRIMARY KEY (insurance_Id)
) ;
