--liquibase formatted sql
--changeset Kanchan:V20220929162528__AL_tb_vm_OEM_Warrant_29092022.sql
alter table tb_vm_OEM_Warranty modify column maintanceDate  date null default null;  