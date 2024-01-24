--liquibase formatted sql
--changeset Kanchan:V20220214121009__AL_tb_as_pro_bill_mas_14022022.sql
alter table tb_as_pro_bill_mas add logical_prop_no varchar(50) DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20220214121009__AL_tb_as_pro_bill_mas_140220221.sql
alter table tb_as_bill_mas add logical_prop_no varchar(50) DEFAULT NULL;
