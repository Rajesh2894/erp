--liquibase formatted sql
--changeset Anil:V20190709105634__AL_tb_swd_scheme_application_09072019.sql
ALTER TABLE tb_swd_scheme_application 
DROP COLUMN SDSCH_ID;


