--liquibase formatted sql
--changeset Anil:V20200522123207__AL_tb_rti_application_22052020.sql
ALTER TABLE tb_rti_application 
ADD COLUMN CORR_ADDRESS VARCHAR(1000) NULL,
ADD COLUMN CORR_PINCODE BIGINT(12) NULL ;
