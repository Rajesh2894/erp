--liquibase formatted sql
--changeset Kanchan:V20220228183345__CR_tb_vm_insurance_claim_28022022.sql
CREATE TABLE `tb_vm_insurance_claim` (
  `insurance_Claim_Id` bigint(12) NOT NULL COMMENT 'primary key',
  `vehicle_No` bigint(12) DEFAULT NULL,
  `vehicle_Type` bigint(12) NOT NULL,
  `dept_id` bigint(12) NOT NULL,
  `insured_by` bigint(12) NOT NULL,
  `issue_date` date NOT NULL,
  `end_date` date NOT NULL,
  `insured_amt` double(15,2) NOT NULL,
  `insured_fees` double(15,2) NOT NULL,
  `claim_amt` double(15,2) NOT NULL,
  `claim_approved_amt` double(15,2) NOT NULL,
  `orgid` bigint(12) NOT NULL,
  `created_by` bigint(12) NOT NULL,
  `created_date` datetime NOT NULL,
  `updated_by` bigint(12) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `lg_ip_mac` varchar(100) NOT NULL, 
  `lg_ip_mac_upd` varchar(100) DEFAULT NULL 
) ;
