--liquibase formatted sql
--changeset priya:V20180208111449__AL_tb_portal_sms_mail_template_PORTAL_060220182.sql
ALTER TABLE tb_portal_sms_mail_template 
ADD CONSTRAINT FK_TEMPLATE_SE_ID
  FOREIGN KEY (SE_ID)
  REFERENCES tb_portal_sms_integration (SE_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
