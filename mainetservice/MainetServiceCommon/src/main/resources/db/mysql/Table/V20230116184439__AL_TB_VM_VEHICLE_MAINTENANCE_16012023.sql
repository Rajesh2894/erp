--liquibase formatted sql
--changeset Kanchan:V20230116184439__AL_TB_VM_VEHICLE_MAINTENANCE_16012023.sql
alter Table TB_VM_VEHICLE_MAINTENANCE
Add Column VE_MAINKM bigint(20) null default null,
Add Column VE_MAINKM_UNIT bigint(12) null default null,
Add Column VE_BUFFER_TIME bigint(3) null default null,
Add Column VE_BUFFER_TIME_UNIT bigint(12) null default null;