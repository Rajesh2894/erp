--liquibase formatted sql
--changeset nilima:V20180920200105_AL_TB_AS_PROP_MAS1.sql
alter table TB_AS_PROP_MAS change column PM_SURVEY_NUMBER PM_SURVEY_NUMBER VARCHAR(25) NULL COMMENT '	SURVEY NUMBER	';
--liquibase formatted sql
--changeset nilima:V20180920200105_AL_TB_AS_PROP_MAS2.sql
alter table TB_AS_PROP_MAS change column PM_STREET_NO PM_STREET_NO VARCHAR(500) NULL COMMENT '	STREET NUMBER_NAME	';
--liquibase formatted sql
--changeset nilima:V20180920200105_AL_TB_AS_PROP_MAS3.sql
alter table TB_AS_PROP_MAS change column PM_TOJI_NO PM_TOJI_NO VARCHAR(50) NULL COMMENT '	TOJI NUMBER	';
--liquibase formatted sql
--changeset nilima:V20180920200105_AL_TB_AS_PROP_MAS4.sql
alter table TB_AS_PROP_MAS change column PM_VILLAGE_MAUJA PM_VILLAGE_MAUJA VARCHAR(50) NULL COMMENT '	VILLAGE_MAUJA NAME	';
