--liquibase formatted sql
--changeset priya:V20180303121648__AL_portal_employee_hist.sql
ALTER TABLE employee_hist
CHANGE COLUMN LOCID LOCID BIGINT(12) NULL COMMENT 'location id' ;
--liquibase formatted sql
--changeset priya:V20180303121648__AL_portal_employee_hist1.sql
ALTER TABLE employee_hist
CHANGE COLUMN DSGID DSGID BIGINT(12) NULL COMMENT 'Designation id' ;
