--liquibase formatted sql
--changeset Kanchan:V20230110174016__AL_TB_SFAC_IA_Master_Det_10012023.sql
Alter table TB_SFAC_IA_Master_Det modify column DSGID varchar(100) null default null;