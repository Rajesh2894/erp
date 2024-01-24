--liquibase formatted sql
--changeset Kanchan:V20220919194317__AL_tb_vm_petrol_requisition_19092022.sql
alter table tb_vm_petrol_requisition add column PU_ID bigint(12) DEFAULT NULL;