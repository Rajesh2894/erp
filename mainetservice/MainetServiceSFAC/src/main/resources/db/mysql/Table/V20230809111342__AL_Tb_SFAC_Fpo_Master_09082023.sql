--liquibase formatted sql
--changeset PramodPatil:V20230809111342__AL_Tb_SFAC_Fpo_Master_09082023.sql
Alter table Tb_SFAC_Fpo_Master modify column REGION bigint(20) null default null;