Insert into tb_tax_mas
( TAX_ID,TAX_DESC ,TAX_METHOD ,PARENT_CODE,TAX_GROUP,TAX_PRINT_ON1 ,TAX_CODE,
  TAX_DISPLAY_SEQ,DP_DEPTID, COLL_SEQ ,ORGID ,CREATED_BY ,CREATED_DATE,TAX_CATEGORY1 ,TAX_CATEGORY2 , TAX_APPLICABLE ,SM_SERVICE_ID ,TAX_PRINT_ON2 ,TAX_PRINT_ON3 ,tax_desc_id ,Tax_Active ,
  LG_IP_MAC)  values (fn_java_sq_generation('COM','TB_TAX_MAS','TAX_ID',NULL,NULL),'Additional Water Tax',380,2059,93,81,'AS19',28,7,28,176,1044,now(),883,908,50,NULL,NULL,83,40731,'Y','27.56.223.120');
Insert into tb_tax_mas
( TAX_ID,TAX_DESC ,TAX_METHOD ,PARENT_CODE,TAX_GROUP,TAX_PRINT_ON1 ,TAX_CODE,
  TAX_DISPLAY_SEQ,DP_DEPTID, COLL_SEQ ,ORGID ,CREATED_BY ,CREATED_DATE,TAX_CATEGORY1 ,TAX_CATEGORY2 , TAX_APPLICABLE ,SM_SERVICE_ID ,TAX_PRINT_ON2 ,TAX_PRINT_ON3 ,tax_desc_id ,Tax_Active ,
  LG_IP_MAC)  values (fn_java_sq_generation('COM','TB_TAX_MAS','TAX_ID',NULL,NULL),'Rebate for Self Occupied',380,2059,93,81,'AS20',29,7,29,176,1044,now(),886,894,50,NULL,NULL,83,40606,'Y','27.56.223.120');
Insert into tb_tax_mas
( TAX_ID,TAX_DESC ,TAX_METHOD ,PARENT_CODE,TAX_GROUP,TAX_PRINT_ON1 ,TAX_CODE,
  TAX_DISPLAY_SEQ,DP_DEPTID, COLL_SEQ ,ORGID ,CREATED_BY ,CREATED_DATE,TAX_CATEGORY1 ,TAX_CATEGORY2 , TAX_APPLICABLE ,SM_SERVICE_ID ,TAX_PRINT_ON2 ,TAX_PRINT_ON3 ,tax_desc_id ,Tax_Active ,
  LG_IP_MAC)  values (fn_java_sq_generation('COM','TB_TAX_MAS','TAX_ID',NULL,NULL),'Exemption on Property Tax',380,2059,93,81,'AS21',30,7,30,176,1044,now(),886,894,50,NULL,NULL,83,40734,'Y','27.56.223.120');
Insert into tb_tax_mas
( TAX_ID,TAX_DESC ,TAX_METHOD ,PARENT_CODE,TAX_GROUP,TAX_PRINT_ON1 ,TAX_CODE,
  TAX_DISPLAY_SEQ,DP_DEPTID, COLL_SEQ ,ORGID ,CREATED_BY ,CREATED_DATE,TAX_CATEGORY1 ,TAX_CATEGORY2 , TAX_APPLICABLE ,SM_SERVICE_ID ,TAX_PRINT_ON2 ,TAX_PRINT_ON3 ,tax_desc_id ,Tax_Active ,
  LG_IP_MAC)  values (fn_java_sq_generation('COM','TB_TAX_MAS','TAX_ID',NULL,NULL),'Interest',380,NULL,93,81,'AS22',31,7,31,176,1044,now(),884,912,50,NULL,NULL,83,40733,'Y','27.56.223.120');
Insert into tb_tax_mas
( TAX_ID,TAX_DESC ,TAX_METHOD ,PARENT_CODE,TAX_GROUP,TAX_PRINT_ON1 ,TAX_CODE,
  TAX_DISPLAY_SEQ,DP_DEPTID, COLL_SEQ ,ORGID ,CREATED_BY ,CREATED_DATE,TAX_CATEGORY1 ,TAX_CATEGORY2 , TAX_APPLICABLE ,SM_SERVICE_ID ,TAX_PRINT_ON2 ,TAX_PRINT_ON3 ,tax_desc_id ,Tax_Active ,
  LG_IP_MAC)  values (fn_java_sq_generation('COM','TB_TAX_MAS','TAX_ID',NULL,NULL),'Transfer Fee',378,NULL,94,81,'AS23',28,7,28,176,1044,now(),887,895,48,7053,NULL,83,42326,'Y','27.56.223.120');
Insert into tb_tax_mas
( TAX_ID,TAX_DESC ,TAX_METHOD ,PARENT_CODE,TAX_GROUP,TAX_PRINT_ON1 ,TAX_CODE,
  TAX_DISPLAY_SEQ,DP_DEPTID, COLL_SEQ ,ORGID ,CREATED_BY ,CREATED_DATE,TAX_CATEGORY1 ,TAX_CATEGORY2 , TAX_APPLICABLE ,SM_SERVICE_ID ,TAX_PRINT_ON2 ,TAX_PRINT_ON3 ,tax_desc_id ,Tax_Active ,
  LG_IP_MAC)  values (fn_java_sq_generation('COM','TB_TAX_MAS','TAX_ID',NULL,NULL),'Application Charge',378,NULL,94,81,'AS24',29,7,29,176,1044,now(),887,895,48,7033,NULL,83,144,'Y','27.56.223.120');
commit;

Update tb_tax_mas set tax_Desc='General Water Tax' ,tax_desc='General Water Tax'  where tax_id=5718;
commit;

update _sequences set next=25 where name=
(select SQ_SEQ_NAME from tb_seq_generation l where l.SQ_TBL_NAME='tb_tax_mas' and l.SQ_FLD_NAME='TAX_ID' and l.sq_orgid=176);
commit;

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),
(select tax_id  from tb_tax_mas where tax_code='AS19' and orgid=176),430,'A',176,1044,'2020-03-18 12:41:55',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),
(select tax_id  from tb_tax_mas where tax_code='AS19' and orgid=176),436,'A',176,1044,'2020-03-18 12:41:55',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),
(select tax_id  from tb_tax_mas where tax_code='AS19' and orgid=176),40215,'A',176,1044,'2020-03-18 12:41:56',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),
(select tax_id  from tb_tax_mas where tax_code='AS20' and orgid=176),430,'A',176,1044,'2020-03-18 12:43:36',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),
(select tax_id  from tb_tax_mas where tax_code='AS20' and orgid=176),436,'A',176,1044,'2020-03-18 12:43:36',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),
(select tax_id  from tb_tax_mas where tax_code='AS20' and orgid=176),40215,'A',176,1044,'2020-03-18 12:43:36',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS21' and orgid=176),430,'A',176,1044,'2020-03-18 12:46:05',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS21' and orgid=176),436,'A',176,1044,'2020-03-18 12:46:05',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS21' and orgid=176),40215,'A',176,1044,'2020-03-18 12:46:05',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS22' and orgid=176),430,'A',176,1044,'2020-03-18 12:47:19',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS22' and orgid=176),436,'A',176,1044,'2020-03-18 12:47:19',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS22' and orgid=176),40215,'A',176,1044,'2020-03-18 12:47:19',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS23' and orgid=176),430,'A',176,1044,'2020-03-18 12:47:59',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS23' and orgid=176),436,'A',176,1044,'2020-03-18 12:47:59',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS23' and orgid=176),40215,'A',176,1044,'2020-03-18 12:47:59',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS24' and orgid=176),430,'A',176,1044,'2020-03-18 12:48:40',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS24' and orgid=176),436,'A',176,1044,'2020-03-18 12:48:40',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS24' and orgid=176),40215,'A',176,1044,'2020-03-18 12:48:40',NULL,NULL,'192.168.33.102',NULL);

commit;
