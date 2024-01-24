--liquibase formatted sql
--changeset priya:V20180123174558__AL_tb_eip_announcement_hist_19012018.sql
ALTER TABLE tb_eip_announcement_hist 
CHANGE COLUMN USER_ID CREATED_BY INT(11) NULL DEFAULT NULL COMMENT 'User Id' ,
ADD COLUMN IMAGE VARCHAR(200) NULL COMMENT '' AFTER H_STATUS,
ADD COLUMN DMS_IMG_ID VARCHAR(100) NULL DEFAULT NULL COMMENT '' AFTER IMAGE,
ADD COLUMN DMS_IMG_FOLDER_PATH VARCHAR(100) NULL DEFAULT NULL COMMENT '' AFTER DMS_IMG_ID,
ADD COLUMN DMS_IMG_NAME VARCHAR(100) NULL DEFAULT NULL COMMENT '' AFTER DMS_IMG_FOLDER_PATH,
ADD COLUMN DMS_IMG_VERSION VARCHAR(10) NULL DEFAULT NULL COMMENT '' AFTER DMS_IMG_NAME;

--liquibase formatted sql
--changeset priya:V20180123174558__AL_tb_eip_announcement_hist_190120181.sql
ALTER TABLE tb_eip_announcement_hist 
DROP COLUMN LANG_ID;

