--liquibase formatted sql
--changeset Kanchan:V20230612184832__AL_Tb_Sfac_Fpo_Profile_Mgmt_Mast_12062023.sql
alter table Tb_Sfac_Fpo_Profile_Mgmt_Mast
add column ERP_ACCOUNTANT_NAME bigint(20),
add ACC_ERP_PCKG_COST decimal(15,2),
add SOFTWARE_USE_DATE datetime NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20230612184832__AL_Tb_Sfac_Fpo_Profile_Mgmt_Mast_120620231.sql
Alter table Tb_SFAC_Farmer_Mast add column FPO_ID bigint(20) null default null;