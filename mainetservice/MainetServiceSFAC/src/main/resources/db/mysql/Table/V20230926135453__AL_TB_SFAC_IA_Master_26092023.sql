--liquibase formatted sql
--changeset PramodPatil:V20230926135453__AL_TB_SFAC_IA_Master_26092023.sql
Alter table TB_SFAC_IA_Master add column REMARK varchar(1000) Null default null;

--liquibase formatted sql
--changeset PramodPatil:V20230926135453__AL_TB_SFAC_IA_Master_260920231.sql
Alter table TB_SFAC_IA_MAST_HIST add column REMARK varchar(1000) Null default null;