--liquibase formatted sql
--changeset Kanchan:V20221116163148__AL_tb_vm_petrol_requisition_16112022.sql
alter table tb_vm_petrol_requisition modify column FUEL_REQ_REASON varchar(200) null default null;