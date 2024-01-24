--liquibase formatted sql
--changeset nilima:V20171207203653__AL_TB_CARE_FEEDBACK_01122017.sql
Alter table tb_care_feedback add COLUMN FEEDBACK_DATE datetime;