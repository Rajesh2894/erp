--liquibase formatted sql
--changeset Kanchan:V20201118180728__AL_tb_wt_penalty_18112020.sql
alter table tb_wt_penalty add column curr_bm_idno bigint(12);
--liquibase formatted sql
--changeset Kanchan:V20201118180728__AL_tb_wt_penalty_181120201.sql
alter table tb_wt_penalty_hist add column curr_bm_idno bigint(12);


