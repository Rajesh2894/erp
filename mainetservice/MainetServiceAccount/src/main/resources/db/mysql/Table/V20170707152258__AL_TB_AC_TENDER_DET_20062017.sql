ALTER TABLE TB_AC_TENDER_DET 
CHANGE COLUMN TR_TENDERID_DET TR_TENDERID_DET BIGINT(12) NOT NULL COMMENT '' ,
CHANGE COLUMN TR_TENDER_ID TR_TENDER_ID BIGINT(12) NULL COMMENT '' ,
CHANGE COLUMN ORGID ORGID BIGINT(12) NOT NULL COMMENT '' ,
CHANGE COLUMN CREATED_BY CREATED_BY BIGINT(12) NOT NULL COMMENT '' ,
CHANGE COLUMN UPDATED_BY UPDATED_BY BIGINT(12) NULL COMMENT '' ,
CHANGE COLUMN LANG_ID LANG_ID BIGINT(12) NOT NULL COMMENT '' ,
CHANGE COLUMN FI04_N1 TR_TENDERDET_AMT DECIMAL(15,2) NULL DEFAULT NULL COMMENT 'Tender Detail Amount' ;
