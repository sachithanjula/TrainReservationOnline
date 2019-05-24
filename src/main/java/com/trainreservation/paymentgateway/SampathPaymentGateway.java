package com.trainreservation.paymentgateway;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "spg/card/authenticate")
public class SampathPaymentGateway {
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<String> authenticatePayment(@RequestBody Map<String, Object> payload) {
        String response;
        int number =  Integer.parseInt(payload.get("number").toString());
        int ccv = Integer.parseInt(payload.get("ccv").toString());
        String expiry = payload.get("expiry").toString();

        if (PaymentValidator.areCardDetailsValid(number, ccv, expiry)) {
            response = "authorized";
        }
        else {
            response = "unauthorized";
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
