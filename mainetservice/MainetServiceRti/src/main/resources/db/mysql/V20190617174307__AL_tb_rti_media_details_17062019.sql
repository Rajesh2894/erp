--liquibase formatted sql
--changeset Anil:V20190617174307__AL_tb_rti_media_details_17062019.sql
ALTER TABLE tb_rti_media_details
ADD COLUMN MEDIA_DESC VARCHAR(200) NULL COMMENT 'Media Desc' AFTER `MEDIA_AMOUNT`;
