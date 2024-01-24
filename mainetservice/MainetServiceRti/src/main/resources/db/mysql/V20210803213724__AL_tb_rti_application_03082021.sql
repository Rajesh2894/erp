--liquibase formatted sql
--changeset Kanchan:V20210803213724__AL_tb_rti_application_03082021.sql
ALTER TABLE tb_rti_application MODIFY COLUMN RTI_DESC varchar(3500);
--liquibase formatted sql
--changeset Kanchan:V20210803213724__AL_tb_rti_application_030820211.sql
ALTER TABLE tb_rti_application MODIFY COLUMN RTI_SUBJECT varchar(600);
