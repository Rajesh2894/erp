-- Drop columns 
alter table TB_AC_DEPOSITS drop column lang_id;
alter table TB_AC_DEPOSITS drop column budgetcode_id;
alter table TB_AC_DEPOSITS modify sac_head_id not null;
-- Drop primary, unique and foreign key constraints 
alter table TB_AC_DEPOSITS
  drop constraint FK_DEP_BUGDCODE;