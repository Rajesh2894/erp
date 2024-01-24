--liquibase formatted sql
--changeset nilima:V20181022185505_TB_AS_ASSESMENT_MAST1.sql
ALTER TABLE TB_AS_ASSESMENT_MAST ADD COLUMN MN_ASS_TAX_COLL_EMP BIGINT(20) DEFAULT NULL COMMENT '	TAX COLLECTOR EMPLOYEE ' AFTER ORGID;
--liquibase formatted sql
--changeset nilima:V20181022185505_TB_AS_ASSESMENT_MAST2.sql
ALTER TABLE TB_AS_PRO_ASSESMENT_MAST ADD COLUMN PRO_ASS_TAX_COLL_EMP BIGINT(20) DEFAULT NULL COMMENT '	TAX COLLECTOR EMPLOYEE ' AFTER ORGID;
--liquibase formatted sql
--changeset nilima:V20181022185505_TB_AS_ASSESMENT_MAST3.sql
ALTER TABLE TB_AS_PRO_MAST_HIST ADD COLUMN PRO_ASS_TAX_COLL_EMP BIGINT(20) DEFAULT NULL COMMENT '	TAX COLLECTOR EMPLOYEE ' AFTER ORGID;
--liquibase formatted sql
--changeset nilima:V20181022185505_TB_AS_ASSESMENT_MAST4.sql
ALTER TABLE TB_AS_PROP_MAS ADD COLUMN PM_ASS_TAX_COLL_EMP BIGINT(20) DEFAULT NULL COMMENT '	TAX COLLECTOR EMPLOYEE ' AFTER ORGID;
--liquibase formatted sql
--changeset nilima:V20181022185505_TB_AS_ASSESMENT_MAST5.sql
ALTER TABLE TB_AS_MAST_HIST ADD COLUMN MN_ASS_TAX_COLL_EMP BIGINT(20) DEFAULT NULL COMMENT '	TAX COLLECTOR EMPLOYEE ' AFTER ORGID;

