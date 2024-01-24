--liquibase formatted sql
--changeset jinea:V20180105113429__AL_LG_IP_MAC.sql
UPDATE tb_ac_advance SET lg_ip_mac='MH-0099/192.168.100.215/00-22-4D-9F-89-42' where lg_ip_mac is null;
commit;

--liquibase formatted sql
--changeset jinea:V20180105113429__AL_LG_IP_MAC1.sql
ALTER TABLE tb_ac_advance
CHANGE COLUMN lg_ip_mac LG_IP_MAC VARCHAR(100) NOT NULL COMMENT 'client machine\'s login name | ip address | physical address' ;

--liquibase formatted sql
--changeset jinea:V20180105113429__AL_LG_IP_MAC2.sql
UPDATE tb_ac_bill_deduction_detail SET LG_IP_MAC='CSI-LEN-907/192.168.100.118/00-23-24-61-3D-E2' where LG_IP_MAC is null;
commit;

--liquibase formatted sql
--changeset jinea:V20180105113429__AL_LG_IP_MAC3.sql
ALTER TABLE tb_ac_bill_deduction_detail 
CHANGE COLUMN LG_IP_MAC LG_IP_MAC VARCHAR(100) NOT NULL COMMENT 'client machine\'s login name | ip address | physical address' ;

--liquibase formatted sql
--changeset jinea:V20180105113429__AL_LG_IP_MAC4.sql
UPDATE tb_ac_bill_mas SET LG_IP_MAC='CSI-LEN-901/192.168.100.157/00-23-24-61-37-C9' where LG_IP_MAC is null;
commit;

--liquibase formatted sql
--changeset jinea:V20180105113429__AL_LG_IP_MAC5.sql
ALTER TABLE tb_ac_bill_mas 
CHANGE COLUMN LG_IP_MAC LG_IP_MAC VARCHAR(100) NOT NULL COMMENT 'client machine\'s login name | ip address | physical address' ;

--liquibase formatted sql
--changeset jinea:V20180105113429__AL_LG_IP_MAC6.sql
UPDATE tb_ac_budgetallocation SET LG_IP_MAC='MH-0099/192.168.100.215/00-22-4D-9F-89-42' where LG_IP_MAC is null;
commit;

--liquibase formatted sql
--changeset jinea:V20180105113429__AL_LG_IP_MAC7.sql
ALTER TABLE tb_ac_budgetallocation 
CHANGE COLUMN LG_IP_MAC LG_IP_MAC VARCHAR(100) NOT NULL COMMENT 'client machine\'s login name | ip address | physical address' ;

--liquibase formatted sql
--changeset jinea:V20180105113429__AL_LG_IP_MAC8.sql
UPDATE tb_ac_budgetallocation_hist SET LG_IP_MAC='MH-0099/192.168.100.215/00-22-4D-9F-89-42' where LG_IP_MAC is null;
commit;

--liquibase formatted sql
--changeset jinea:V20180105113429__AL_LG_IP_MAC9.sql
ALTER TABLE tb_ac_budgetallocation_hist 
CHANGE COLUMN LG_IP_MAC LG_IP_MAC VARCHAR(100) NOT NULL COMMENT 'client machine\'s login name | ip address | physical address' ;

--liquibase formatted sql
--changeset jinea:V20180105113429__AL_LG_IP_MAC10.sql
UPDATE tb_ac_chequebookleaf_det SET LG_IP_MAC='CSI-LEN-918/192.168.100.215/00-23-24-61-39-D7' where LG_IP_MAC is null;
commit;

--liquibase formatted sql
--changeset jinea:V20180105113429__AL_LG_IP_MAC11.sql
ALTER TABLE tb_ac_chequebookleaf_det 
CHANGE COLUMN LG_IP_MAC LG_IP_MAC VARCHAR(100) NOT NULL COMMENT 'client machine\'s login name | ip address | physical address' ;

--liquibase formatted sql
--changeset jinea:V20180105113429__AL_LG_IP_MAC12.sql
UPDATE tb_ac_deposits SET LG_IP_MAC='CSI-LEN-901/192.168.100.157/00-23-24-61-37-C9' where LG_IP_MAC is null;
commit;

--liquibase formatted sql
--changeset jinea:V20180105113429__AL_LG_IP_MAC13.sql
ALTER TABLE tb_ac_deposits 
CHANGE COLUMN LG_IP_MAC LG_IP_MAC VARCHAR(100) NOT NULL COMMENT 'client machine\'s login name | ip address | physical address' ;

--liquibase formatted sql
--changeset jinea:V20180105113429__AL_LG_IP_MAC14.sql
UPDATE tb_ac_payment_mas SET LG_IP_MAC='CSI-LEN-901/192.168.100.157/00-23-24-61-37-C9' where LG_IP_MAC is null;
commit;

--liquibase formatted sql
--changeset jinea:V20180105113429__AL_LG_IP_MAC15.sql
ALTER TABLE tb_ac_payment_mas 
CHANGE COLUMN LG_IP_MAC LG_IP_MAC VARCHAR(100) NOT NULL COMMENT 'client machine\'s login name | ip address | physical address' ;

--liquibase formatted sql
--changeset jinea:V20180105113429__AL_LG_IP_MAC16.sql
UPDATE tb_ac_projectedrevenue SET LG_IP_MAC='CSI-LEN-918/192.168.100.215/00-23-24-61-39-D7' where LG_IP_MAC is null;
commit;

--liquibase formatted sql
--changeset jinea:V20180105113429__AL_LG_IP_MAC17.sql
ALTER TABLE tb_ac_projectedrevenue 
CHANGE COLUMN LG_IP_MAC LG_IP_MAC VARCHAR(100) NOT NULL COMMENT 'client machine\'s login name | ip address | physical address' ;

--liquibase formatted sql
--changeset jinea:V20180105113429__AL_LG_IP_MAC18.sql
UPDATE tb_ac_projected_expenditure SET LG_IP_MAC='CSI-LEN-918/192.168.100.215/00-23-24-61-39-D7' where LG_IP_MAC is null;
commit;

--liquibase formatted sql
--changeset jinea:V20180105113429__AL_LG_IP_MAC19.sql
ALTER TABLE tb_ac_projected_expenditure 
CHANGE COLUMN LG_IP_MAC LG_IP_MAC VARCHAR(100) NOT NULL COMMENT 'client machine\'s login name | ip address | physical address' ;

--liquibase formatted sql
--changeset jinea:V20180105113429__AL_LG_IP_MAC20.sql
UPDATE tb_ac_tender_det SET LG_IP_MAC='MH-0099/192.168.100.215/00-22-4D-9F-89-42' where LG_IP_MAC is null;
commit;

--liquibase formatted sql
--changeset jinea:V20180105113429__AL_LG_IP_MAC21.sql
ALTER TABLE tb_ac_tender_det 
CHANGE COLUMN LG_IP_MAC LG_IP_MAC VARCHAR(100) NOT NULL COMMENT 'client machine\'s login name | ip address | physical address' ;

--liquibase formatted sql
--changeset jinea:V20180105113429__AL_LG_IP_MAC22.sql
UPDATE tb_ac_tender_master SET LG_IP_MAC='MH-0099/192.168.100.215/00-22-4D-9F-89-42' where LG_IP_MAC is null;
commit;

--liquibase formatted sql
--changeset jinea:V20180105113429__AL_LG_IP_MAC23.sql
ALTER TABLE tb_ac_tender_master 
CHANGE COLUMN LG_IP_MAC LG_IP_MAC VARCHAR(100) NOT NULL COMMENT 'client machine\'s login name | ip address | physical address' ;

--liquibase formatted sql
--changeset jinea:V20180105113429__AL_LG_IP_MAC24.sql
UPDATE tb_bank_account SET LG_IP_MAC='CSI-LEN-918/192.168.100.215/00-23-24-61-39-D7' where LG_IP_MAC is null;
commit;

--liquibase formatted sql
--changeset jinea:V20180105113429__AL_LG_IP_MAC25.sql
ALTER TABLE tb_bank_account 
CHANGE COLUMN LG_IP_MAC LG_IP_MAC VARCHAR(100) NOT NULL COMMENT 'client machine\'s login name | ip address | physical address' ;


