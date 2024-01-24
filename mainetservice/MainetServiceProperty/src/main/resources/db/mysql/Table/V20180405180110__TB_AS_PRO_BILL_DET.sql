--liquibase formatted sql
--changeset nilima:V20180405180110__TB_AS_PRO_BILL_DET1.sql
DROP TABLE IF EXISTS TB_AS_PRO_BILL_DET;

--liquibase formatted sql
--changeset nilima:V20180405180110__TB_AS_PRO_BILL_DET2.sql
create table TB_AS_PRO_BILL_DET (
pro_bd_billdetid bigint(12)	 NOT NULL	  COMMENT '	Primary Id of this table	',
bm_idno bigint(12)	 NULL DEFAULT NULL 	  COMMENT '	Bill id of TB_AS_PRO_BILL_MAS	',
tax_id bigint(12)	 NOT NULL	  COMMENT '	tax_id	',
rebate_id bigint(12)	 NULL DEFAULT NULL 	  COMMENT '	rebate_id	',
adjustment_id bigint(12)	 NULL DEFAULT NULL 	  COMMENT '	adjustment_id	',
pro_bd_cur_taxamt decimal(15,2)	 NULL DEFAULT NULL 	  COMMENT '	Tax amount against this bill	',
pro_bd_cur_bal_taxamt decimal(15,2)	 NULL DEFAULT NULL 	  COMMENT '	Current year tax amount to be paid 	',
pro_bd_prv_bal_arramt decimal(15,2)	 NULL DEFAULT NULL 	  COMMENT '	Previous year arrear tax amount to be paid 	',
pro_bd_prv_arramt decimal(15,2)	 NULL DEFAULT NULL 	  COMMENT '	Arrear amount present in the previous billing  year if present	',
pro_bd_billflag varchar(1)	 NULL DEFAULT NULL 	  COMMENT '	This will hold B value for all taxes including outstation and dishonor when generated thru. bill package. When outstation and dishonor charges gets added in bill detail thru form, then these charges will hold O value	',
pro_bd_cur_taxamt_print decimal(15,2)	 NULL DEFAULT NULL 	  COMMENT '	Column to store pro_bd_curyr_tax_amt which will be used in Bill Reports Printing	',
orgid bigint(12)	 NOT NULL	  COMMENT '	Org ID	',
created_by bigint(12)	 NOT NULL	  COMMENT '	User ID	',
created_date datetime	 NOT NULL	  COMMENT '	Last Modification Date	',
updated_by bigint(12)	 NULL DEFAULT NULL 	  COMMENT '	User id who update the data	',
updated_date datetime	 NULL DEFAULT NULL 	  COMMENT '	Date on which data is going to update	',
lg_ip_mac varchar(100)	 NULL DEFAULT NULL 	  COMMENT '	Client Machines Login Name | IP Address | Physical Address	',
lg_ip_mac_upd varchar(100)	 NULL DEFAULT NULL 	  COMMENT '	Updated Client Machines Login Name | IP Address | Physical Address	',
tax_category bigint(12)	 NULL DEFAULT NULL 	  COMMENT '	TAX Category	',
coll_seq bigint(12)	 NULL DEFAULT NULL 	  COMMENT '	Collection sequence 	',
PRO_BM_FORMULA VARCHAR(500) NULL DEFAULT NULL COMMENT 'BRMS FORMULA',
PRO_BM_RULE VARCHAR(500) NULL DEFAULT NULL COMMENT 'BRMS RULE',
PRO_BM_BRMSTAX DECIMAL(12,2) NULL DEFAULT NULL COMMENT 'BRMS TAX');


--liquibase formatted sql
--changeset nilima:V20180405180110__TB_AS_PRO_BILL_DET3.sql
alter table TB_AS_PRO_BILL_DET add constraint PK_bd_billdetid primary key (pro_bd_billdetid);
--liquibase formatted sql
--changeset nilima:V20180405180110__TB_AS_PRO_BILL_DET4.sql
alter table TB_AS_PRO_BILL_DET add constraint FK_pro_bm_idno foreign key (bm_idno)
references  TB_AS_PRO_BILL_MAS (pro_bm_idno);