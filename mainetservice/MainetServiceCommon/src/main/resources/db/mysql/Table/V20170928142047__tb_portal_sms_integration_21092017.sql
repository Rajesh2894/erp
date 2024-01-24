--liquibase formatted sql
--changeset nilima:V20170928142047__tb_portal_sms_integration_21092017.sql
ALTER TABLE tb_portal_sms_integration 
DROP FOREIGN KEY FK_SMS_EVENTID;
ALTER TABLE tb_portal_sms_integration 
CHANGE COLUMN SERVICE_EVENT_ID SMFID BIGINT(12) NULL DEFAULT NULL COMMENT '' ;
ALTER TABLE tb_portal_sms_integration 
ADD CONSTRAINT FK_SMS_EVENTID
  FOREIGN KEY (SMFID)
  REFERENCES tb_sysmodfunction (SMFID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;