--liquibase formatted sql
--changeset Kanchan:V20230113210020__AL_TB_SFAC_BLOCK_TARGET_DETAIL_13012023.sql
Alter table TB_SFAC_BLOCK_TARGET_DETAIL add column ALLOCATION_SUB_CATEGORY bigint(20) Null default null;