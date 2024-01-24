--liquibase formatted sql
--changeset Anil:V20191016170020__CR_tb_as_temp_pro_bill_det_16102019.sql
drop table if exists tb_as_temp_pro_bill_det;
--liquibase formatted sql
--changeset Anil:V20191016170020__CR_tb_as_temp_pro_bill_det_161020191.sql
CREATE TABLE tb_as_temp_pro_bill_det(
  temp_pro_bd_billdetid bigint(12) NOT NULL COMMENT 'Primary Id of this table',
  bm_idno bigint(12) DEFAULT NULL COMMENT 'Bill id of TB_AS_temp_pro_BILL_MAS',
  tax_id bigint(12) NOT NULL COMMENT 'tax_id',
  rebate_id bigint(12) DEFAULT NULL COMMENT 'rebate_id',
  adjustment_id bigint(12) DEFAULT NULL COMMENT 'adjustment_id',
  temp_pro_bd_cur_taxamt decimal(15,2) DEFAULT NULL COMMENT 'Tax amount against this bill',
  temp_pro_bd_cur_bal_taxamt decimal(15,2) DEFAULT NULL COMMENT 'Current year tax amount to be paid ',
  temp_pro_bd_prv_bal_arramt decimal(15,2) DEFAULT NULL COMMENT 'Previous year arrear tax amount to be paid ',
  temp_pro_bd_prv_arramt decimal(15,2) DEFAULT NULL COMMENT 'Arrear amount present in the previous billing  year if present',
  temp_pro_bd_billflag varchar(1) DEFAULT NULL COMMENT 'This will hold B value for all taxes including outstation and dishonor when generated thru. bill package. When outstation and dishonor charges gets added in bill detail thru form, then these charges will hold O value',
  temp_pro_bd_cur_taxamt_print decimal(15,2) DEFAULT NULL COMMENT 'Column to store temp_pro_bd_curyr_tax_amt which will be used in Bill Reports Printing',
  temp_pro_BM_FORMULA varchar(500) DEFAULT NULL COMMENT 'BRMS FORMULA',
  temp_pro_BM_RULE varchar(500) DEFAULT NULL COMMENT 'BRMS RULE',
  temp_pro_BM_BRMSTAX decimal(12,2) DEFAULT NULL COMMENT 'BRMS TAX',
  temp_pro_bd_cur_taxamt_auth decimal(15,2) DEFAULT NULL,
  temp_pro_bd_cur_bal_taxamt_auth decimal(15,2) DEFAULT NULL,
  temp_pro_bd_prv_bal_arramt_auth decimal(15,2) DEFAULT NULL,
  temp_pro_bd_prv_arramt_auth decimal(15,2) DEFAULT NULL,
  orgid bigint(12) NOT NULL COMMENT 'Org ID',
  created_by bigint(12) NOT NULL COMMENT 'User ID',
  created_date datetime NOT NULL COMMENT 'Last Modification Date',
  updated_by bigint(12) DEFAULT NULL COMMENT 'User id who update the data',
  updated_date datetime DEFAULT NULL COMMENT 'Date on which data is going to update',
  lg_ip_mac varchar(100) DEFAULT NULL COMMENT 'Client Machines Login Name | IP Address | Physical Address',
  lg_ip_mac_upd varchar(100) DEFAULT NULL COMMENT 'Updated Client Machines Login Name | IP Address | Physical Address',
  tax_category bigint(12) DEFAULT NULL COMMENT 'TAX Category',
  coll_seq bigint(12) DEFAULT NULL COMMENT 'Collection sequence ',
  PRIMARY KEY (temp_pro_bd_billdetid),
  KEY FK_temp_pro_bm_idno (bm_idno),
  KEY INDX_temp_pro_BILL_BM_IDNO (bm_idno),
  KEY INDX_temp_pro_BILL_TAX_ID (tax_id),
  KEY INDX_temp_pro_BILL_ORGID (orgid),
  KEY INDX_temp_pro_BILL_TAX_CATEGORY (tax_category),
  KEY INDX_temp_pro_BILL_COLL_SEQ (coll_seq),
  CONSTRAINT FK_temp_pro_bm_idno FOREIGN KEY (bm_idno) REFERENCES tb_as_temp_pro_bill_mas (temp_pro_bm_idno)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
