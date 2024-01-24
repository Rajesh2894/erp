--liquibase formatted sql
--changeset Kanchan:V20210610114633__AL_tb_rti_application_10062021.sql
alter table tb_rti_application add column POSTAL_CARD_NO varchar(50)NULL;
--liquibase formatted sql
--changeset Kanchan:V20210610114633__AL_tb_rti_application_100620211.sql		
alter table tb_rti_application_hist add column POSTAL_AMT decimal(15,2) NULL;
