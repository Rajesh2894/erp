--liquibase formatted sql
--changeset jinea:V20180105113352__AL_CREATED_BY.sql
ALTER TABLE tb_ac_bill_deduction_detail 
CHANGE COLUMN CREATED_BY CREATED_BY BIGINT(12) NOT NULL COMMENT 'user id who created the record' ;

--liquibase formatted sql
--changeset jinea:V20180105113352__AL_CREATED_BY1.sql
ALTER TABLE tb_ac_bill_exp_detail 
CHANGE COLUMN CREATED_BY CREATED_BY BIGINT(12) NOT NULL COMMENT 'user id who created the record' ;

--liquibase formatted sql
--changeset jinea:V20180105113352__AL_CREATED_BY2.sql
ALTER TABLE tb_ac_budgetallocation_hist 
CHANGE COLUMN CREATED_BY CREATED_BY BIGINT(12) NOT NULL COMMENT 'user id who created the record' ;

--liquibase formatted sql
--changeset jinea:V20180105113352__AL_CREATED_BY3.sql
ALTER TABLE tb_ac_codingstructure_det 
CHANGE COLUMN CREATED_BY CREATED_BY BIGINT(12) NOT NULL COMMENT 'user id who created the record' ;

--liquibase formatted sql
--changeset jinea:V20180105113352__AL_CREATED_BY4.sql
update tb_ac_payment_mas pm set pm.CREATED_BY=(select e.empid from employee e where e.EMPNAME="MBA" and e.orgid=pm.orgid) 
where pm.CREATED_BY is null;
commit;

--liquibase formatted sql
--changeset jinea:V20180105113352__AL_CREATED_BY5.sql
ALTER TABLE tb_ac_payment_mas 
CHANGE COLUMN CREATED_BY CREATED_BY BIGINT(12) NOT NULL COMMENT 'user id who created the record';

--liquibase formatted sql
--changeset jinea:V20180105113352__AL_CREATED_BY6.sql
ALTER TABLE tb_bank_account 
CHANGE COLUMN CREATED_BY CREATED_BY BIGINT(12) NOT NULL COMMENT 'user id who created the record';