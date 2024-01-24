--liquibase formatted sql
--changeset Kanchan:V20230208181713__AL_tb_vm_insurance_detail_08022023.sql
ALTER TABLE tb_vm_insurance_detail CHANGE column insured_fees Policy_No varchar(20) NULL DEFAULT NULL;