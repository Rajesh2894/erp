--liquibase formatted sql
--changeset nilima:V20170928141804__AL_tb_portal_sms_integration_25092017.sql
ALTER TABLE tb_portal_sms_integration
CHANGE COLUMN `SM_SERVICEID` `SM_SERVICEID` BIGINT(12) NULL COMMENT 'Service Id' ;
