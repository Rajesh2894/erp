--liquibase formatted sql
--changeset Kanchan:V20221003161344__AL_tb_vm_petrol_requisition_03102022.sql
alter table tb_vm_petrol_requisition add column  FUEL_REQ_REASON VARCHAR(200)  NOT NULL;