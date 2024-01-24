--liquibase formatted sql
--changeset priya:V20180125104458__Al_tb_ac_bill_deduction_detail_24012018
ALTER TABLE tb_ac_bill_deduction_detail 
CHANGE COLUMN FI04_V1 BCH_ID BIGINT(12) NULL DEFAULT NULL COMMENT '' ;

--liquibase formatted sql
--changeset priya:V20180125104458__Al_tb_ac_bill_deduction_detail_240120181
ALTER TABLE tb_ac_bill_deduction_detail 
ADD COLUMN FI04_V1 VARCHAR(100) NULL DEFAULT 'Null' COMMENT '' AFTER BUDGETCODE_ID;
