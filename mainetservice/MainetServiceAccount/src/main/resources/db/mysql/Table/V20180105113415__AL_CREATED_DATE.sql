--liquibase formatted sql
--changeset jinea:V20180105113415__AL_CREATED_DATE.sql
UPDATE tb_ac_budgetallocation_hist SET CREATED_DATE=NOW() WHERE CREATED_DATE IS NULL;
COMMIT;

--liquibase formatted sql
--changeset jinea:V20180105113415__AL_CREATED_DATE1.sql
ALTER TABLE tb_ac_budgetallocation_hist 
CHANGE COLUMN CREATED_DATE CREATED_DATE DATETIME NOT NULL COMMENT 'record creation date';

--liquibase formatted sql
--changeset jinea:V20180105113415__AL_CREATED_DATE2.sql
UPDATE tb_ac_payment_mas SET CREATED_DATE=NOW() WHERE CREATED_DATE IS NULL;
COMMIT;

--liquibase formatted sql
--changeset jinea:V20180105113415__AL_CREATED_DATE3.sql
ALTER TABLE tb_ac_payment_mas 
CHANGE COLUMN CREATED_DATE CREATED_DATE DATETIME NOT NULL COMMENT 'record creation date';