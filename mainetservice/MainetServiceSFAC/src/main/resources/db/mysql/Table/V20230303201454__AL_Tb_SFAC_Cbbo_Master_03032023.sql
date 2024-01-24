--liquibase formatted sql
--changeset Kanchan:V20230303201454__AL_Tb_SFAC_Cbbo_Master_03032023.sql
Alter table Tb_SFAC_Cbbo_Master add column APPLICATION_ID bigint(20) NULL DEFAULT NULL,add STATUS varchar(100) NULL DEFAULT NULL,add REMARK varchar(150) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20230303201454__AL_Tb_SFAC_Cbbo_Master_030320231.sql
Alter table TB_SFAC_CBBO_MAST_HIST add column APPLICATION_ID bigint(20) NULL DEFAULT NULL,add STATUS varchar(100) NULL DEFAULT NULL,add REMARK varchar(150) NULL DEFAULT NULL;