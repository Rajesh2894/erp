--liquibase formatted sql
--changeset Kanchan:V20230427200925__AL_Tb_SFAC_Cbbo_Master_27042023.sql
Alter table Tb_SFAC_Cbbo_Master add column LGD_CODE varchar(10) null default null;
