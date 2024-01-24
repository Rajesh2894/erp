--liquibase formatted sql
--changeset Kanchan:V20220915165950__AL_tb_vm_oem_warranty_15092022.sql
alter Table tb_vm_oem_warranty Add column maintanceDate date not null;
