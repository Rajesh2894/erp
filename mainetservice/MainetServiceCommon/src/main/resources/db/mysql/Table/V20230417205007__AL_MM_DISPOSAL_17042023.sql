--liquibase formatted sql
--changeset Kanchan:V20230417205007__AL_MM_DISPOSAL_17042023.sql
ALTER TABLE MM_DISPOSAL ADD column expiryid bigint(12) null default null;
--liquibase formatted sql
--changeset Kanchan:V20230417205007__AL_MM_DISPOSAL_170420231.sql
ALTER TABLE MM_DISPOSAL_ITEMS ADD column binlocation bigint(12) null default null;
--liquibase formatted sql
--changeset Kanchan:V20230417205007__AL_MM_DISPOSAL_170420232.sql
ALTER TABLE MM_EXPIRED_DET ADD column binlocation bigint(12) null default null;