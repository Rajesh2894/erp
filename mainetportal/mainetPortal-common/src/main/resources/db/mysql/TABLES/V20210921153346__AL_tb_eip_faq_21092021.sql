--liquibase formatted sql
--changeset Kanchan:V20210921153346__AL_tb_eip_faq_21092021.sql
alter table tb_eip_faq modify ANSWER_EN varchar(2000);
--liquibase formatted sql
--changeset Kanchan:V20210921153346__AL_tb_eip_faq_210920211.sql
alter table tb_eip_faq modify QUESTION_EN varchar(500); 
