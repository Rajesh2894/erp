-- Add/modify columns 
alter table TB_CONTRACT_INSTALMENT_DETAIL add tm_taxid number(12);
-- Add comments to the columns 
comment on column TB_CONTRACT_INSTALMENT_DETAIL.tm_taxid
  is 'Tax Id from TB_TAX_MAST';
