--liquibase formatted sql
--changeset priya:V20180303121613__AL_portal_employee.sql
ALTER TABLE employee
CHANGE COLUMN AUT_N2 REPORTING_EMPID BIGINT(12) NULL DEFAULT NULL COMMENT 'additional nvarchar2 aut_n2 to be used in future' ;
--liquibase formatted sql
--changeset priya:V20180303121613__AL_portal_employee1.sql
ALTER TABLE employee 
CHANGE COLUMN DSGID DSGID BIGINT(12) NULL COMMENT 'Designation Id';






