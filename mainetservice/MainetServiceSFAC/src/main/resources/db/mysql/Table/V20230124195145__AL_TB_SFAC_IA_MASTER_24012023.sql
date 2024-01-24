--liquibase formatted sql
--changeset Kanchan:V20230124195145__AL_TB_SFAC_IA_MASTER_24012023.sql
Alter table TB_SFAC_IA_MASTER add column IA_SHORT_NAME VARCHAR(200) Null default null;