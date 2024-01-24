--liquibase formatted sql
--changeset nilima:V20181122150500_AL_TB_AST_VALUATION_DET1.sql
alter table TB_AST_VALUATION_DET add column BATCH_NO varchar(50) default null comment '' after state;
--liquibase formatted sql
--changeset nilima:V20181122150500_AL_TB_AST_VALUATION_DET2.sql
alter table TB_AST_VALUATION_DET_HIST add column BATCH_NO varchar(50) default null comment '' after state;