-- Add/modify columns 
alter table TB_AC_BILL_DEDUCTION_DETAIL rename column fi04_n1 to SAC_HEAD_ID;
alter table TB_AC_BILL_DEDUCTION_DETAIL modify sac_head_id NUMBER(12) not null;
-- Add comments to the columns 
comment on column TB_AC_BILL_DEDUCTION_DETAIL.sac_head_id
  is 'Account Headcode Id';
