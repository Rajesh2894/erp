--liquibase formatted sql
--changeset Anil:V20191010154801__AL_tb_care_request_10102019.sql
ALTER TABLE tb_care_request CHANGE COLUMN COMP_SUBTYPE_ID COMP_SUBTYPE_ID BIGINT(12) NULL COMMENT 'complaint Subtype' ;
