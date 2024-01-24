--liquibase formatted sql
--changeset Kanchan:V20230608182027__AL_tb_As_bill_mas_08062023.sql
ALTER TABLE tb_As_bill_mas MODIFY COLUMN mn_no VARCHAR(100) null default null;
--liquibase formatted sql
--changeset Kanchan:V20230608182027__AL_tb_As_bill_mas_080620231.sql
ALTER TABLE tb_As_pro_bill_mas MODIFY COLUMN pro_bm_no VARCHAR(100) null default null;
