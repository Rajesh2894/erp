--liquibase formatted sql
--changeset Kanchan:V20221213161455__AL_tb_vm_petrol_requisition_13122022.sql
alter table  tb_vm_petrol_requisition modify column VE_ID bigint(12) DEFAULT NULL;