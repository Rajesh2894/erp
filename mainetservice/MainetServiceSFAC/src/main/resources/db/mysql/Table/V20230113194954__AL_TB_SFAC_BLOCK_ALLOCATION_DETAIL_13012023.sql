--liquibase formatted sql
--changeset Kanchan:V20230113194954__AL_TB_SFAC_BLOCK_ALLOCATION_DETAIL_13012023.sql
Alter table TB_SFAC_BLOCK_ALLOCATION_DETAIL
add column ALLOCATION_SUB_CATEGORY bigint(20) Null default null,
add column REASON varchar(200) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230113194954__AL_TB_SFAC_BLOCK_ALLOCATION_DETAIL_130120231.sql
Alter table Tb_SFAC_Fpo_Master
add column ALLOCATION_SUB_CATEGORY bigint(20) Null default null;