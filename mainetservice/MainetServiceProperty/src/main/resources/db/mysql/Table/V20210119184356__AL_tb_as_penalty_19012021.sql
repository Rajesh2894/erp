--liquibase formatted sql
--changeset Kanchan:V20210119184356__AL_tb_as_penalty_19012021.sql
alter table tb_as_penalty add column  group_prop_no varchar(20),add  parent_prop_no varchar(20)  NULL;
--liquibase formatted sql
--changeset Kanchan:V20210119184356__AL_tb_as_penalty_190120211.sql
alter table tb_as_excess_amt add column  group_prop_no varchar(20),add  parent_prop_no varchar(20) NULL;
--liquibase formatted sql
--changeset Kanchan:V20210119184356__AL_tb_as_penalty_190120212.sql
alter table tb_as_excess_amt_hist  add column  group_prop_no varchar(20),add  parent_prop_no varchar(20)  NULL;



