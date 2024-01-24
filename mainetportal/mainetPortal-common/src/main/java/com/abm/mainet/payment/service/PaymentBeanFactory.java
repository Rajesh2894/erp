package com.abm.mainet.payment.service;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author umashanker.kanaujiya this class are used for object creation
 */
public class PaymentBeanFactory {

    public static PaymentStrategy getInstance(final String pgName) {
        PaymentStrategy paymentStrategy = null;
        if ((pgName != null) && pgName.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.PAYU)) {
            paymentStrategy = new PayuPayment();
        } else if ((pgName != null) && pgName.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.HDFC)) {
            paymentStrategy = new HDFCPayment();
        } else if ((pgName != null) && pgName.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.TECH_PROCESS)) {
            paymentStrategy = new TechProcessPayment();
        } else if ((pgName != null) && pgName.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.MAHA_ONLINE)) {
            paymentStrategy = new MahaOnlinePayment();
        } else if ((pgName != null) && pgName.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.CCA)) {
            paymentStrategy = new CCAvenue();
        }
        else if ((pgName != null) && pgName.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.AWL)) {
            paymentStrategy = new Awlline();
        }else if ((pgName != null) && pgName.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.EASYPAY)) {
            paymentStrategy = new EasyPay();
        } else if ((pgName != null) && pgName.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.ICICI)) {
            paymentStrategy = new ICICIPayment();
        }else if ((pgName != null) && pgName.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.BILLCLOUD)) {
            paymentStrategy = new BillCloud();
        }
        else if ((pgName != null) && pgName.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.ATOMPAY)) {
			paymentStrategy = new AtomPayment();
		}
        return paymentStrategy;
    }

}
