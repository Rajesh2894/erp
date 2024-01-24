--liquibase formatted sql
--changeset Kanchan:V20210927154309__AL_tb_as_bill_mas_27092021.sql
alter table tb_as_bill_mas
add column Assd_std_rate decimal(15,2), 
add  Assd_alv decimal(15,2), 
add  Assd_rv decimal(15,2),
add  Assd_cv decimal(15,2) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20210927154309__AL_tb_as_bill_mas_270920211.sql
alter table tb_as_bill_mas_hist
add column Assd_std_rate decimal(15,2),
add  Assd_alv decimal(15,2),
add  Assd_rv decimal(15,2),
add  Assd_cv decimal(15,2) NULL DEFAULT NULL;


