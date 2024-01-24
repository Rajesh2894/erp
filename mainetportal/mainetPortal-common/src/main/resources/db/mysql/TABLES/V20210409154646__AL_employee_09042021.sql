--liquibase formatted sql
--changeset Kanchan:V20210409154646__AL_employee_09042021.sql
alter table employee modify column emp_address varchar(800) ;
