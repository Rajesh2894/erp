--liquibase formatted sql
--changeset Kanchan:V20210118182109__AL_TB_SW_VEHICLE_SCHEDULING_18012021.sql
alter table TB_SW_VEHICLE_SCHEDULING add column IS_ACTIVE VARCHAR(1) NULL;
