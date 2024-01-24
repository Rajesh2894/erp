--liquibase formatted sql
--changeset nilima:V20190618132954__TB_LGL_ARBITORY_FEE.sql
CREATE TABLE TB_LGL_ARBITORY_FEE 
(
ARB_ID        BIGINT(12)        COMMENT ' Primary Key',
JUDGE_ID     BIGINT(12)        COMMENT ' Judge id',
ARB_FEEID    BIGINT(12)        COMMENT ' Arbitaory Fee Type',
ARB_AMT        DECIMAL(15,2)        COMMENT ' Arbitaory Fee Amount',
ORGID        BIGINT(12)        COMMENT ' organization id',
CREATED_BY    BIGINT(12)        COMMENT ' user id who created the record',
CREATED_DATE     DATETIME        COMMENT ' record creation date',
UPDATED_BY    BIGINT(12)        COMMENT ' user id who updated the record',
UPDATED_DATE    DATETIME        COMMENT ' date on which updated the record',
LG_IP_MAC    VARCHAR(100)        COMMENT ' machine ip address from where user has created the record',
LG_IP_MAC_UPD    VARCHAR(100)             COMMENT ' machine ip address from where user has updated the record',
PRIMARY KEY (ARB_ID)
);