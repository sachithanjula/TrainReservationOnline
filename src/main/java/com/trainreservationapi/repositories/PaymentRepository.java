package com.trainreservationapi.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.trainreservationapi.domain.Payment;

public interface PaymentRepository extends MongoRepository<Payment, String>{
	
	Payment findByPid(long pid);

    List<Payment> findByPaymentDate(Date date);
    
    List<Payment> findByUid(long uid);

    void deleteByPid(long pid);

}
