--liquibase formatted sql
--changeset Kanchan:V20230621200937__UP_TB_VEHICLE_MAST_21062023.sql
SET SQL_SAFE_UPDATES=0;
--liquibase formatted sql
--changeset Kanchan:V20230621200937__UP_TB_VEHICLE_MAST_210620231.sql
update TB_VEHICLE_MAST set VEH_MAIN_BY=43494 where VEH_MAIN_BY IS NULL;