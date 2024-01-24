-- Drop columns 
alter table TB_AC_CHEQUEBOOKLEAF_DET drop column lang_id;
alter table TB_AC_CHEQUEBOOKLEAF_DET modify chequebook_id not null;
alter table TB_AC_CHEQUEBOOKLEAF_DET modify cpd_idstatus not null;
alter table TB_AC_CHEQUEBOOKLEAF_DET modify lg_ip_mac not null;
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_AC_CHEQUEBOOKLEAF_DET
  add constraint FK_CHEQUEBOOK_ID foreign key (CHEQUEBOOK_ID)
  references tb_ac_chequebookleaf_mas (CHEQUEBOOK_ID);