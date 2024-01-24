--liquibase formatted sql
--changeset Anil:V20191019101616__AL_tb_rti_media_details_19102019.sql
ALTER TABLE tb_rti_media_details ADD COLUMN media_status CHAR(1) NULL AFTER UPL_FILE_PATH;
