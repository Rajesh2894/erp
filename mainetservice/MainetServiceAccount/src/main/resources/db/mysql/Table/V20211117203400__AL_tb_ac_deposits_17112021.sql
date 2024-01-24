--liquibase formatted sql
--changeset Kanchan:V20211117203400__AL_tb_ac_deposits_17112021.sql
ALTER TABLE `tb_ac_deposits`
ADD COLUMN `DEFECT_LIABLITY_DATE` DATE  NULL  AFTER `PAYMENT_ID`;
--liquibase formatted sql
--changeset Kanchan:V20211117203400__AL_tb_ac_deposits_171120211.sql                  
ALTER TABLE  tb_ac_bank_depositslip_master
CHANGE COLUMN  DPS_SLIPNO DPS_SLIPNO  VARCHAR(50) NOT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211117203400__AL_tb_ac_deposits_171120212.sql
ALTER TABLE  tb_ac_deposits
CHANGE COLUMN  DEP_NO DEP_NO VARCHAR(50) NOT NULL ;
