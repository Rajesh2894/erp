--liquibase formatted sql
--changeset Kanchan:V20230303101928__AL_Tb_SFAC_Circular_Notify_Master_03032023.sql
Alter table Tb_SFAC_Circular_Notify_Master add column CONVENER_OF_CIRCULAR bigint(20) null default null;