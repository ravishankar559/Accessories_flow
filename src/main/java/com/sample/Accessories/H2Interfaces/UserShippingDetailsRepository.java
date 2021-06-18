package com.sample.Accessories.H2Interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.sample.Accessories.H2Entity.UserShippingDetails;

@Component
public interface UserShippingDetailsRepository extends JpaRepository<UserShippingDetails, String> {

}
