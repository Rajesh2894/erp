--liquibase formatted sql
--changeset priya:V20180201120706__AL_designation_31012018_portal.sql
ALTER TABLE designation 
CHANGE COLUMN LOCID LOCID BIGINT(12) NULL DEFAULT NULL COMMENT 'Location Id' ;
