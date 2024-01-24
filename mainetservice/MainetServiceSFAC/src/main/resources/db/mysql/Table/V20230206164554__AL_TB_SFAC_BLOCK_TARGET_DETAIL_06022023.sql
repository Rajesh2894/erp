--liquibase formatted sql
--changeset Kanchan:V20230206164554__AL_TB_SFAC_BLOCK_TARGET_DETAIL_06022023.sql
Alter table TB_SFAC_BLOCK_TARGET_DETAIL add column TARGET_DATE datetime Null default null;