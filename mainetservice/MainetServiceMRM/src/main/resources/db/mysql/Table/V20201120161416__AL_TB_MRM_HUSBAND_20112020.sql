--liquibase formatted sql
--changeset Kanchan:V20201120161416__AL_TB_MRM_HUSBAND_20112020.sql
alter table TB_MRM_HUSBAND drop column CAPTURE_PHOTO, drop column CAPTURE_FINGERPRINT;
--liquibase formatted sql
--changeset Kanchan:V20201120161416__AL_TB_MRM_HUSBAND_201120201.sql
alter table TB_MRM_HUSBAND_HIST drop column CAPTURE_PHOTO, drop column CAPTURE_FINGERPRINT;
--liquibase formatted sql
--changeset Kanchan:V20201120161416__AL_TB_MRM_HUSBAND_201120202.sql
alter table TB_MRM_WIFE drop column CAPTURE_PHOTO, drop column CAPTURE_FINGERPRINT;
--liquibase formatted sql
--changeset Kanchan:V20201120161416__AL_TB_MRM_HUSBAND_201120203.sql
alter table TB_MRM_WIFE_HIST drop column CAPTURE_PHOTO, drop column CAPTURE_FINGERPRINT;
