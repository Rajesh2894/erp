--liquibase formatted sql
--changeset Kanchan:V20221222113029__AL_TB_VM_VEHICLE_MAINTENANCE_22122022.sql
alter table TB_VM_VEHICLE_MAINTENANCE add column VE_ID  BigInt(12) null default null;