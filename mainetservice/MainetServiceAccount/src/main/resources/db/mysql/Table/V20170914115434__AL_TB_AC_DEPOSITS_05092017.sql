--liquibase formatted sql
--changeset nilima:V20170914115434__AL_TB_AC_DEPOSITS_05092017.sql
ALTER TABLE TB_AC_DEPOSITS 
DROP FOREIGN KEY FK_DEP_BUGDCODE;

--liquibase formatted sql
--changeset nilima:V20170914115434__AL_TB_AC_DEPOSITS_050920171.sql
ALTER TABLE TB_AC_DEPOSITS 
DROP COLUMN BUDGETCODE_ID,
CHANGE COLUMN SAC_HEAD_ID SAC_HEAD_ID BIGINT(12) NOT NULL COMMENT 'Account Headcode Id' ,
DROP INDEX FK_DEP_BUGDCODE_idx ;

--liquibase formatted sql
--changeset nilima:V20170914115434__AL_TB_AC_DEPOSITS_050920172.sql
ALTER TABLE TB_AC_DEPOSITS
DROP COLUMN LANG_ID;
