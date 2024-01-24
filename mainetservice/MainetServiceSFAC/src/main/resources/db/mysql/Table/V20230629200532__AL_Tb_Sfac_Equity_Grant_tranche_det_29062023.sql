--liquibase formatted sql
--changeset Kanchan:V20230629200532__AL_Tb_Sfac_Equity_Grant_tranche_det_29062023.sql
Alter table Tb_Sfac_Equity_Grant_tranche_det
add column DMC_APPROVAL_DATE datetime Null default null,
add column SHARE_COLLECTION decimal(15,2) Null default null;