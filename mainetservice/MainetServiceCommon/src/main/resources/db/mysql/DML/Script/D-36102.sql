INSERT INTO tb_tax_mas (TAX_ID,TAX_DESC,TAX_METHOD,TAX_VALUE_TYPE,PARENT_CODE,TAX_GROUP,TAX_PRINT_ON1,TAX_CODE,TAX_DISPLAY_SEQ,DP_DEPTID,COLL_MTD,COLL_SEQ,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,TAX_CATEGORY1,TAX_CATEGORY2,TAX_CATEGORY3,TAX_CATEGORY4,TAX_CATEGORY5,TAX_APPLICABLE,SM_SERVICE_ID,TAX_PRINT_ON2,TAX_PRINT_ON3,tax_desc_id,Tax_Active,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_MAS','TAX_ID',NULL,NULL),'Property Tax','380',NULL,NULL,'93','81','AS12',27,7,NULL,27,164,484,'2020-02-15 14:38:20',484,'2020-02-15 14:38:20',883,908,NULL,NULL,NULL,50,NULL,NULL,'83',154,'Y','203.109.97.194',NULL);


INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS12' and orgid=164),430,'A',164,484,'2020-02-15 14:38:20',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS12' and orgid=164),436,'A',164,484,'2020-02-15 14:38:20',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS12' and orgid=164),40215,'A',164,484,'2020-02-15 14:38:20',NULL,NULL,'192.168.33.102',NULL);


INSERT INTO tb_tax_mas (TAX_ID,TAX_DESC,TAX_METHOD,TAX_VALUE_TYPE,PARENT_CODE,TAX_GROUP,TAX_PRINT_ON1,TAX_CODE,TAX_DISPLAY_SEQ,DP_DEPTID,COLL_MTD,COLL_SEQ,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,TAX_CATEGORY1,TAX_CATEGORY2,TAX_CATEGORY3,TAX_CATEGORY4,TAX_CATEGORY5,TAX_APPLICABLE,SM_SERVICE_ID,TAX_PRINT_ON2,TAX_PRINT_ON3,tax_desc_id,Tax_Active,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('COM','TB_TAX_MAS','TAX_ID',NULL,NULL),'Consolidate Tax','380',NULL,NULL,'93','81','AS13',28,7,NULL,28,164,484,'2020-02-15 14:38:32',484,'2020-02-15 14:38:32',883,908,NULL,NULL,NULL,50,NULL,NULL,'83',157,'Y','203.109.97.194',NULL);


update tb_tax_mas set PARENT_CODE=(select tax_id  from tb_tax_mas where tax_code='AS12' and orgid=164) WHERE TAX_ID=
(select tax_id  from tb_tax_mas where tax_code='AS13' and orgid=164);


INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS13' and orgid=164),430,'A',164,484,'2020-02-15 14:38:32',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS13' and orgid=164),436,'A',164,484,'2020-02-15 14:38:32',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS13' and orgid=164),40215,'A',164,484,'2020-02-15 14:38:32',NULL,NULL,'192.168.33.102',NULL);


INSERT INTO tb_tax_mas (TAX_ID,TAX_DESC,TAX_METHOD,TAX_VALUE_TYPE,PARENT_CODE,TAX_GROUP,TAX_PRINT_ON1,TAX_CODE,TAX_DISPLAY_SEQ,DP_DEPTID,COLL_MTD,COLL_SEQ,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,TAX_CATEGORY1,TAX_CATEGORY2,TAX_CATEGORY3,TAX_CATEGORY4,TAX_CATEGORY5,TAX_APPLICABLE,SM_SERVICE_ID,TAX_PRINT_ON2,TAX_PRINT_ON3,tax_desc_id,Tax_Active,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_MAS','TAX_ID',NULL,NULL),'Education Cess','380',NULL,NULL,'93','81','AS14',29,7,NULL,29,164,484,'2020-02-15 14:38:44',484,'2020-02-15 14:38:44',883,908,NULL,NULL,NULL,50,NULL,NULL,'83',160,'Y','203.109.97.194',NULL);


update tb_tax_mas set PARENT_CODE=(select tax_id  from tb_tax_mas where tax_code='AS12' and orgid=164) WHERE TAX_ID=
(select tax_id  from tb_tax_mas where tax_code='AS14' and orgid=164);



INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS14' and orgid=164),430,'A',164,484,'2020-02-15 14:38:44',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS14' and orgid=164),436,'A',164,484,'2020-02-15 14:38:44',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS14' and orgid=164),40215,'A',164,484,'2020-02-15 14:38:44',NULL,NULL,'192.168.33.102',NULL);


INSERT INTO tb_tax_mas (TAX_ID,TAX_DESC,TAX_METHOD,TAX_VALUE_TYPE,PARENT_CODE,TAX_GROUP,TAX_PRINT_ON1,TAX_CODE,TAX_DISPLAY_SEQ,DP_DEPTID,COLL_MTD,COLL_SEQ,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,TAX_CATEGORY1,TAX_CATEGORY2,TAX_CATEGORY3,TAX_CATEGORY4,TAX_CATEGORY5,TAX_APPLICABLE,SM_SERVICE_ID,TAX_PRINT_ON2,TAX_PRINT_ON3,tax_desc_id,Tax_Active,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_MAS','TAX_ID',NULL,NULL),'Interest','380',NULL,NULL,'93','81','AS15',30,7,NULL,30,164,484,'2020-02-15 14:38:55',484,'2020-02-15 14:38:55',884,912,NULL,NULL,NULL,50,NULL,NULL,'83',40733,'Y','203.109.97.194',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS15' and orgid=164),430,'A',164,484,'2020-02-15 14:38:55',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS15' and orgid=164),436,'A',164,484,'2020-02-15 14:38:55',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS15' and orgid=164),40215,'A',164,484,'2020-02-15 14:38:56',NULL,NULL,'192.168.33.102',NULL);



INSERT INTO tb_tax_mas (TAX_ID,TAX_DESC,TAX_METHOD,TAX_VALUE_TYPE,PARENT_CODE,TAX_GROUP,TAX_PRINT_ON1,TAX_CODE,TAX_DISPLAY_SEQ,DP_DEPTID,COLL_MTD,COLL_SEQ,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,TAX_CATEGORY1,TAX_CATEGORY2,TAX_CATEGORY3,TAX_CATEGORY4,TAX_CATEGORY5,TAX_APPLICABLE,SM_SERVICE_ID,TAX_PRINT_ON2,TAX_PRINT_ON3,tax_desc_id,Tax_Active,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_MAS','TAX_ID',NULL,NULL),'Penalty','380',NULL,NULL,'93','81','AS16',31,7,NULL,31,164,484,'2020-02-15 14:39:07',484,'2020-02-15 14:39:07',885,889,NULL,NULL,NULL,50,NULL,NULL,'83',147,'Y','203.109.97.194',NULL);

update tb_tax_mas set PARENT_CODE=(select tax_id  from tb_tax_mas where tax_code='AS12' and orgid=164) WHERE TAX_ID=
(select tax_id  from tb_tax_mas where tax_code='AS16' and orgid=164);



INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS12' and orgid=164),430,'A',164,484,'2020-02-15 14:39:08',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS12' and orgid=164),436,'A',164,484,'2020-02-15 14:39:08',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS12' and orgid=164),40215,'A',164,484,'2020-02-15 14:39:08',NULL,NULL,'192.168.33.102',NULL);


INSERT INTO tb_tax_mas (TAX_ID,TAX_DESC,TAX_METHOD,TAX_VALUE_TYPE,PARENT_CODE,TAX_GROUP,TAX_PRINT_ON1,TAX_CODE,TAX_DISPLAY_SEQ,DP_DEPTID,COLL_MTD,COLL_SEQ,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,TAX_CATEGORY1,TAX_CATEGORY2,TAX_CATEGORY3,TAX_CATEGORY4,TAX_CATEGORY5,TAX_APPLICABLE,SM_SERVICE_ID,TAX_PRINT_ON2,TAX_PRINT_ON3,tax_desc_id,Tax_Active,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_MAS','TAX_ID',NULL,NULL),'Early Payment Discount','380',NULL,13409,'93','81','AS17',32,7,NULL,32,164,484,'2020-02-15 14:39:18',484,'2020-02-15 14:39:18',886,893,NULL,NULL,NULL,51,NULL,NULL,'83',40322,'Y','203.109.97.194',NULL);


update tb_tax_mas set PARENT_CODE=(select tax_id  from tb_tax_mas where tax_code='AS12' and orgid=164) WHERE TAX_ID=
(select tax_id  from tb_tax_mas where tax_code='AS17' and orgid=164);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS17' and orgid=164),430,'A',164,484,'2020-02-15 14:39:18',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS17' and orgid=164),436,'A',164,484,'2020-02-15 14:39:19',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS17' and orgid=164),40215,'A',164,484,'2020-02-15 14:39:19',NULL,NULL,'192.168.33.102',NULL);




INSERT INTO tb_tax_mas (TAX_ID,TAX_DESC,TAX_METHOD,TAX_VALUE_TYPE,PARENT_CODE,TAX_GROUP,TAX_PRINT_ON1,TAX_CODE,TAX_DISPLAY_SEQ,DP_DEPTID,COLL_MTD,COLL_SEQ,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,TAX_CATEGORY1,TAX_CATEGORY2,TAX_CATEGORY3,TAX_CATEGORY4,TAX_CATEGORY5,TAX_APPLICABLE,SM_SERVICE_ID,TAX_PRINT_ON2,TAX_PRINT_ON3,tax_desc_id,Tax_Active,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_MAS','TAX_ID',NULL,NULL),'Advance Payment','378',NULL,NULL,'93','81','AS18',33,7,NULL,33,164,484,'2020-02-15 14:39:30',484,'2020-02-15 14:39:30',882,902,NULL,NULL,NULL,50,NULL,NULL,'83',150,'Y','203.109.97.194',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS18' and orgid=164),430,'A',164,484,'2020-02-15 14:39:30',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS18' and orgid=164),436,'A',164,484,'2020-02-15 14:39:30',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS18' and orgid=164),40215,'A',164,484,'2020-02-15 14:39:30',NULL,NULL,'192.168.33.102',NULL);



INSERT INTO tb_tax_mas (TAX_ID,TAX_DESC,TAX_METHOD,TAX_VALUE_TYPE,PARENT_CODE,TAX_GROUP,TAX_PRINT_ON1,TAX_CODE,TAX_DISPLAY_SEQ,DP_DEPTID,COLL_MTD,COLL_SEQ,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,TAX_CATEGORY1,TAX_CATEGORY2,TAX_CATEGORY3,TAX_CATEGORY4,TAX_CATEGORY5,TAX_APPLICABLE,SM_SERVICE_ID,TAX_PRINT_ON2,TAX_PRINT_ON3,tax_desc_id,Tax_Active,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_MAS','TAX_ID',NULL,NULL),'General Water Tax','378',NULL,NULL,'93','81','AS19',34,7,NULL,34,164,484,'2020-02-15 14:39:42',484,'2020-02-15 14:39:42',883,908,NULL,NULL,NULL,50,NULL,NULL,'83',40732,'Y','203.109.97.194',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS19' and orgid=164),430,'A',164,484,'2020-02-15 14:39:42',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS19' and orgid=164),436,'A',164,484,'2020-02-15 14:39:42',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS19' and orgid=164),40215,'A',164,484,'2020-02-15 14:39:42',NULL,NULL,'192.168.33.102',NULL);



INSERT INTO tb_tax_mas (TAX_ID,TAX_DESC,TAX_METHOD,TAX_VALUE_TYPE,PARENT_CODE,TAX_GROUP,TAX_PRINT_ON1,TAX_CODE,TAX_DISPLAY_SEQ,DP_DEPTID,COLL_MTD,COLL_SEQ,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,TAX_CATEGORY1,TAX_CATEGORY2,TAX_CATEGORY3,TAX_CATEGORY4,TAX_CATEGORY5,TAX_APPLICABLE,SM_SERVICE_ID,TAX_PRINT_ON2,TAX_PRINT_ON3,tax_desc_id,Tax_Active,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_MAS','TAX_ID',NULL,NULL),'Additional Water Tax','380',NULL,NULL,'93','81','AS20',35,7,NULL,35,164,484,'2020-02-15 14:39:54',484,'2020-02-15 14:39:54',883,908,NULL,NULL,NULL,50,NULL,NULL,'83',40731,'Y','203.109.97.194',NULL);


update tb_tax_mas set PARENT_CODE=(select tax_id  from tb_tax_mas where tax_code='AS12' and orgid=164) WHERE TAX_ID=
(select tax_id  from tb_tax_mas where tax_code='AS20' and orgid=164);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS20' and orgid=164),430,'A',164,484,'2020-02-15 14:39:54',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS20' and orgid=164),436,'A',164,484,'2020-02-15 14:39:54',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS20' and orgid=164),40215,'A',164,484,'2020-02-15 14:39:54',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_mas (TAX_ID,TAX_DESC,TAX_METHOD,TAX_VALUE_TYPE,PARENT_CODE,TAX_GROUP,TAX_PRINT_ON1,TAX_CODE,TAX_DISPLAY_SEQ,DP_DEPTID,COLL_MTD,COLL_SEQ,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,TAX_CATEGORY1,TAX_CATEGORY2,TAX_CATEGORY3,TAX_CATEGORY4,TAX_CATEGORY5,TAX_APPLICABLE,SM_SERVICE_ID,TAX_PRINT_ON2,TAX_PRINT_ON3,tax_desc_id,Tax_Active,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_MAS','TAX_ID',NULL,NULL),'Rebate for Self Occupied','380',NULL,13409,'93','81','AS21',36,7,NULL,36,164,484,'2020-02-15 14:40:05',484,'2020-02-15 14:40:05',886,894,NULL,NULL,NULL,50,NULL,NULL,'83',40606,'Y','203.109.97.194',NULL);


update tb_tax_mas set PARENT_CODE=(select tax_id  from tb_tax_mas where tax_code='AS12' and orgid=164) WHERE TAX_ID=
(select tax_id  from tb_tax_mas where tax_code='AS21' and orgid=164);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS21' and orgid=164),430,'A',164,484,'2020-02-15 14:40:06',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS21' and orgid=164),436,'A',164,484,'2020-02-15 14:40:06',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS21' and orgid=164),40215,'A',164,484,'2020-02-15 14:40:06',NULL,NULL,'192.168.33.102',NULL);


INSERT INTO tb_tax_mas (TAX_ID,TAX_DESC,TAX_METHOD,TAX_VALUE_TYPE,PARENT_CODE,TAX_GROUP,TAX_PRINT_ON1,TAX_CODE,TAX_DISPLAY_SEQ,DP_DEPTID,COLL_MTD,COLL_SEQ,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,TAX_CATEGORY1,TAX_CATEGORY2,TAX_CATEGORY3,TAX_CATEGORY4,TAX_CATEGORY5,TAX_APPLICABLE,SM_SERVICE_ID,TAX_PRINT_ON2,TAX_PRINT_ON3,tax_desc_id,Tax_Active,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_MAS','TAX_ID',NULL,NULL),'Exemption on Property Tax','380',NULL,13409,'93','81','AS22',37,7,NULL,37,164,484,'2020-02-15 14:40:17',484,'2020-02-15 14:40:17',886,894,NULL,NULL,NULL,50,NULL,NULL,'83',40734,'Y','203.109.97.194',NULL);

update tb_tax_mas set PARENT_CODE=(select tax_id  from tb_tax_mas where tax_code='AS12' and orgid=164) WHERE TAX_ID=
(select tax_id  from tb_tax_mas where tax_code='AS22' and orgid=164);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS22' and orgid=164),430,'A',164,484,'2020-02-15 14:40:17',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS22' and orgid=164),436,'A',164,484,'2020-02-15 14:40:18',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS22' and orgid=164),40215,'A',164,484,'2020-02-15 14:40:18',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_mas (TAX_ID,TAX_DESC,TAX_METHOD,TAX_VALUE_TYPE,PARENT_CODE,TAX_GROUP,TAX_PRINT_ON1,TAX_CODE,TAX_DISPLAY_SEQ,DP_DEPTID,COLL_MTD,COLL_SEQ,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,TAX_CATEGORY1,TAX_CATEGORY2,TAX_CATEGORY3,TAX_CATEGORY4,TAX_CATEGORY5,TAX_APPLICABLE,SM_SERVICE_ID,TAX_PRINT_ON2,TAX_PRINT_ON3,tax_desc_id,Tax_Active,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_MAS','TAX_ID',NULL,NULL),'Transfer Fee','378',NULL,NULL,'94','81','AS23',8,7,NULL,6,164,484,'2020-02-15 14:43:39',484,'2020-02-15 14:43:39',887,895,NULL,NULL,NULL,48,6597,NULL,'83',42326,'Y','203.109.97.194',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS23' and orgid=164),430,'A',164,484,'2020-02-15 14:43:40',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS23' and orgid=164),436,'A',164,484,'2020-02-15 14:43:40',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS23' and orgid=164),40215,'A',164,484,'2020-02-15 14:43:40',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_mas (TAX_ID,TAX_DESC,TAX_METHOD,TAX_VALUE_TYPE,PARENT_CODE,TAX_GROUP,TAX_PRINT_ON1,TAX_CODE,TAX_DISPLAY_SEQ,DP_DEPTID,COLL_MTD,COLL_SEQ,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,TAX_CATEGORY1,TAX_CATEGORY2,TAX_CATEGORY3,TAX_CATEGORY4,TAX_CATEGORY5,TAX_APPLICABLE,SM_SERVICE_ID,TAX_PRINT_ON2,TAX_PRINT_ON3,tax_desc_id,Tax_Active,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_MAS','TAX_ID',NULL,NULL),'Application Charge','378',NULL,NULL,'94','81','AS24',7,7,NULL,7,164,484,'2020-02-15 14:43:51',484,'2020-02-15 14:43:51',887,895,NULL,NULL,NULL,48,6577,NULL,'83',144,'Y','203.109.97.194',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS24' and orgid=164),430,'A',164,484,'2020-02-15 14:43:51',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS24' and orgid=164),436,'A',164,484,'2020-02-15 14:43:51',NULL,NULL,'192.168.33.102',NULL);

INSERT INTO tb_tax_det (TD_TAXDET,TM_TAXID,TD_DEPEND_FACT,STATUS,ORGID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD) VALUES (fn_java_sq_generation('COM','TB_TAX_DET','TD_TAXDET',NULL,NULL),(select tax_id  from tb_tax_mas where tax_code='AS24' and orgid=164),40215,'A',164,484,'2020-02-15 14:43:51',NULL,NULL,'192.168.33.102',NULL);


update _sequences set next=25 where name=
(select SQ_SEQ_NAME from tb_seq_generation l where l.SQ_TBL_NAME='tb_tax_mas' and l.SQ_FLD_NAME='TAX_ID' and l.sq_orgid=164);
commit;

UPDATE `suda_service1`.`tb_tax_mas` SET `PARENT_CODE`='13386' WHERE `TAX_ID`='13388';
UPDATE `suda_service1`.`tb_tax_mas` SET `PARENT_CODE`='13386' WHERE `TAX_ID`='13391';
UPDATE `suda_service1`.`tb_tax_mas` SET `PARENT_CODE`='13386' WHERE `TAX_ID`='13392';
UPDATE `suda_service1`.`tb_tax_mas` SET `PARENT_CODE`='13386' WHERE `TAX_ID`='13393';
commit;
