--liquibase formatted sql
--changeset Kanchan:V20210215134318__AL_tb_as_assesment_mast_15022021.sql
alter table tb_as_assesment_mast add column LOGICAL_PROP_NO varchar(50) NULL;
--liquibase formatted sql
--changeset Kanchan:V20210215134318__AL_tb_as_assesment_mast_150220211.sql
ALTER TABLE tb_as_bill_mas add column BM_SRV_DT datetime ; 
--liquibase formatted sql
--changeset Kanchan:V20210215134318__AL_tb_as_assesment_mast_150220212.sql
ALTER TABLE tb_as_bill_mas add column PD_FLATNO varchar(48);
--liquibase formatted sql
--changeset Kanchan:V20210215134318__AL_tb_as_assesment_mast_150220213.sql
alter table tb_as_prop_mas add column CPD_BILLMETH bigint(20);
--liquibase formatted sql
--changeset Kanchan:V20210215134318__AL_tb_as_assesment_mast_150220214.sql
alter table tb_as_prop_mas add column pd_flatno varchar(12) NULL;


