--liquibase formatted sql
--changeset nilima:V20180720200530__AL_TB_AST_INSURANCE_REV1.sql
alter table TB_AST_INSURANCE_REV add column REV_GRP_ID BIGINT(12)  NOT NULL COMMENT '	GROUP ID ' AFTER ASSET_INSURANCE_REV_ID;
--liquibase formatted sql
--changeset nilima:V20180720200530__AL_TB_AST_INSURANCE_REV2.sql
alter table TB_AST_INSURANCE_REV add column REV_GRP_IDENTFY CHAR(1)  NOT NULL COMMENT '	O FOR OLD/N for NEW	' AFTER REV_GRP_ID;
