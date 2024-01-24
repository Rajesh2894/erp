--liquibase formatted sql
--changeset Kanchan:V20210430135518__AL_tb_as_exception_30042021.sql
alter table tb_as_exception modify column LG_IP_MAC varchar(100) NULL;
--liquibase formatted sql
--changeset Kanchan:V20210430135518__AL_tb_as_exception_300420211.sql
alter table tb_as_exception modify column UPDATED_BY bigint(12) NULL;
