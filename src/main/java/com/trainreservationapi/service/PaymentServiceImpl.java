package com.trainreservationapi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trainreservationapi.domain.Payment;
import com.trainreservationapi.repositories.PaymentRepository;

@Service("paymentService")
@Transactional
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;

	@Override
    public Payment findByPid(long pid) {
        return paymentRepository.findByPid(pid);
    }

    @Override
    public List<Payment> findByUid(long uid) {
        return paymentRepository.findByUid(uid);
    }

    @Override
    public List<Payment> findByPaymentDate(Date date) {
        return paymentRepository.findByPaymentDate(date);
    }

    @Override
    public List<Payment> getAllPayments() {
        List<Payment> l = paymentRepository.findAll();
        System.out.println(l.size());
        return paymentRepository.findAll();
    }

    @Override
    public void savePayment(Payment payment) {
        paymentRepository.save(payment);
    }

    @Override
    public void updatePayment(long pid, Payment paymentUpdate) {
        if (pid != paymentUpdate.getPid()) { paymentUpdate.setPid(pid); }

        Payment paymentExisting = findByPid(pid);

        if (paymentExisting != null) {
            paymentExisting.setAmount(paymentUpdate.getAmount());
            paymentExisting.setPaymentDetails(paymentUpdate.getPaymentDetails());
            paymentExisting.setPaymentType(paymentUpdate.getPaymentType());
            paymentExisting.setUid(paymentUpdate.getUid());

            savePayment(paymentExisting);
        }
    }

    @Override
    public void deletePayment(Payment payment) {

    }

    @Override
    public boolean isPaymentExist(Payment payment) {
        return false;
    }

}
