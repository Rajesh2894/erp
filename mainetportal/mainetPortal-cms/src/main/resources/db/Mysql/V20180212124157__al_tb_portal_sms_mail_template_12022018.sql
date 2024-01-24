--liquibase formatted sql
--changeset priya:V20180212124157__al_tb_portal_sms_mail_template_12022018.sql
ALTER TABLE tb_portal_sms_mail_template 
ADD PRIMARY KEY (TP_ID);