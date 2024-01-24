--liquibase formatted sql
--changeset Kanchan:V20221212090843__AL_tb_vm_OEM_Warranty_12122022.sql
alter table tb_vm_OEM_Warranty add column REF_NO Varchar(20) Null default null;