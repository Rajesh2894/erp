--liquibase formatted sql
--changeset Kanchan:V20221209183319__AL_tb_sfac_fpo_master_09122022.sql
Alter table tb_sfac_fpo_master
add column GSTIN varchar(15) Null default null,
add column AUTHORIZE_CAPITAL bigint(20) Null default null,
add column TOTAL_AREA_COV_KHARIF varchar(200) Null default null,
add column TOTAL_AREA_COV_RABI varchar(200) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20221209183319__AL_tb_sfac_fpo_master_091220221.sql
Alter table tb_sfac_fpo_master change column IA_ALLOCATION_YEAR_CBBO CBBO_ALLOCATION_YEAR bigint(20) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20221209183319__AL_tb_sfac_fpo_master_091220222.sql
Alter table Tb_Sfac_Fpo_Bank_Det modify column BANK_NAME bigint(20) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20221209183319__AL_tb_sfac_fpo_master_091220223.sql
alter table Tb_SFAC_Fpo_Master_Det
modify column PRIMARY_CROP_APP_BY_DMC varchar(200) NULL DEFAULT NULL,
modify column SECONDARY_CROP_APP_BY_DMC varchar(200) NULL DEFAULT NULL;