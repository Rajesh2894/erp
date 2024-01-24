-- Drop columns 
alter table TB_RECEIPT_MAS drop column lang_id;
alter table TB_RECEIPT_MAS modify field_id null;
