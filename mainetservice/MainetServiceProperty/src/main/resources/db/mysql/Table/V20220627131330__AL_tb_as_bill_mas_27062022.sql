--liquibase formatted sql
--changeset Kanchan:V20220627131330__AL_tb_as_bill_mas_27062022.sql
alter table tb_as_bill_mas add column Revised_bill_date datetime null default null, add column Revised_bill_type varchar(20) null default null;
--liquibase formatted sql
--changeset Kanchan:V20220627131330__AL_tb_as_bill_mas_270620221.sql
alter table tb_as_bill_mas_hist add column Revised_bill_date datetime null default null, add column Revised_bill_type varchar(20) null default null;
--liquibase formatted sql
--changeset Kanchan:V20220627131330__AL_tb_as_bill_mas_270620222.sql
alter table tb_as_pro_bill_mas add column Revised_bill_date datetime null default null, add column Revised_bill_type varchar(20) null default null;
--liquibase formatted sql
--changeset Kanchan:V20220627131330__AL_tb_as_bill_mas_270620223.sql
alter table tb_as_pro_bill_mas_hist add column Revised_bill_date datetime null default null, add column Revised_bill_type varchar(20) null default null;
