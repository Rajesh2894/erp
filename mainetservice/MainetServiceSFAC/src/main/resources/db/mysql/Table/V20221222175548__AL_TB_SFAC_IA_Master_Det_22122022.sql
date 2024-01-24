--liquibase formatted sql
--changeset Kanchan:V20221222175548__AL_TB_SFAC_IA_Master_Det_22122022.sql
alter table TB_SFAC_IA_Master_Det modify column DSGID varchar(100) null default null;