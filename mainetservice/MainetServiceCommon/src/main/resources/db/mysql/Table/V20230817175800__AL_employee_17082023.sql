--liquibase formatted sql
--changeset PramodPatil:V20230817175800__AL_employee_17082023.sql
Alter table employee modify column EMP_ADDRESS varchar(1000) Null default null;
