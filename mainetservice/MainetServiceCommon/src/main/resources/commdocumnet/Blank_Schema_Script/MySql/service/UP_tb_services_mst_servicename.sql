update tb_services_mst set SM_SERVICE_NAME='Booking of Municipal Property',
SM_SERVICE_NAME_MAR='म्युनसिपल प्रोपर्टी बुकींग'
where  sm_shortdesc='ESR';
COMMIT;
update tb_services_mst set SM_SERVICE_NAME='Application for Road cutting Permission',
SM_SERVICE_NAME_MAR='सड़क काटने की अनुमति के लिए आवेदन'
where  sm_shortdesc='RCP';
COMMIT;

update tb_services_mst set SM_SERVICE_NAME='File RTI On-line',
SM_SERVICE_NAME_MAR='फाइल आरटीआई ऑन लाइन'
where  sm_shortdesc='RAF';
COMMIT;

update tb_services_mst set SM_SERVICE_NAME='Issuance of new trade license',
SM_SERVICE_NAME_MAR='नए ट्रेड लाइसेंस जारी करना'
where  sm_shortdesc='NTL';
commit;

update tb_services_mst set SM_SERVICE_NAME='Issuance of Duplicate Trade License',
SM_SERVICE_NAME_MAR='डुप्लीकेट ट्रेड लाइसेंस जारी करना'
where  sm_shortdesc='DTL';

update tb_services_mst set SM_SERVICE_NAME='Change in Business Name',
SM_SERVICE_NAME_MAR='व्यावसायिक नाम में परिवर्तन'
where  sm_shortdesc='CBN';

update tb_services_mst set SM_SERVICE_NAME='Change in Trade Category',
SM_SERVICE_NAME_MAR='व्यापार श्रेणी में परिवर्तन'
where  sm_shortdesc='CCS';
COMMIT;

update tb_services_mst set SM_SERVICE_NAME='Amalgamation of Properties',
SM_SERVICE_NAME_MAR='संपत्ति का समामेलन/एकीकरण'
where  sm_shortdesc='AML';
COMMIT;

update tb_services_mst set SM_SERVICE_NAME='Bifurcation of property',
SM_SERVICE_NAME_MAR='संपत्ति का विभाजन'
where  sm_shortdesc='BIF';
COMMIT;

update tb_services_mst set SM_SERVICE_NAME='Self Assessment ( Change )',
SM_SERVICE_NAME_MAR='स्व मूल्यांकन (परिवर्तन)'
where  sm_shortdesc='CIA';
COMMIT;

update tb_services_mst set SM_SERVICE_NAME='Change in Ownership',
SM_SERVICE_NAME_MAR='स्वामित्व में बदलाव'
where  sm_shortdesc='WCO';

update tb_services_mst set SM_SERVICE_NAME='Change in Usage',
SM_SERVICE_NAME_MAR='उपयोग में परिवर्तन'
where  sm_shortdesc='WCU';


update tb_services_mst set SM_SERVICE_NAME='Self Assessment ( New Registration )',
SM_SERVICE_NAME_MAR='स्व मूल्यांकन (नया पंजीकरण)'
where  sm_shortdesc='NPR';

update tb_services_mst set SM_SERVICE_NAME='New Water Connection',
SM_SERVICE_NAME_MAR='नया पानी कनेक्शन'
where  sm_shortdesc='WNC';
commit;

update tb_services_mst set SM_SERVICE_NAME='Self Assessment ( No change )',
SM_SERVICE_NAME_MAR='स्व मूल्यांकन (कोई परिवर्तन नहीं)'
where  sm_shortdesc='NCA';

update tb_services_mst set SM_SERVICE_NAME='Plumber License',
SM_SERVICE_NAME_MAR='प्लम्बर लाइसेंस'
where  sm_shortdesc='WPL';

update tb_services_mst set SM_SERVICE_NAME='Renewal of Plumber License',
SM_SERVICE_NAME_MAR='प्लम्बर लाइसेंस का नवीनीकरण'
where  sm_shortdesc='WRP';

update tb_services_mst set SM_SERVICE_NAME='File RTI On-line',
SM_SERVICE_NAME_MAR='फाइल आरटीआई ऑन लाइन'
where  sm_shortdesc='RAF';

update tb_services_mst set SM_SERVICE_NAME='Self Assessment ( New Registration ) ',
SM_SERVICE_NAME_MAR='स्व मूल्यांकन (नया पंजीकरण)'
where  sm_shortdesc='SAS'

update tb_services_mst set SM_SERVICE_NAME='Temporary / Permanent Closing of Water Connection',
SM_SERVICE_NAME_MAR='पानी के कनेक्शन का अस्थायी / स्थायी समापन'
where  sm_shortdesc='WCC';

update tb_services_mst set SM_SERVICE_NAME='Re-connection',
SM_SERVICE_NAME_MAR='पुन: कनेक्शन'
where  sm_shortdesc='WRC';
commit;







