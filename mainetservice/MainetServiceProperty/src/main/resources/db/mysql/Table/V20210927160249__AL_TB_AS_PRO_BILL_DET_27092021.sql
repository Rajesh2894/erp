--liquibase formatted sql
--changeset Kanchan:V20210927160249__AL_TB_AS_PRO_BILL_DET_27092021.sql
alter table TB_AS_PRO_BILL_DET
add column MN_PER_RATE decimal(15,2) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20210927160249__AL_TB_AS_PRO_BILL_DET_270920211.sql
alter table tb_as_pro_bill_det_hist
add column MN_PER_RATE decimal(15,2) NULL DEFAULT NULL;
