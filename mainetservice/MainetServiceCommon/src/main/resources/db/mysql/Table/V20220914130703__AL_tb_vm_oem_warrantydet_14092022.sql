--liquibase formatted sql
--changeset Kanchan:V20220914130703__AL_tb_vm_oem_warrantydet_14092022.sql
alter Table tb_vm_oem_warrantydet Add column maintanceDate date not null;