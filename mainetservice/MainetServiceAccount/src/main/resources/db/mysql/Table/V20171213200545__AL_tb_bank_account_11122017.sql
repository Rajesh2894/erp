--liquibase formatted sql
--changeset nilima:V20171213200545__AL_tb_bank_account_111220172.sql
ALTER TABLE tb_bank_account
CHANGE COLUMN FI04_N1 FUNCTION_ID BIGINT(12) NULL DEFAULT NULL COMMENT 'ORGID' ,
CHANGE COLUMN FI04_V1 ORGID BIGINT(12) NULL DEFAULT NULL COMMENT 'Additional nvarchar2 FI04_V1 to be used in future' ;

  
--liquibase formatted sql
--changeset nilima:V20171213200545__AL_tb_bank_account_111220173.sql
ALTER TABLE tb_bank_account 
DROP COLUMN BA_OPEN_BAL_AMT,
DROP COLUMN BA_OPENBAL_DATE,
CHANGE COLUMN ORGID ORGID BIGINT(12) NOT NULL COMMENT 'Additional nvarchar2 FI04_V1 to be used in future' ,
CHANGE COLUMN FI04_D1 BANKID BIGINT(12) COMMENT 'Bank Id ';

--liquibase formatted sql
--changeset nilima:V20171213200545__AL_tb_bank_account_111220179.sql
update tb_bank_account  D set ORGID=(SELECT c.ORGID FROM 
									 tb_bank_master a,
   									tb_ulb_bank c
									where a.BANKID=c.BANKID
                                    and d.ulb_bankid=c.ULB_BANKID);
COMMIT;


--liquibase formatted sql
--changeset nilima:V20171213200545__AL_tb_bank_account_111220174.sql
update tb_bank_account  D set BANKID=(SELECT a.BANKID FROM 
									 tb_bank_master a,
   									tb_ulb_bank c
									where a.BANKID=c.BANKID
                                    and d.ulb_bankid=c.ULB_BANKID);

commit;

 
 

--liquibase formatted sql
--changeset nilima:V20171213200545__AL_tb_bank_account_111220175.sql
ALTER TABLE tb_bank_account
DROP FOREIGN KEY FK_AC_BANKID;

--liquibase formatted sql
--changeset nilima:V20171213200545__AL_tb_bank_account_1112201710.sql
ALTER TABLE tb_bank_account 
CHANGE COLUMN BANKID BANKID BIGINT(12) NULL COMMENT 'Bank Id ' ;

--liquibase formatted sql
--changeset nilima:V20171213200545__AL_tb_bank_account_1112201711.sql
ALTER TABLE tb_bank_account 
ADD CONSTRAINT FK_AC_BANKID
  FOREIGN KEY (BANKID)
  REFERENCES tb_bank_master (BANKID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  

--liquibase formatted sql
--changeset nilima:V20171213200545__AL_tb_bank_account_111220176.sql
ALTER TABLE tb_ac_chequebookleaf_mas 
DROP FOREIGN KEY FK_ULB_BANKID;
ALTER TABLE tb_ac_chequebookleaf_mas
DROP COLUMN ULB_BANKID,
DROP INDEX FK_BANK_ID ;

--liquibase formatted sql
--changeset nilima:V20171213200545__AL_tb_bank_account_111220171.sql
ALTER TABLE tb_bank_account 
DROP COLUMN ULB_BANKID,
DROP INDEX FK_ULB_BANKID_ACCOUNT ;


--liquibase formatted sql
--changeset nilima:V20171213200545__AL_tb_bank_account_11122017.sql
DROP TABLE tb_ulb_bank;
