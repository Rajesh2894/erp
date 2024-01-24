--liquibase formatted sql
--changeset Kanchan:V20210301192751__AL_tb_eip_announcement_01032021.sql
ALTER TABLE tb_eip_announcement
ADD COLUMN ISHIGHLIGHTEDFLAG CHAR(1) NULL DEFAULT 'N' ;
--liquibase formatted sql
--changeset Kanchan:V20210301192751__AL_tb_eip_announcement_010320211.sql
ALTER TABLE tb_eip_announcement_hist
ADD COLUMN ISHIGHLIGHTEDFLAG CHAR(1) NULL DEFAULT 'N' ;
