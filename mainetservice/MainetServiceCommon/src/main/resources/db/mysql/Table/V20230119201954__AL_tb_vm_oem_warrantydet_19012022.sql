--liquibase formatted sql
--changeset Kanchan:V20230119201954__AL_tb_vm_oem_warrantydet_19012022.sql
Alter table tb_vm_oem_warrantydet 
add column PART_ID_NO    VARCHAR(30) null default null,
add column PART_POSITION_DESC    VARCHAR(200) null default null,
add column PART_QTY     BIGINT(3) null default null,
add column PART_REMARK    VARCHAR(250) null default null;