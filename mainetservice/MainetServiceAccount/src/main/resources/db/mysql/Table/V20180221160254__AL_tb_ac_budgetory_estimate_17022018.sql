--liquibase formatted sql
--changeset priya:V20180221160254__AL_tb_ac_budgetory_estimate_17022018.sql
ALTER TABLE tb_ac_budgetory_estimate 
CHANGE COLUMN LAST_YEAR_COUNT NFA_YEARID INT(11) NULL DEFAULT NULL COMMENT 'Last year count' ;
