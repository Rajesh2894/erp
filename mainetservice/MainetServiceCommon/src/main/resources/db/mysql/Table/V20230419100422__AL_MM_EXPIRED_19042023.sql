--liquibase formatted sql
--changeset Kanchan:V20230419100422__AL_MM_EXPIRED_19042023.sql
ALTER TABLE MM_EXPIRED ADD APM_APPLICATION_ID bigint(20) null default null AFTER expiryid ;
