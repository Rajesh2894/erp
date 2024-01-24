create table TB_AS_PRO_BILL_DET
( pro_bd_billdetid        BIGINT not null comment 'Primary Id of this table',
  bm_idno             BIGINT not null comment 'Bill id of TB_AS_BILL_MAS',
  tax_id              BIGINT not null comment 'Tax Id of TB_TAX_MAST',
  rebate_id           BIGINT comment 'Rebaite given' ,
  adjustment_id       BIGINT comment 'adjustment given',
  pro_bd_cur_taxamt       DECIMAL(15,2) not null comment 'Tax amount against current bill',
  pro_bd_cur_bal_taxamt   DECIMAL(15,2) comment 'Current year tax amount to be paid ',
  pro_bd_prv_bal_arramt   DECIMAL(15,2) comment 'Previous year arrear tax amount to be paid ',
  pro_bd_cur_arramt       DECIMAL(15,2) comment 'Arrear amount present in the current year if present',
  pro_bd_prv_arramt       DECIMAL(15,2) comment 'Arrear amount present in the previous billing  year if present',
  pro_bd_cur_bal_arramt   DECIMAL(15,2) comment 'Current year arrear tax amount to be paid ',
  pro_bd_billflag         VARCHAR(1) comment 'This will hold B value for all taxes including outstation and dishonor when generated thru. bill package. When outstation and dishonor charges gets added in bill detail thru form, then these charges will hold O value',
  pro_bd_cur_taxamt_print DECIMAL(15,2) 'Column to store pro_bd_curyr_tax_amt which will be used in Bill Reports Printing',
  orgid               BIGINT not null comment 'Org ID',
  created_by          BIGINT not null comment 'User ID',
  created_date        DATETIME not null comment 'Last Modification Date',
  updated_by          BIGINT comment 'User id who update the data',
  updated_date        DATETIME comment 'Date on which data is going to update',
  lg_ip_mac           VARCHAR(100) comment 'Client Machine��������s Login Name | IP Address | Physical Address',
  lg_ip_mac_upd       VARCHAR(100) comment 'Updated Client Machine��������s Login Name | IP Address | Physical Address',
  tax_category        BIGINT comment 'WTG prefix from tax mas ',
  coll_seq            BIGINT comment 'Collection sequence ');

alter table TB_AS_PRO_BILL_DET
  add constraint PK_AS_PRO_BILLDETID primary key (pro_bd_BILLDETID);
alter table TB_AS_PRO_BILL_DET
  add constraint FK_AS_PRO_BM_IDNO foreign key (BM_IDNO)
  references TB_AS_BILL_MAS (BM_IDNO);

  

;
;
;
;
;
;
;
;
;
