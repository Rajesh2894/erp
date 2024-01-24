--liquibase formatted sql
--changeset Kanchan:V20221223125744__AL_TB_SFAC_IA_Master_23122022.sql
Alter table TB_SFAC_IA_Master add column LEVEL char(1) Null default null,
add column STATE bigint(20) Null default null;
