--liquibase formatted sql
--changeset Kanchan:V20230410195825__AL_TB_RL_PROPERTY_DTL_10042023.sql
ALTER TABLE TB_RL_PROPERTY_DTL ADD column PROP_LEFT_AREA decimal(12,2) null default null;
--liquibase formatted sql
--changeset Kanchan:V20230410195825__AL_TB_RL_PROPERTY_DTL_100420231.sql
ALTER TABLE TB_RL_PROPERTY_DTL ADD column PROP_TOTAL_AREA decimal(12,2) null default null;
--liquibase formatted sql
--changeset Kanchan:V20230410195825__AL_TB_RL_PROPERTY_DTL_100420232.sql
ALTER TABLE TB_RL_PROPTY_SHIFT modify column PROP_FROMTIME datetime null default null;
--liquibase formatted sql
--changeset Kanchan:V20230410195825__AL_TB_RL_PROPERTY_DTL_100420233.sql
ALTER TABLE TB_RL_PROPTY_SHIFT modify column PROP_TOTIME datetime null default null;