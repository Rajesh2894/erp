--liquibase formatted sql
--changeset Kanchan:V20221223125855__AL_TB_SFAC_IA_Master_Det_23122022.sql
Alter table TB_SFAC_IA_Master_Det modify column DSGID bigint(20) Null default null;