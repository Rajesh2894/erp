--liquibase formatted sql
--changeset Kanchan:V20230503181526__AL_MM_EXPIRED_03052023.sql
ALTER TABLE MM_EXPIRED MODIFY movementdate date null default null;