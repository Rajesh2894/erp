--liquibase formatted sql
--changeset nilima:V20180802200532__AL_TB_AST_RETIRE1.sql
alter table TB_AST_RETIRE add column NON_FUNCTIONAL_DATE DATETIME NOT NULL COMMENT '         X             ' AFTER ORGID;
--liquibase formatted sql
--changeset nilima:V20180802200532__AL_TB_AST_RETIRE2.sql
alter table TB_AST_RETIRE_HIST add column NON_FUNCTIONAL_DATE DATETIME NOT NULL COMMENT '         X             ' AFTER ORGID;




