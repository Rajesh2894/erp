--liquibase formatted sql
--changeset Kanchan:V20230616193326__AL_mm_purchasereturn_det_16062023.sql
ALTER TABLE mm_purchasereturn_det ADD returnid bigint(12) AFTER returndetid;
--liquibase formatted sql
--changeset Kanchan:V20230616193326__AL_mm_purchasereturn_det_160620231.sql
ALTER TABLE mm_purchasereturn_det ADD FOREIGN KEY (returnid) REFERENCES mm_purchasereturn(returnid) ;
