--liquibase formatted sql
--changeset nilima:V20181019200525_TB_WT_BILL_MAS1.sql
alter table TB_WT_BILL_MAS add column BM_ACTUAL_ARR_AMOUNT decimal(15,2) DEFAULT NULL COMMENT '	Bill ARREARS Amount	' after BM_TOTAL_BAL_AMOUNT;
--liquibase formatted sql
--changeset nilima:V20181019200525_TB_WT_BILL_MAS2.sql
alter table TB_WT_BILL_MAS add column BM_GEN_FLAG char(1) DEFAULT NULL COMMENT '	Bill genaration Flag	' after BM_ACTUAL_ARR_AMOUNT;