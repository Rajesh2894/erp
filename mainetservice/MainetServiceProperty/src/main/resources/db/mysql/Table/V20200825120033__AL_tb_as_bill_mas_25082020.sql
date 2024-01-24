--liquibase formatted sql
--changeset Anil:V20200825120033__AL_tb_as_bill_mas_25082020.sql
ALTER TABLE tb_as_bill_mas ADD COLUMN demand_rebate decimal(15,2) NULL;
--liquibase formatted sql
--changeset Anil:V20200825120033__AL_tb_as_bill_mas_250820201.sql
ALTER TABLE tb_as_bill_mas_hist ADD COLUMN demand_rebate decimal(15,2) NULL;
--liquibase formatted sql
--changeset Anil:V20200825120033__AL_tb_as_bill_mas_250820202.sql
ALTER TABLE tb_as_pro_bill_mas ADD COLUMN demand_rebate decimal(15,2) NULL;
--liquibase formatted sql
--changeset Anil:V20200825120033__AL_tb_as_bill_mas_250820203.sql
ALTER TABLE tb_as_pro_bill_mas_hist ADD COLUMN demand_rebate decimal(15,2) NULL;
