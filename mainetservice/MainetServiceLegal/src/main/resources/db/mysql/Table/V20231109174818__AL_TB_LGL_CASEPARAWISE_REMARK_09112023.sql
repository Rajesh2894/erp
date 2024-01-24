--liquibase formatted sql
--changeset PramodPatil:V20231109174818__AL_TB_LGL_CASEPARAWISE_REMARK_09112023.sql
ALTER TABLE TB_LGL_CASEPARAWISE_REMARK ADD COLUMN status varchar(1) null default null;