--liquibase formatted sql
--changeset Kanchan:V20220916171821__AL_tb_vm_oem_warranty_16092022.sql
Alter table tb_vm_oem_warranty modify column Remarks varchar(500) null default null;