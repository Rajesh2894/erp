--liquibase formatted sql
--changeset nilima:V20180821173916__AL_employee_17082018.sql
ALTER TABLE employee
ADD COLUMN MOB_OTP VARCHAR(200) NULL AFTER AGENCY_LOCATION;



