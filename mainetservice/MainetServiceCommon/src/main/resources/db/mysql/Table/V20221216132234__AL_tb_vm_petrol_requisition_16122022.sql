--liquibase formatted sql
--changeset Kanchan:V20221216132234__AL_tb_vm_petrol_requisition_16122022.sql
Alter table tb_vm_petrol_requisition add column VE_CHASIS_SRNO varchar(55) Null default null;