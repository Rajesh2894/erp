--liquibase formatted sql
--changeset Kanchan:V20220722115129__AL_tb_as_bill_det_22072022.sql
alter table tb_as_bill_det add column Revised_bill_date  datetime,add Revised_bill_type varchar(20) null default null;
--liquibase formatted sql
--changeset Kanchan:V20220722115129__AL_tb_as_bill_det_220720221.sql
alter table tb_as_bill_det_hist add column Revised_bill_date  datetime,add Revised_bill_type varchar(20) null default null;
--liquibase formatted sql
--changeset Kanchan:V20220722115129__AL_tb_as_bill_det_220720222.sql
alter table tb_as_pro_bill_det add column Revised_bill_date  datetime,add Revised_bill_type varchar(20) null default null;
--liquibase formatted sql
--changeset Kanchan:V20220722115129__AL_tb_as_bill_det_220720223.sql
alter table tb_as_pro_bill_det_hist add column Revised_bill_date  datetime,add Revised_bill_type varchar(20) null default null;