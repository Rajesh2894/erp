--liquibase formatted sql
--changeset Anil:V20200407123706__AL_tb_sw_mrf_mast_07042020.sql
ALTER TABLE tb_sw_mrf_mast ADD COLUMN PM_PROP_NO VARCHAR(20) NULL;
--liquibase formatted sql
--changeset Anil:V20200407123706__AL_tb_sw_mrf_mast_070420201.sql
ALTER TABLE tb_sw_mrf_mast_hist ADD COLUMN PM_PROP_NO VARCHAR(20) NULL;
