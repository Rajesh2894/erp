--liquibase formatted sql
--changeset Kanchan:V20230106191141__AL_Tb_SFAC_Fpo_Master_06012023.sql
Alter table Tb_SFAC_Fpo_Master add column REGISTERED_ON_ENAM  bigint(20),add USER_ID_ENAM varchar(50) NULL DEFAULT NULL;