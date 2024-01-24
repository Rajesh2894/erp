--liquibase formatted sql
--changeset PramodPatil:V20231012182201__CR_tb_wms_bid_itemrate_det_12102023.sql
CREATE TABLE tb_wms_bid_itemrate_det
(
ITEM_RATE_BID_ID BIGINT NOT NULL,
BID_ID BIGINT null default null,
ITEM_ID BIGINT null default null,
QUANTITY DOUBLE null default null,
RATE DECIMAL(10, 2) null default '0.00',
AMOUNT DECIMAL(10, 2) null default '0.00',
ORGID BIGINT NOT NULL,
CREATED_DATE DATETIME NOT NULL,
CREATED_BY BIGINT NOT NULL,
LG_IP_MAC VARCHAR(100) NOT NULL,
LG_IP_MAC_UPD VARCHAR(100) null default null,
UPDATED_BY BIGINT null default null,
UPDATED_DATE DATETIME null default null,
PRIMARY KEY (ITEM_RATE_BID_ID),
FOREIGN KEY (BID_ID) REFERENCES tb_wms_bid_master(bid_Id) );

