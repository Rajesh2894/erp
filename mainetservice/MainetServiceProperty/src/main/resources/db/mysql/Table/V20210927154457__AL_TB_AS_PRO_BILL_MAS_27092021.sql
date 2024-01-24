--liquibase formatted sql
--changeset Kanchan:V20210927154457__AL_TB_AS_PRO_BILL_MAS_27092021.sql
alter table TB_AS_PRO_BILL_MAS    
add column Assd_std_rate decimal(15,2),
add  Assd_alv decimal(15,2),
add  Assd_rv decimal(15,2),
add  Assd_cv decimal(15,2) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20210927154457__AL_TB_AS_PRO_BILL_MAS_270920211.sql
alter table tb_as_pro_bill_mas_hist
add column Assd_std_rate decimal(15,2),
add  Assd_rv decimal(15,2),
add  Assd_cv decimal(15,2) NULL DEFAULT NULL;
