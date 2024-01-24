create table TB_AS_BILL_DET
( bd_billdetid        BIGINT not null,
  bm_idno             BIGINT not null,
  tax_id              BIGINT not null,
  rebate_id           BIGINT,
  adjustment_id       BIGINT,
  bd_cur_taxamt       DECIMAL(15,2) not null,
  bd_cur_bal_taxamt   DECIMAL(15,2),
  bd_prv_bal_arramt   DECIMAL(15,2),
  bd_cur_arramt       DECIMAL(15,2),
  bd_prv_arramt       DECIMAL(15,2),
  bd_cur_bal_arramt   DECIMAL(15,2),
  bd_billflag         VARCHAR(1),
  bd_cur_taxamt_print DECIMAL(15,2),
  orgid               BIGINT not null,
  created_by          BIGINT not null,
  created_date        DATETIME not null,
  updated_by          BIGINT,
  updated_date        DATETIME,
  lg_ip_mac           VARCHAR(100),
  lg_ip_mac_upd       VARCHAR(100),
  tax_category        BIGINT,
  coll_seq            BIGINT);

 alter table TB_AS_BILL_DET
  add constraint PK_AS_BILLDETID primary key (BD_BILLDETID);
alter table TB_AS_BILL_DET
  add constraint FK_AS_BM_IDNO foreign key (BM_IDNO)
  references TB_AS_BILL_MAS (BM_IDNO);
  
  
comment on table TB_AS_BILL_DET
  is 'Detail table used for water bill generation.';
comment on column TB_AS_BILL_DET.bd_billdetid
  is 'Primary Id of this table';
comment on column TB_AS_BILL_DET.bm_idno
  is 'Bill id of TB_WT_BILL_MAS';
comment on column TB_AS_BILL_DET.tax_id
  is 'Tax Id of TB_WT_TAX_MAS';
comment on column TB_AS_BILL_DET.bd_cur_taxamt
  is 'Tax amount against this bill';
comment on column TB_AS_BILL_DET.bd_cur_bal_taxamt
  is 'Current year tax amount to be paid ';
comment on column TB_AS_BILL_DET.bd_prv_bal_arramt
  is 'Previous year arrear tax amount to be paid ';
comment on column TB_AS_BILL_DET.bd_cur_arramt
  is 'Arrear amount present in the current year if present';
comment on column TB_AS_BILL_DET.bd_prv_arramt
  is 'Arrear amount present in the previous billing  year if present';
comment on column TB_AS_BILL_DET.bd_cur_bal_arramt
  is 'Current year arrear tax amount to be paid ';
comment on column TB_AS_BILL_DET.bd_billflag
  is 'This will hold B value for all taxes including outstation and dishonor when generated thru. bill package. When outstation and dishonor charges gets added in bill detail thru form, then these charges will hold O value';
comment on column TB_AS_BILL_DET.bd_cur_taxamt_print
  is 'Column to store bd_curyr_tax_amt which will be used in Bill Reports Printing';
comment on column TB_AS_BILL_DET.orgid
  is 'Org ID';
comment on column TB_AS_BILL_DET.created_by
  is 'User ID';
comment on column TB_AS_BILL_DET.created_date
  is 'Last Modification Date';
comment on column TB_AS_BILL_DET.updated_by
  is 'User id who update the data';
comment on column TB_AS_BILL_DET.updated_date
  is 'Date on which data is going to update';
comment on column TB_AS_BILL_DET.lg_ip_mac
  is 'Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
comment on column TB_AS_BILL_DET.lg_ip_mac_upd
  is 'Updated Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
comment on column TB_AS_BILL_DET.tax_category
  is 'WTG prefix from tax mas ';
comment on column TB_AS_BILL_DET.coll_seq
  is 'Collection sequence ';
