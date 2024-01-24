ALTER TABLE `TB_RL_BILL_MAST` 
CHANGE COLUMN `bm_due_date` `bm_due_date` DATE NULL DEFAULT NULL COMMENT 'Bill due date' ,
ADD COLUMN `bm_start_date` DATE NULL COMMENT 'Bill Start Date' AFTER `TAX_ID

ALTER TABLE `TB_RL_BILL_MAST` 
ADD COLUMN `bm_type` CHAR(1) NULL DEFAULT NULL COMMENT 'null ->Installment \'I\' -> Interest' AFTER `bm_start_date`;

