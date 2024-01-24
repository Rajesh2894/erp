-- Drop columns 
alter table TB_AC_CHEQUEBOOKLEAF_MAS drop column lang_id;
alter table TB_AC_CHEQUEBOOKLEAF_MAS modify ulb_bankid not null;
alter table TB_AC_CHEQUEBOOKLEAF_MAS modify ba_accountid not null;
alter table TB_AC_CHEQUEBOOKLEAF_MAS modify rcpt_chqbook_date not null;
alter table TB_AC_CHEQUEBOOKLEAF_MAS rename column user_id to CREATED_BY;
alter table TB_AC_CHEQUEBOOKLEAF_MAS rename column lmoddate to CREATED_DATE;
alter table TB_AC_CHEQUEBOOKLEAF_MAS modify lg_ip_mac not null;

-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_AC_CHEQUEBOOKLEAF_MAS
  add constraint FK_ULB_BANKID foreign key (ULB_BANKID)
  references tb_ulb_bank (ULB_BANKID);
  
alter table TB_AC_CHEQUEBOOKLEAF_MAS
  add constraint FK_BA_ACCOUNTID foreign key (BA_ACCOUNTID)
  references tb_bank_account (BA_ACCOUNTID);