--liquibase formatted sql
--changeset Anil:V20200414171446__AL_tb_rts_drn_con_14042020.sql
ALTER TABLE tb_rts_drn_con ADD COLUMN status VARCHAR(5) NULL AFTER UPDATED_DATE;
--liquibase formatted sql
--changeset Anil:V20200414171446__AL_tb_rts_drn_con_140420201.sql
ALTER TABLE tb_rts_drn_con_hist ADD COLUMN status VARCHAR(5) NULL AFTER UPDATED_DATE;
