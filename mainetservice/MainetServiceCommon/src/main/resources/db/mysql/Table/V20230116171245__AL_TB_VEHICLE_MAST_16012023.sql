--liquibase formatted sql
--changeset Kanchan:V20230116171245__AL_TB_VEHICLE_MAST_16012023.sql
alter Table TB_VEHICLE_MAST
add Column VEH_MAIN_BY bigint(20) null default null,
add Column MNT_VENDORID bigint(12) null default null;