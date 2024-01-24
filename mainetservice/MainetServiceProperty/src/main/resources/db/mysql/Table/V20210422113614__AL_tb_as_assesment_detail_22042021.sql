--liquibase formatted sql
--changeset Kanchan:V20210422113614__AL_tb_as_assesment_detail_22042021.sql
alter table tb_as_assesment_detail add column CPD_USAGECLS bigint(20) NULL;
--liquibase formatted sql
--changeset Kanchan:V20210422113614__AL_tb_as_assesment_detail_220420211.sql
alter table tb_as_pro_assesment_detail add column CPD_USAGECLS bigint(20) NULL;
