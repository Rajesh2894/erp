--liquibase formatted sql
--changeset Kanchan:V20221230175640__AL_Tb_SFAC_Fpo_Master_30122022.sql
Alter table Tb_SFAC_Fpo_Master
add column SHARED_CAPITAL bigint(20) Null default null,
add column NORTHEAST_REGION bigint(20) Null default null;