--liquibase formatted sql
--changeset Kanchan:V20220204174850__AL_tb_vm_insurance_detail_04022022.sql
Alter table tb_vm_insurance_detail modify insured_fees double(15,2) NULL DEFAULT NULL;
