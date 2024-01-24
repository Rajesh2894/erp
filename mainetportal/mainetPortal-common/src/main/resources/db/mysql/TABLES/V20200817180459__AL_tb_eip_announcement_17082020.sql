--liquibase formatted sql
--changeset Anil:V20200817180459__AL_tb_eip_announcement_17082020.sql
ALTER TABLE tb_eip_announcement
ADD COLUMN LINKTYPE char(1) DEFAULT NULL AFTER HIGHLIGHTED_DATE,
ADD COLUMN LINK varchar(500) DEFAULT NULL AFTER LINKTYPE ;
--liquibase formatted sql
--changeset Anil:V20200817180459__AL_tb_eip_announcement_170820201.sql
ALTER TABLE tb_eip_announcement_hist
ADD COLUMN LINKTYPE char(1) DEFAULT NULL AFTER HIGHLIGHTED_DATE,
ADD COLUMN LINK varchar(500) DEFAULT NULL AFTER LINKTYPE; 




