--liquibase formatted sql
--changeset Kanchan:V20211119162555__AL_tb_rti_application_19112021.sql
alter table tb_rti_application add column RTI_SCND_APEAL_STATUS Varchar(200) NULL DEFAULT NULL;
