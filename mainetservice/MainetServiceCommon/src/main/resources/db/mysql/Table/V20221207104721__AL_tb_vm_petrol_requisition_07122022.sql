--liquibase formatted sql
--changeset Kanchan:V20221207104721__AL_tb_vm_petrol_requisition_07122022.sql
alter table tb_vm_petrol_requisition add column COUPON_No varchar(25) NULL DEFAULT NULL;