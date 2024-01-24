-- Drop columns 
alter table TB_AC_BILL_EXP_DETAIL drop column lang_id;
alter table TB_AC_BILL_EXP_DETAIL modify lg_ip_mac not null;
alter table TB_AC_BILL_EXP_DETAIL rename column fi04_n1 to SAC_HEAD_ID;
alter table TB_AC_BILL_EXP_DETAIL modify sac_head_id NUMBER(12) not null;
-- Add comments to the columns 
comment on column TB_AC_BILL_EXP_DETAIL.sac_head_id
  is 'Account Headcode Id';