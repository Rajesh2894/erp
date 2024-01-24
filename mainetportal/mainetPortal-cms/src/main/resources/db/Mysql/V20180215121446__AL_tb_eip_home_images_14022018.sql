--liquibase formatted sql
--changeset priya:V20180215121446__AL_tb_eip_home_images_14022018.sql
ALTER TABLE tb_eip_home_images 
ADD COLUMN CAPTION VARCHAR(50) NULL DEFAULT NULL COMMENT '' AFTER DMS_DOC_VERSION;
--liquibase formatted sql
--changeset priya:V20180215121446__AL_tb_eip_home_images_140220181.sql
ALTER TABLE tb_eip_home_images_hist 
ADD COLUMN CAPTION VARCHAR(50) NULL DEFAULT NULL COMMENT '' AFTER DMS_DOC_VERSION;

