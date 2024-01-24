--liquibase formatted sql
--changeset Kanchan:V20220422145647__AL_tb_as_assesment_mast_22042022.sql
alter table  tb_as_assesment_mast add column new_house_no varchar(50) null default null;
--liquibase formatted sql
--changeset Kanchan:V20220422145647__AL_tb_as_assesment_mast_220420221.sql
alter table  tb_as_pro_assesment_mast add column new_house_no varchar(50) null default null;
--liquibase formatted sql
--changeset Kanchan:V20220422145647__AL_tb_as_assesment_mast_220420222.sql
alter table tb_as_prop_mas add column new_house_no varchar(50) null default null;
