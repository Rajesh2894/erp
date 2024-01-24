--liquibase formatted sql
--changeset nilima:V20171101140425__AL_role_entitlement_31102017.sql
delete from role_entitlement where ROLE_ID is null;
commit;

--liquibase formatted sql
--changeset nilima:V20171101140425__AL_role_entitlement_311020171.sql
ALTER TABLE role_entitlement
DROP FOREIGN KEY FK_ROLL_ID,
DROP FOREIGN KEY FK_ROLL_SMFID;

--liquibase formatted sql
--changeset nilima:V20171101140425__AL_role_entitlement_311020172.sql
ALTER TABLE role_entitlement
CHANGE COLUMN ROLE_ID ROLE_ID BIGINT(12) NOT NULL COMMENT 'FK reference of GM_ID  TB_GROUP_MAST' ,
CHANGE COLUMN SMFID SMFID BIGINT(12) NOT NULL COMMENT 'FK reference of smfid TB_SYSMODFUNCTION' ,
CHANGE COLUMN IS_ACTIVE IS_ACTIVE VARCHAR(2) NOT NULL COMMENT 'Active Role' ,
ADD COLUMN CREATED_BY BIGINT(12) COMMENT '' AFTER BU_DELETE,
ADD COLUMN CREATED_DATE DATE COMMENT '' AFTER CREATED_BY;

--liquibase formatted sql
--changeset nilima:V20171101140425__AL_role_entitlement_311020173.sql
ALTER TABLE role_entitlement
ADD CONSTRAINT FK_ROLL_ID
  FOREIGN KEY (ROLE_ID)
  REFERENCES tb_group_mast(GM_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT FK_ROLL_SMFID
  FOREIGN KEY (SMFID)
  REFERENCES tb_sysmodfunction(SMFID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;