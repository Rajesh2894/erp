--liquibase formatted sql
--changeset Kanchan:V20230209182336__AL_tb_vm_insurance_Claim_09022023.sql
ALTER TABLE tb_vm_insurance_Claim CHANGE COLUMN insured_fees Policy_No varchar(20) Null default null;