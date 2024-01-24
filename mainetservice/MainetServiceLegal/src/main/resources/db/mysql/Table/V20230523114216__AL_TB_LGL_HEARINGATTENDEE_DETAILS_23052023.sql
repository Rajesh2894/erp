--liquibase formatted sql
--changeset Kanchan:V20230523114216__AL_TB_LGL_HEARINGATTENDEE_DETAILS_23052023.sql
ALTER TABLE TB_LGL_HEARINGATTENDEE_DETAILS ADD column hr_id bigint(12) null default null;
--liquibase formatted sql
--changeset Kanchan:V20230523114216__AL_TB_LGL_HEARINGATTENDEE_DETAILS_230520231.sql
ALTER TABLE TB_LGL_HEARINGATTENDEE_DETAILS ADD FOREIGN KEY (hr_id) REFERENCES TB_LGL_HEARING(hr_id);
--liquibase formatted sql
--changeset Kanchan:V20230523114216__AL_TB_LGL_HEARINGATTENDEE_DETAILS_230520232.sql
ALTER TABLE TB_LGL_HEARINGATTENDEE_DETAILS_HIST ADD column hr_id bigint(12) null default null;
--liquibase formatted sql
--changeset Kanchan:V20230523114216__AL_TB_LGL_HEARINGATTENDEE_DETAILS_230520233.sql
ALTER TABLE TB_LGL_HEARINGATTENDEE_DETAILS_HIST ADD FOREIGN KEY (hr_id) REFERENCES TB_LGL_HEARING(hr_id) ;