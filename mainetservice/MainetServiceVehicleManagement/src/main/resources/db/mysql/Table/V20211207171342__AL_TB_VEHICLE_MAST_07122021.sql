--liquibase formatted sql
--changeset Kanchan:V20211207171342__AL_TB_VEHICLE_MAST_07122021.sql
Alter table  TB_VEHICLE_MAST add column  VE_FUEL_CAPACITY  decimal(15,2)  NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211207171342__AL_TB_VEHICLE_MAST_071220211.sql
Alter table  TB_VM_VEHICLE_MAST_HIST add column  VE_FUEL_CAPACITY  decimal(15,2)  NULL DEFAULT NULL;
