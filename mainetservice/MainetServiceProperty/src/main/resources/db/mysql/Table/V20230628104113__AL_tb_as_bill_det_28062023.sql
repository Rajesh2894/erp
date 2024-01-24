--liquibase formatted sql
--changeset Kanchan:V20230628104113__AL_tb_as_bill_det_28062023.sql
ALTER TABLE tb_as_bill_det ADD COLUMN bill_gen_penalty decimal(15,2) null default '0.00';
--liquibase formatted sql
--changeset Kanchan:V20230628104113__AL_tb_as_bill_det_280620231.sql
ALTER TABLE tb_as_bill_det_hist ADD COLUMN bill_gen_penalty decimal(15,2) null default '0.00';
--liquibase formatted sql
--changeset Kanchan:V20230628104113__AL_tb_as_bill_det_280620232.sql
ALTER TABLE tb_as_pro_bill_det ADD COLUMN bill_gen_penalty decimal(15,2) null default '0.00';
--liquibase formatted sql
--changeset Kanchan:V20230628104113__AL_tb_as_bill_det_280620233.sql
ALTER TABLE tb_as_pro_bill_det_hist ADD COLUMN bill_gen_penalty decimal(15,2) null default '0.00';