--liquibase formatted sql
--changeset Kanchan:V20230301174012__AL_Tb_SFAC_Fpo_Master_01032023.sql
Alter table Tb_SFAC_Fpo_Master
drop column BANK_NAME,drop column ACCOUNT_NO,drop column IFSC_CODE,drop column BRANCH,drop column FPO_CEO_NAME,
drop column FPO_CEO_DOB,drop column FPO_CEO_UIDNO,drop column FPO_CEO_MOBNO,drop column FPO_CEO_ADDRESS,
drop column FPO_CEO_EMAIL,drop column FPO_CA_NAME,drop column FPO_CA_DOB,drop column FPO_CA_UIDNO ,drop column FPO_CA_MOBNO,
drop column FPO_CA_ADDRESS,drop column FPO_CA_EMAIL,drop column FPO_ACCOUNTANT_NAME,drop column FPO_ACCOUNTANT_DOB,
drop column FPO_ACCOUNTANT_UIDNO,drop column FPO_ACCOUNTANT_MOBNO,drop column FPO_ACCOUNTANT_ADDRESS,
drop column FPO_ACCOUNTANT_EMAIL;