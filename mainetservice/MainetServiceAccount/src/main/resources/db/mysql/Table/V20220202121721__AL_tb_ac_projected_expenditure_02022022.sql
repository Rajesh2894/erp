--liquibase formatted sql
--changeset Kanchan:V20220202121721__AL_tb_ac_projected_expenditure_02022022.sql
ALTER TABLE  tb_ac_projected_expenditure ADD EXPECTED_ESTAMT decimal(15,2) AFTER REVISED_ESTAMT;
--liquibase formatted sql
--changeset Kanchan:V20220202121721__AL_tb_ac_projected_expenditure_020220221.sql
ALTER TABLE tb_ac_projectedrevenue ADD EXPECTED_ESTAMT decimal(15,2) AFTER REVISED_ESTAMT;
--liquibase formatted sql
--changeset Kanchan:V20220202121721__AL_tb_ac_projected_expenditure_020220222.sql
ALTER TABLE tb_ac_projected_expenditure ADD REMARK varchar(100) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20220202121721__AL_tb_ac_projected_expenditure_020220223.sql
ALTER TABLE tb_ac_projectedrevenue ADD REMARK varchar(100) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20220202121721__AL_tb_ac_projected_expenditure_020220224.sql
ALTER TABLE TB_AC_BUDGETORY_ESTIMATE ADD REMARK varchar(100) NULL DEFAULT NULL;
