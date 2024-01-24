--liquibase formatted sql
--changeset PramodPatil:V20230829171305__AL_TB_LOI_MAS_29082023.sql
ALTER TABLE TB_LOI_MAS Modify column LOI_V3 varchar(100) null default null;
