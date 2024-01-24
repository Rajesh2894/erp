--liquibase formatted sql
--changeset Kanchan:V20220228185048__AL_tb_vm_insurance_Claim_28022022.sql
Alter table  tb_vm_insurance_Claim modify column  insured_fees double(15,2)  NULL DEFAULT NULL;
