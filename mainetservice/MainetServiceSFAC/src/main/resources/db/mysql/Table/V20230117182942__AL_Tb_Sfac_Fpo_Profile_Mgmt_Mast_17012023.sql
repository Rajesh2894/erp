--liquibase formatted sql
--changeset Kanchan:V20230117182942__AL_Tb_Sfac_Fpo_Profile_Mgmt_Mast_17012023.sql
Alter table Tb_Sfac_Fpo_Profile_Mgmt_Mast
add column BLOCK_DE_ALLOCATED_CBBO varchar(200) Null default null,
add column OLD_BLOCK varchar(200) Null default null,
add column DPR_REC_DT datetime Null default null,
add column DPR_REVIEWER varchar(200) Null default null,
add column DPR_SCORE bigint(20) Null default null,
add column DPR_REV_SUBM_DT datetime Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230117182942__AL_Tb_Sfac_Fpo_Profile_Mgmt_Mast_170120231.sql
Alter table Tb_Sfac_Fpo_Profile_Mgmt_Mast modify column ADD_SHARES_ISSUED bigint(20) null default null;