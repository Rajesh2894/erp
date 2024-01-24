update tb_portal_service_master set PSM_SERVICE_NAME='Booking of Municipal Property',
PSM_SERVICE_NAME_REG='म्युनसिपल प्रोपर्टी बुकींग'
where  PSM_SHORT_NAME='ESR';
COMMIT;
update tb_portal_service_master set PSM_SERVICE_NAME='Application for Road cutting Permission',
PSM_SERVICE_NAME_REG='सड़क काटने की अनुमति के लिए आवेदन'
where  PSM_SHORT_NAME='RCP';
COMMIT;

update tb_portal_service_master set PSM_SERVICE_NAME='File RTI On-line',
PSM_SERVICE_NAME_REG='फाइल आरटीआई ऑन लाइन'
where  PSM_SHORT_NAME='RAF';
COMMIT;

update tb_portal_service_master set PSM_SERVICE_NAME='Issuance of new trade license',
PSM_SERVICE_NAME_REG='नए ट्रेड लाइसेंस जारी करना'
where  PSM_SHORT_NAME='NTL';
commit;

update tb_portal_service_master set PSM_SERVICE_NAME='Issuance of Duplicate Trade License',
PSM_SERVICE_NAME_REG='डुप्लीकेट ट्रेड लाइसेंस जारी करना'
where  PSM_SHORT_NAME='DTL';

update tb_portal_service_master set PSM_SERVICE_NAME='Change in Business Name',
PSM_SERVICE_NAME_REG='व्यावसायिक नाम में परिवर्तन'
where  PSM_SHORT_NAME='CBN';

update tb_portal_service_master set PSM_SERVICE_NAME='Change in Trade Category',
PSM_SERVICE_NAME_REG='व्यापार श्रेणी में परिवर्तन'
where  PSM_SHORT_NAME='CCS';
COMMIT;


update tb_portal_service_master set PSM_SERVICE_NAME='Amalgamation of Properties',
PSM_SERVICE_NAME_REG='संपत्ति का समामेलन/एकीकरण'
where  PSM_SHORT_NAME='AML';
COMMIT;

update tb_portal_service_master set PSM_SERVICE_NAME='Bifurcation of property',
PSM_SERVICE_NAME_REG='संपत्ति का विभाजन'
where  PSM_SHORT_NAME='BIF';
COMMIT;

update tb_portal_service_master set PSM_SERVICE_NAME='Self Assessment ( Change )',
PSM_SERVICE_NAME_REG='स्व मूल्यांकन (परिवर्तन)'
where  PSM_SHORT_NAME='CIA';
COMMIT;

update tb_portal_service_master set PSM_SERVICE_NAME='Change in Ownership',
PSM_SERVICE_NAME_REG='स्वामित्व में बदलाव'
where  PSM_SHORT_NAME='WCO';

update tb_portal_service_master set PSM_SERVICE_NAME='Change in Usage',
PSM_SERVICE_NAME_REG='उपयोग में परिवर्तन'
where  PSM_SHORT_NAME='WCU';


update tb_portal_service_master set is_deleted=1
where  PSM_SHORT_NAME='NPR';

update tb_portal_service_master set PSM_SERVICE_NAME='New Water Connection',
PSM_SERVICE_NAME_REG='नया पानी कनेक्शन'
where  PSM_SHORT_NAME='WNC';
commit;

update tb_portal_service_master set PSM_SERVICE_NAME='Self Assessment ( No change )',
PSM_SERVICE_NAME_REG='स्व मूल्यांकन (कोई परिवर्तन नहीं)'
where  PSM_SHORT_NAME='NCA';

update tb_portal_service_master set PSM_SERVICE_NAME='Plumber License',
PSM_SERVICE_NAME_REG='प्लम्बर लाइसेंस'
where  PSM_SHORT_NAME='WPL';

update tb_portal_service_master set PSM_SERVICE_NAME='Renewal of Plumber License',
PSM_SERVICE_NAME_REG='प्लम्बर लाइसेंस का नवीनीकरण'
where  PSM_SHORT_NAME='WRP';

update tb_portal_service_master set PSM_SERVICE_NAME='File RTI On-line',
PSM_SERVICE_NAME_REG='फाइल आरटीआई ऑन लाइन'
where  PSM_SHORT_NAME='RAF';

update tb_portal_service_master set PSM_SERVICE_NAME='Self Assessment ( New Registration ) ',
PSM_SERVICE_NAME_REG='स्व मूल्यांकन (नया पंजीकरण)'
where  PSM_SHORT_NAME='SAS';

update tb_portal_service_master set PSM_SERVICE_NAME='Temporary / Permanent Closing of Water Connection',
PSM_SERVICE_NAME_REG='पानी के कनेक्शन का अस्थायी / स्थायी समापन'
where  PSM_SHORT_NAME='WCC';

update tb_portal_service_master set PSM_SERVICE_NAME='Re-connection',
PSM_SERVICE_NAME_REG='पुन: कनेक्शन'
where  PSM_SHORT_NAME='WRC';
commit;





