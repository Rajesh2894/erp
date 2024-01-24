-- Add/modify columns 
alter table TB_TAX_MAS add tax_desc_id NUMBER(12);
-- Add comments to the columns 
comment on column TB_TAX_MAS.tax_desc_id
  is 'Tax Desc Id from ''TXN'' prefix';
