--liquibase formatted sql
--changeset PramodPatil:V20230713173814__AL_TB_SFAC_BLOCK_ALLOCATION_DETAIL_13072023.sql
Alter table TB_SFAC_BLOCK_ALLOCATION_DETAIL add column FPO_ID bigint(20) Null default null;

--liquibase formatted sql
--changeset PramodPatil:V20230713173814__AL_TB_SFAC_BLOCK_ALLOCATION_DETAIL_130720231.sql
Alter table TB_SFAC_BLOCK_ALLOCATION_DET_HIST add column FPO_ID bigint(20) Null default null;