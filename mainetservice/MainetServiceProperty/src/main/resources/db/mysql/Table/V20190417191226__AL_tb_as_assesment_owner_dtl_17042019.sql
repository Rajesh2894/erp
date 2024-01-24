--liquibase formatted sql
--changeset nilima:V20190417191226__AL_tb_as_assesment_owner_dtl_17042019.sql
ALTER TABLE tb_as_assesment_owner_dtl
CHANGE COLUMN SM_SERVICE_ID SM_SERVICE_ID BIGINT(12) NULL COMMENT '	SERVICE ID NUMBER	' ;