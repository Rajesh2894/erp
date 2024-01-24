--liquibase formatted sql
--changeset Anil:V20200811170216__AL_tb_wt_excess_amt_tb_wt_excess_amt_11082020.sql
ALTER TABLE tb_wt_excess_amt ADD COLUMN EXCESS_ACTIVE CHAR(1) NULL;
--liquibase formatted sql
--changeset Anil:V20200811170216__AL_tb_wt_excess_amt_tb_wt_excess_amt_110820201.sql
ALTER TABLE tb_wt_excess_amt_hist ADD COLUMN EXCESS_ACTIVE CHAR(1) NULL;
