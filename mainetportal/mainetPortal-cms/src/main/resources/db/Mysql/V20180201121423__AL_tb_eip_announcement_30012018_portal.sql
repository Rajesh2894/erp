--liquibase formatted sql
--changeset priya:V20180201121423__AL_tb_eip_announcement_30012018_portal.sql
ALTER TABLE tb_eip_announcement
DROP COLUMN LANG_ID,
CHANGE COLUMN USER_ID CREATED_BY BIGINT(12) NOT NULL ;
