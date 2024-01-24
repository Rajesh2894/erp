--liquibase formatted sql
--changeset Kanchan:V20201120164637__AL_TB_MRM_HUSBAND_20112020.sql
alter table TB_MRM_HUSBAND add column CAPTURE_PHOTO_NAME varchar(100) Null,
add column CAPTURE_PHOTO_PATH varchar(100) Null,
add column CAPTURE_FINGERPRINT_NAME varchar(100) Null,
add column CAPTURE_FINGERPRINT_PATH varchar(100) Null;
--liquibase formatted sql
--changeset Kanchan:V20201120164637__AL_TB_MRM_HUSBAND_201120201.sql
alter table TB_MRM_HUSBAND_HIST add column CAPTURE_PHOTO_NAME varchar(100) Null,
add column CAPTURE_PHOTO_PATH varchar(100) Null,
add column CAPTURE_FINGERPRINT_NAME varchar(100) Null,
add column CAPTURE_FINGERPRINT_PATH varchar(100) Null;
--liquibase formatted sql
--changeset Kanchan:V20201120164637__AL_TB_MRM_HUSBAND_201120202.sql
alter table TB_MRM_WIFE add column CAPTURE_PHOTO_NAME varchar(100) Null,
add column CAPTURE_PHOTO_PATH varchar(100) Null,
add column CAPTURE_FINGERPRINT_NAME varchar(100) Null,
add column CAPTURE_FINGERPRINT_PATH varchar(100) Null;
--liquibase formatted sql
--changeset Kanchan:V20201120164637__AL_TB_MRM_HUSBAND_201120203.sql
alter table TB_MRM_WIFE_HIST add column CAPTURE_PHOTO_NAME varchar(100) Null,
add column CAPTURE_PHOTO_PATH varchar(100) Null,
add column CAPTURE_FINGERPRINT_NAME varchar(100) Null,
add column CAPTURE_FINGERPRINT_PATH varchar(100) Null;
