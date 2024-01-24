--liquibase formatted sql
--changeset Kanchan:V20230508095128__AL_TB_PORTAL_SMS_MAIL_TEMPLATE_08052023.sql
ALTER TABLE TB_PORTAL_SMS_MAIL_TEMPLATE modify column EXT_TMPLT varchar(30) null default null;
--liquibase formatted sql
--changeset Kanchan:V20230508095128__AL_TB_PORTAL_SMS_MAIL_TEMPLATE_080520231.sql
ALTER TABLE TB_PORTAL_SMS_MAIL_TEMPLATE modify column EXT_TMPLT_REG varchar(30) null default null;