--liquibase formatted sql
--changeset nilima:V20170818151916__AL_TB_WORKFLOW_REQUEST_14082017.sql
ALTER TABLE TB_WORKFLOW_REQUEST
CHANGE COLUMN STATUS STATUS VARCHAR(20) NULL DEFAULT NULL COMMENT 'Status' ;