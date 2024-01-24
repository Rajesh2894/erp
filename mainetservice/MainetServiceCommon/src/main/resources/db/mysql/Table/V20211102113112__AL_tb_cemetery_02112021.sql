--liquibase formatted sql
--changeset Kanchan:V20211102113112__AL_tb_cemetery_02112021.sql
alter table  tb_cemetery add column WARDID bigint(20) DEFAULT NULL;
