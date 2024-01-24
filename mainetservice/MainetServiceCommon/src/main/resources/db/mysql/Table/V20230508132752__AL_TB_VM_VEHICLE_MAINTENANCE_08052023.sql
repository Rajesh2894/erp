--liquibase formatted sql
--changeset Kanchan:V20230508132752__AL_TB_VM_VEHICLE_MAINTENANCE_08052023.sql
alter table TB_VM_VEHICLE_MAINTENANCE
modify column VE_DOWNTIME bigint(6) null default null,
modify column VE_MAINDAY bigint(6) null default null;
--liquibase formatted sql
--changeset Kanchan:V20230508132752__AL_TB_VM_VEHICLE_MAINTENANCE_080520231.sql
alter table TB_VM_VEHICLEFUEL_MAST modify column VEF_DMNO varchar(12) not null;
--liquibase formatted sql
--changeset Kanchan:V20230508132752__AL_TB_VM_VEHICLE_MAINTENANCE_080520232.sql
alter table TB_VM_VEHICLEFUEL_MAST_HIST modify column VEF_DMNO varchar(12) not null;