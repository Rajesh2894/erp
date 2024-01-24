--liquibase formatted sql
--changeset Anil:V20200727120623__AL_tb_wt_penalty_hist_27072020.sql
ALTER TABLE tb_wt_penalty_hist DROP PRIMARY KEY,
ADD PRIMARY KEY (H_WPENALTY_ID);
