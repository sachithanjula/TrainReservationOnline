package com.trainreservationapi.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trainreservationapi.config.Config;
import com.trainreservationapi.domain.Payment;
import com.trainreservationapi.helper.PaymentHelper;
import com.trainreservationapi.service.PaymentServiceImpl;
import com.trainreservationapi.service.SessionServiceImpl;
import com.trainreservationapi.service.TrainServiceImpl;


@Controller
@RequestMapping(value = "/payment")
@CrossOrigin(origins = Config.allowedOrigin)
public class PaymentController {
	
	@Autowired
    private PaymentServiceImpl paymentService = new PaymentServiceImpl();

    @Autowired
    private SessionServiceImpl sessionService = new SessionServiceImpl();

    @Autowired
    private TrainServiceImpl trainService = new TrainServiceImpl();

    private PaymentHelper paymentHelper = new PaymentHelper();
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Payment>> getAllPaymentEntries() {
        return new ResponseEntity<>(paymentService.getAllPayments(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/pid/{pid}", method = RequestMethod.GET)
    public ResponseEntity<Payment> getPayment(@PathVariable("pid") long pid) {
        return new ResponseEntity<>(paymentService.findByPid(pid), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/uid/{uid}", method = RequestMethod.GET)
    public ResponseEntity<List<Payment>> getAllPaymentEntriesByUser(@PathVariable("uid") long uid) {
        return new ResponseEntity<>(paymentService.findByUid(uid), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/total", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> getTotalAmountToPay(@RequestHeader("Authentication") long authKey, @RequestBody Map<String, Object> payload) {
        Map<String, Object> response = new HashMap<>();
        double total = 0;

        // go through each fid and it's count.
        for (String id: payload.keySet()) {
            int itemCount = Integer.parseInt(payload.get(id).toString());
            double itemPrice = trainService.getPriceOf(id);
            double subtotal = itemPrice * itemCount;

            total += subtotal;
        }

        response.put("success", "true");
        response.put("amount", total);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> addPayment(@RequestHeader("Authentication") long authKey, @RequestBody Payment payment) {
    	System.out.println("payPost");
    	System.out.println(payment);
        Map<String, String> response = new HashMap<>();

        if (sessionService.authenticate(authKey)) {
            payment.setPid((payment.getUid() + payment.getPaymentDate().toString() + new Date().toString()).hashCode());

            // the client is not supposed to send the amount to be charged since it can be easily altered.
            // instead the fId and count of each is sent.
            double tot = 0;
            Map<String, Integer> items = payment.getTicketsAndCounts();
            // calculate
            for (String id: items.keySet()) { tot += (trainService.getPriceOf(id) * items.get(id)); }
            payment.setAmount(tot);

            // give loyalty points for the amount.
            //payment.setLoyaltyPoints(tot * (0.01/100));

            // for credit cards we only keep the last 4 digits of the card number for,
            // security concerns.
            if (payment.getPaymentType().equals("card")) {
                Map<String, String> paymentDetails = payment.getPaymentDetails();
                String cardNumber = paymentDetails.get("number");
                String censoredCardNumber = "xxxx-xxxx-xxxx-" + cardNumber.substring(cardNumber.length()-4, cardNumber.length());
                paymentDetails.put("number", censoredCardNumber);
            }

            // we aren't technically contacting any payment gateways :(
            // but we'll direct if the payment is successful.
            paymentService.savePayment(payment);
            response.put("success", "true");
            response.put("redirect", "home.html");
            response.put("pid", Long.toString(payment.getPid()));
        }
        else {
            response.put("success", "false");
            response.put("redirect", "book.html");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
