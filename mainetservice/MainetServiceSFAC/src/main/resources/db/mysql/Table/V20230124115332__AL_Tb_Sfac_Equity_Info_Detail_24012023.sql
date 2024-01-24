--liquibase formatted sql
--changeset Kanchan:V20230124115332__AL_Tb_Sfac_Equity_Info_Detail_24012023.sql
Alter table Tb_Sfac_Equity_Info_Detail modify column UTILISATION_EQT_GRANT bigint(20) Null default null;