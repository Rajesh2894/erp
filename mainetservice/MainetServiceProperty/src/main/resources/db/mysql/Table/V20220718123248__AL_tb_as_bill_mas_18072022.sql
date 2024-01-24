--liquibase formatted sql
--changeset Kanchan:V20220718123248__AL_tb_as_bill_mas_18072022.sql
alter table tb_as_bill_mas add column APM_APPLICATION_ID bigint(16) null default null;
--liquibase formatted sql
--changeset Kanchan:V20220718123248__AL_tb_as_bill_mas_180720221.sql
alter table tb_as_bill_mas_hist add column APM_APPLICATION_ID bigint(16) null default null;
--liquibase formatted sql
--changeset Kanchan:V20220718123248__AL_tb_as_bill_mas_180720222.sql
alter table tb_as_pro_bill_mas add column APM_APPLICATION_ID bigint(16) null default null;
--liquibase formatted sql
--changeset Kanchan:V20220718123248__AL_tb_as_bill_mas_180720223.sql
alter table tb_as_pro_bill_mas_hist add column APM_APPLICATION_ID bigint(16) null default null;