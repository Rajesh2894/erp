--liquibase formatted sql
--changeset nilima:V20171227130606__ALL_TB_DROP1.sql
drop table IF EXISTS TB_AS_PRO_ASSESMENT_OWNER_DTL;
--liquibase formatted sql
--changeset nilima:V20171227130606__ALL_TB_DROP2.sql
drop table  IF EXISTS TB_AS_PRO_ASSESMENT_FACTOR_DTL;
--liquibase formatted sql
--changeset nilima:V20171227130606__ALL_TB_DROP3.sql
Drop table IF EXISTS TB_AS_PRO_ASSESMENT_DETAIL;
--liquibase formatted sql
--changeset nilima:V20171227130606__ALL_TB_DROP4.sql
drop table IF EXISTS TB_AS_PRO_ASSESMENT_MAST;
--liquibase formatted sql
--changeset nilima:V20171227130606__ALL_TB_DROP5.sql
DROP TABLE IF EXISTS TB_AS_PRO_BILL_DET;
--liquibase formatted sql
--changeset nilima:V20171227130606__ALL_TB_DROP6.sql
DROP TABLE IF EXISTS TB_AS_PRO_BILL_MAS;