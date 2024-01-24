--liquibase formatted sql
--changeset Anil:V20191019101625__CR_tb_adh_bill_mast_19102019.sql
drop table if exists tb_adh_bill_mast;
--liquibase formatted sql
--changeset Anil:V20191019101625__CR_tb_adh_bill_mast_191020191.sql
CREATE TABLE tb_adh_bill_mast(
bm_bmno bigint(12) NOT NULL COMMENT 'Primary Key',
cont_id bigint(19) NOT NULL,
bm_billdate date NOT NULL COMMENT 'Bill Date (Same as Installment Date)',
bm_amount decimal(15,2) NOT NULL COMMENT 'Installment Amount',
bm_paid_amt decimal(15,2) DEFAULT NULL COMMENT 'Installment Paid Amount',
bm_balance_amt decimal(15,2) DEFAULT NULL COMMENT 'Installment Balance Amount',
bm_paid_flag char(1) NOT NULL DEFAULT 'N' COMMENT 'Bill Paid Flag',
bm_active char(1) NOT NULL COMMENT 'Active Bill',
orgid bigint(19) NOT NULL,
created_by bigint(19) NOT NULL,
updated_by bigint(19) DEFAULT NULL,
updated_date datetime DEFAULT NULL COMMENT 'date on which data is going to update',
lg_ip_mac varchar(100) DEFAULT NULL COMMENT 'client machine?s login name | ip address | physical address',
lg_ip_mac_upd varchar(100) DEFAULT NULL COMMENT 'updated client machine?s login name | ip address | physical address',
conit_id bigint(19) NOT NULL,
bm_billno bigint(12) DEFAULT NULL COMMENT 'Bill no',
bm_due_date date DEFAULT NULL COMMENT 'Bill due Date Flag',
bm_paymnet_date date DEFAULT NULL COMMENT 'Bill Payment Date',
Created_date date DEFAULT NULL,
TAX_ID bigint(19) NOT NULL,
bm_start_date date DEFAULT NULL COMMENT 'Bill Start Date',
bm_type char(1) DEFAULT NULL COMMENT 'null ->Installment ''I'' -> Interest',
bm_remark varchar(45) DEFAULT NULL COMMENT 'Remark',
PRIMARY KEY(bm_bmno)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
