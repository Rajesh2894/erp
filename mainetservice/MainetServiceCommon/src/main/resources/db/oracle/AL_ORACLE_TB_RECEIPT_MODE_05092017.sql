-- Drop columns 
alter table TB_RECEIPT_MODE drop column lang_id;
alter table TB_RECEIPT_MODE drop column budgetcode_id;
alter table TB_RECEIPT_MODE modify rm_rcptid not null;
alter table TB_RECEIPT_MODE modify cpd_feemode not null;
alter table TB_RECEIPT_MODE modify created_by not null;
alter table TB_RECEIPT_MODE modify created_date not null;
alter table TB_RECEIPT_MODE modify lg_ip_mac not null;
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_RECEIPT_MODE
  add constraint FK_MOD_RCPT_ID foreign key (RM_RCPTID)
  references tb_receipt_mas (RM_RCPTID);
