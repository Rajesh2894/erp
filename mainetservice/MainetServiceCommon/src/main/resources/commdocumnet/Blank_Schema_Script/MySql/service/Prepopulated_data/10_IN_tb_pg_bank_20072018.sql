/*
-- Query: select * from tb_pg_bank
-- Date: 2018-07-16 17:59
*/
INSERT INTO tb_pg_bank (PG_ID,MERCHANT_ID,PG_NAME,PG_URL,PG_STATUS,BANKID,BA_ACCOUNTID,
ORGID,CREATED_BY,CREATED_DATE,LANG_ID,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD,COMM_N1,COMM_V1,COMM_D1,COMM_LO1) 
VALUES (1,'T7120','HDFC','https://www.tekprocess.co.in/PaymentGateway/services/TransactionDetailsNew','A',96649,NULL,
1,1,now(),1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

INSERT INTO tb_pg_bank (PG_ID,MERCHANT_ID,PG_NAME,PG_URL,PG_STATUS,BANKID,BA_ACCOUNTID,
ORGID,CREATED_BY,CREATED_DATE,LANG_ID,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD,COMM_N1,COMM_V1,COMM_D1,COMM_LO1) 
VALUES (2,'C0Dr8m','PAYU','https://test.payu.in/_payment','A',512346,NULL,
1,1,now(),1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

INSERT INTO tb_pg_bank (PG_ID,MERCHANT_ID,PG_NAME,PG_URL,PG_STATUS,BANKID,BA_ACCOUNTID,
ORGID,CREATED_BY,CREATED_DATE,LANG_ID,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD,COMM_N1,COMM_V1,COMM_D1,COMM_LO1) 
VALUES (3,'3501','MAHAONLINE','http://testpg.mahaonlinegov.in/PGWAY/PaymentInitHTTPRequest.aspx','A','512348',NULL,
1,1,now(),1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
commit;

----------------------------------------------------------------- Query for 157 env --------------------------------------------------------------------------------------------------
INSERT INTO tb_pg_bank(PG_ID,BANKID,MERCHANT_ID,PG_NAME,PG_URL,PG_STATUS,BA_ACCOUNTID,ORGID,CREATED_BY,CREATED_DATE,LANG_ID,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD,COMM_N1,COMM_V1,COMM_D1,COMM_LO1) VALUES (4,96650,'188577','CCAvenue','https://test.ccavenue.com/transaction/transaction.do?command=initiateTransaction','A',null,1,1,'2018-09-17 00:00:00',1,null,null,null,null,null,null,null,null);

---------------------------------------------------------------- Query for 230 env --------------------------------------------------------------------------------------------------
INSERT INTO tb_pg_bank(PG_ID,BANKID,MERCHANT_ID,PG_NAME,PG_URL,PG_STATUS,BA_ACCOUNTID,ORGID,CREATED_BY,CREATED_DATE,LANG_ID,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD,COMM_N1,COMM_V1,COMM_D1,COMM_LO1) VALUES (4,96650,'188577','CCAvenue','https://test.ccavenue.com/transaction/transaction.do?command=initiateTransaction','A',null,1,1,'2018-09-14 00:00:00',1,null,null,null,null,null,null,null,null);

--------------------------------------------------------------- Query for cloud env --------------------------------------------------------------------------------------------------
INSERT INTO tb_pg_bank(PG_ID,BANKID,MERCHANT_ID,PG_NAME,PG_URL,PG_STATUS,BA_ACCOUNTID,ORGID,CREATED_BY,CREATED_DATE,LANG_ID,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD,COMM_N1,COMM_V1,COMM_D1,COMM_LO1) VALUES (4,96650,'188577','CCAvenue','https://test.ccavenue.com/transaction/transaction.do?command=initiateTransaction','A',null,1,1,'2018-09-14 00:00:00',1,null,null,null,null,null,null,null,null);
