--liquibase formatted sql
--changeset Kanchan:V20230323180458__AL_Tb_SFAC_Cbbo_Master_23032023.sql
Alter table Tb_SFAC_Cbbo_Master add column CB_ID bigint(20) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230323180458__AL_Tb_SFAC_Cbbo_Master_230320231.sql
Alter table TB_SFAC_CBBO_MAST_HIST add column CB_ID bigint(20) Null default null;