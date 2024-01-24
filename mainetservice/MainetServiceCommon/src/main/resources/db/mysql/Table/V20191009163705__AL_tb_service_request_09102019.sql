--liquibase formatted sql
--changeset Anil:V20191009163705__AL_tb_service_request_09102019.sql
ALTER TABLE tb_service_request CHANGE COLUMN COMP_SUBTYPE_ID COMP_SUBTYPE_ID BIGINT(12) NULL COMMENT 'complaint Subtype';
