-- Add/modify columns 
alter table TB_RECEIPT_DET modify rm_rcptid not null;
alter table TB_RECEIPT_DET modify rf_feeamount not null;
alter table TB_RECEIPT_DET modify created_by not null;
alter table TB_RECEIPT_DET modify created_date not null;
alter table TB_RECEIPT_DET modify lg_ip_mac not null;
alter table TB_RECEIPT_DET rename column rf_n2 to SAC_HEAD_ID;
alter table TB_RECEIPT_DET modify sac_head_id NUMBER(12);

-- Add/modify columns 
alter table TB_RECEIPT_DET modify sac_head_id not null;
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_RECEIPT_DET
  add constraint FK_DET_RM_RCPTID foreign key (RM_RCPTID)
  references tb_receipt_mas (RM_RCPTID);
