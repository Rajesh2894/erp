--liquibase formatted sql
--changeset nilima:V20180905140001_AL_TB_AS_PRO_BILL_MAS1.sql
alter table TB_AS_PRO_BILL_MAS add column pro_bm_actual_arr_amount decimal(15,2) DEFAULT NULL COMMENT '	Bill ARREARS Amount	' after pro_bm_total_amount;
--liquibase formatted sql
--changeset nilima:V20180905140001_AL_TB_AS_PRO_BILL_MAS2.sql
alter table TB_AS_PRO_BILL_MAS add column pro_gen_flag char(1) DEFAULT NULL COMMENT '	Bill genaration Flag	' after pro_bm_actual_arr_amount;
--liquibase formatted sql
--changeset nilima:V20180905140001_AL_TB_AS_PRO_BILL_MAS3.sql
alter table TB_AS_PRO_BILL_MAS_HIST add column pro_bm_actual_arr_amount decimal(15,2) DEFAULT NULL COMMENT '	Bill ARREARS Amount	' after pro_bm_total_amount;
--liquibase formatted sql
--changeset nilima:V20180905140001_AL_TB_AS_PRO_BILL_MAS4.sql
alter table TB_AS_PRO_BILL_MAS_HIST add column pro_gen_flag char(1) DEFAULT NULL COMMENT '	Bill genaration Flag	' after pro_bm_actual_arr_amount;
--liquibase formatted sql
--changeset nilima:V20180905140001_AL_TB_AS_PRO_BILL_MAS5.sql
alter table TB_AS_BILL_MAS add column mn_actual_arr_amount decimal(15,2) DEFAULT NULL COMMENT '	Bill ARREARS Amount	' after MN_TOTAL_AMOUNT;
--liquibase formatted sql
--changeset nilima:V20180905140001_AL_TB_AS_PRO_BILL_MAS6.sql
alter table TB_AS_BILL_MAS add column mn_gen_flag char(1) DEFAULT NULL COMMENT '	Bill genaration Flag	' after mn_actual_arr_amount;
--liquibase formatted sql
--changeset nilima:V20180905140001_AL_TB_AS_PRO_BILL_MAS7.sql
alter table TB_AS_BILL_MAS_HIST add column mn_actual_arr_amount decimal(15,2) DEFAULT NULL COMMENT '	Bill ARREARS Amount	' after MN_TOTAL_AMOUNT;
--liquibase formatted sql
--changeset nilima:V20180905140001_AL_TB_AS_PRO_BILL_MAS8.sql
alter table TB_AS_BILL_MAS_HIST add column mn_gen_flag char(1) DEFAULT NULL COMMENT '	Bill genaration Flag	' after mn_actual_arr_amount;
