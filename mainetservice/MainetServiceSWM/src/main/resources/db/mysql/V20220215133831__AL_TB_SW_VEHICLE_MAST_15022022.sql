--liquibase formatted sql
--changeset Kanchan:V20220215133831__AL_TB_SW_VEHICLE_MAST_15022022.sql
alter table TB_SW_VEHICLE_MAST modify column VE_STD_WEIGHT decimal(15,2) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20220215133831__AL_TB_SW_VEHICLE_MAST_150220221.sql
alter table TB_SW_VEHICLE_MAST modify column VE_MODEL varchar(200) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20220215133831__AL_TB_SW_VEHICLE_MAST_150220222.sql
alter table TB_SW_VEHICLE_MAST_HIST modify column VE_STD_WEIGHT decimal(15,2) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20220215133831__AL_TB_SW_VEHICLE_MAST_150220223.sql
alter table TB_SW_VEHICLE_MAST_HIST modify column VE_MODEL varchar(200) NULL DEFAULT NULL;
