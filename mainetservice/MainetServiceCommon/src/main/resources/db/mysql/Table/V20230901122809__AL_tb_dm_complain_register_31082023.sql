--liquibase formatted sql
--changeset PramodPatil:V20230901122809__AL_tb_dm_complain_register_31082023.sql
ALTER TABLE tb_dm_complain_register
MODIFY COLUMN call_close_remark varchar(1000) null default null,
MODIFY COLUMN complaint_description varchar(3000) null default null;