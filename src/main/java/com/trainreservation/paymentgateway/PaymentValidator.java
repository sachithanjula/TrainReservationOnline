package com.trainreservation.paymentgateway;

import java.util.Calendar;

public class PaymentValidator {
	
	public static boolean areCardDetailsValid(int number, int ccv, String expiry){
        // since we are emulating a dummy payment gateway, we assume any card number of length 16,
        // is correct.
        boolean digitValidity = ( (Integer.toString(number).length() == 16) && (Integer.toString(ccv).length() == 3) );

        // validating expiry date.
        boolean dateValidity = false;
        if (expiry.contains("/")) {
            String[] dateParts = expiry.split("/");
            int month = Integer.parseInt(dateParts[0]);
            int year = Integer.parseInt(dateParts[1]);
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);


            dateValidity = ( (month > 0 && month <= 12) && (year >= currentYear) );
        }

        return (digitValidity && dateValidity);
    }

}
