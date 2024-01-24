--liquibase formatted sql
--changeset Kanchan:V20210113201037__AL_TB_SW_VEHICLE_SCHEDULING_hist _130112021.sql
alter table TB_SW_VEHICLE_SCHEDULING_hist add column VES_EMPID VARCHAR(50) null;
