--liquibase formatted sql
--changeset Kanchan:V20230608181531__AL_MM_EXPIRED_08062023.sql
ALTER TABLE MM_EXPIRED ADD department bigint(12) null default null AFTER movementno;