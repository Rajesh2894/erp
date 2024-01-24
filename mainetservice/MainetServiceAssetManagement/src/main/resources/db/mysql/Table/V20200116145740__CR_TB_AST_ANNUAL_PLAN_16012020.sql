--liquibase formatted sql
--changeset Anil:V20200116145740__CR_TB_AST_ANNUAL_PLAN_16012020.sql
drop table if exists TB_AST_ANNUAL_PLAN;
--liquibase formatted sql
--changeset Anil:V20200116145740__CR_TB_AST_ANNUAL_PLAN_160120201.sql
create table TB_AST_ANNUAL_PLAN(
AST_ANN_PLAN_ID BIGINT(12) NOT NULL,                                                  
FA_YEARID BIGINT(12) NOT NULL,                              
AST_LOC BIGINT(12) NOT NULL,                              
AST_DEPT BIGINT(12) NOT NULL,                              
AST_CATEGORY BIGINT(12) NOT NULL,                              
AST_DESC VARCHAR(500) NOT NULL,                      
AST_QTY DECIMAL(12,2) NOT NULL,                       
ORG_ID BIGINT(12) NOT NULL,                              
CREATION_DATE DATE NOT NULL,                                     
CREATED_BY BIGINT(12) NOT NULL,                             
UPDATED_BY BIGINT(12) DEFAULT NULL,                              
UPDATED_DATE DATE DEFAULT NULL,                                        
LG_IP_MAC VARCHAR(100) NOT NULL,
LG_IP_MAC_UPD VARCHAR(100) DEFAULT NULL,                      
PRIMARY KEY(AST_ANN_PLAN_ID)
);
