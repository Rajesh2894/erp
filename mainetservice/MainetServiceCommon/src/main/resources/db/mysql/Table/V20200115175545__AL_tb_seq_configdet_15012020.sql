--liquibase formatted sql
--changeset Anil:V20200115175545__AL_tb_seq_configdet_15012020.sql
ALTER TABLE tb_seq_configdet 
DROP PRIMARY KEY,
ADD PRIMARY KEY (SEQ_DETID);
