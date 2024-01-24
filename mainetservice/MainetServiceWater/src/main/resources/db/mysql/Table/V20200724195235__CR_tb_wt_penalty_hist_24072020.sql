--liquibase formatted sql
--changeset Anil:V20200724195235__CR_tb_wt_penalty_hist_24072020.sql
drop table if exists tb_wt_penalty_hist;
--liquibase formatted sql
--changeset Anil:V20200724195235__CR_tb_wt_penalty_hist_240720201.sql
CREATE TABLE  tb_wt_penalty_hist (
   H_WPENALTY_ID bigint(12) NOT NULL,
   WPENALTY_ID  bigint(12) NOT NULL,
   CS_IDN  varchar(40) DEFAULT NULL,
   ACTUAL_AMOUNT  decimal(12,2) DEFAULT NULL,
   PENDING_AMOUNT  decimal(12,2) DEFAULT NULL,
   FIN_YEARID  bigint(12) DEFAULT NULL,
   ACTIVE_FLAG  char(1) DEFAULT NULL,
   ORGID  bigint(12) DEFAULT NULL,
   CREATED_BY  bigint(12) DEFAULT NULL,
   CREATED_DATE  datetime DEFAULT NULL,
   UPDATED_BY  bigint(12) DEFAULT NULL,
   UPDATED_DATE  datetime DEFAULT NULL,
   LG_IP_MAC  varchar(100) DEFAULT NULL,
   LG_IP_MAC_UPD  varchar(100) DEFAULT NULL,
   TAX_ID  bigint(12) DEFAULT NULL,
   SUR_FROM_DATE  datetime DEFAULT NULL,
   SUR_TO_DATE  datetime DEFAULT NULL,
   ACTUAL_ARREAR_AMOUNT  decimal(12,2) DEFAULT NULL,
   BM_IDNO  bigint(12) NOT NULL,
   H_Status varchar(2)  DEFAULT NULL,
  PRIMARY KEY ( H_WPENALTY_ID )
) ;
