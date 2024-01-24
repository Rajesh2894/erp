--liquibase formatted sql
--changeset Kanchan:V20210820172014__AL_TB_MRM_WITNESS_DET_20082021.sql
alter table TB_MRM_WITNESS_DET add column  CAPTURE_PHOTO_NAME  varchar(100),add CAPTURE_PHOTO_PATH varchar(100),add CAPTURE_FINGERPRINT_NAME varchar(100),add CAPTURE_FINGERPRINT_PATH varchar(100) NULL;
--liquibase formatted sql
--changeset Kanchan:V20210820172014__AL_TB_MRM_WITNESS_DET_200820211.sql
alter table TB_MRM_WITNESS_DET_HIST  add column  CAPTURE_PHOTO_NAME  varchar(100),add CAPTURE_PHOTO_PATH varchar(100),add CAPTURE_FINGERPRINT_NAME varchar(100),add CAPTURE_FINGERPRINT_PATH varchar(100) NULL;

