--liquibase formatted sql
--changeset Anil:V20191016171602__AL_tb_lgl_caseparawise_remark_16102019.sql
ALTER TABLE tb_lgl_caseparawise_remark ADD COLUMN par_uad_remark VARCHAR(100) NULL AFTER LG_IP_MAC_UPD;
--liquibase formatted sql
--changeset Anil:V20191016171602__AL_tb_lgl_caseparawise_remark_161020191.sql
ALTER TABLE tb_lgl_caseparawise_remark_hist ADD COLUMN par_uad_remark VARCHAR(100) NULL AFTER LG_IP_MAC_UPD;
