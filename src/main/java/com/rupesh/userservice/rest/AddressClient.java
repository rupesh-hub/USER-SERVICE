package com.rupesh.userservice.rest;

import com.rupesh.userservice.dto.AddressDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="address-service", url = "http://localhost:8181", path = "/address")
public interface AddressClient {

    @GetMapping("/user/{userId}")
    AddressDTO getAddressByUserId(@PathVariable("userId") String userId);

}
