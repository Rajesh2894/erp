--liquibase formatted sql
--changeset Kanchan:V20221222180205__AL_TB_SFAC_IA_Master_Det_22122022.sql
alter table TB_SFAC_IA_Master_Det
add column ROLE bigint(20) Null default null,
add column STATE bigint(20) Null default null;