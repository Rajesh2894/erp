--liquibase formatted sql
--changeset nilima:V20180405180500__ALL_TB_DROP1.sql
drop table IF EXISTS TB_AS_PRO_OWNER_DTL_HIST;

--liquibase formatted sql
--changeset nilima:V20180405180500__ALL_TB_DROP2.sql
drop table IF EXISTS TB_AS_PRO_FACTOR_DTL_HIST;

--liquibase formatted sql
--changeset nilima:V20180405180500__ALL_TB_DROP3.sql
Drop table IF EXISTS TB_AS_PRO_DETAIL_HIST;

--liquibase formatted sql
--changeset nilima:V20180405180500__ALL_TB_DROP4.sql
drop table IF EXISTS TB_AS_PRO_MAST_HIST;

--liquibase formatted sql
--changeset nilima:V20180405180500__ALL_TB_DROP5.sql
drop table IF EXISTS TB_AS_OWNER_DTL_HIST;

--liquibase formatted sql
--changeset nilima:V20180405180500__ALL_TB_DROP6.sql
drop table IF EXISTS TB_AS_FACTOR_DTL_HIST;

--liquibase formatted sql
--changeset nilima:V20180405180500__ALL_TB_DROP7.sql
Drop table IF EXISTS TB_AS_DETAIL_HIST;

--liquibase formatted sql
--changeset nilima:V20180405180500__ALL_TB_DROP8.sql
drop table IF EXISTS TB_AS_MAST_HIST;

--liquibase formatted sql
--changeset nilima:V20180405180500__ALL_TB_DROP9.sql
DROP TABLE IF EXISTS TB_AS_PRO_BILL_DET_HIST;

--liquibase formatted sql
--changeset nilima:V20180405180500__ALL_TB_DROP10.sql
DROP TABLE IF EXISTS TB_AS_PRO_BILL_MAS_HIST;

--liquibase formatted sql
--changeset nilima:V20180405180500__ALL_TB_DROP11.sql
DROP TABLE IF EXISTS TB_AS_BILL_DET_HIST;

--liquibase formatted sql
--changeset nilima:V20180405180500__ALL_TB_DROP12.sql
DROP TABLE IF EXISTS TB_AS_BILL_MAS_HIST;