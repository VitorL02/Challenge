package com.challenge.challenge.services;


import com.challenge.challenge.models.Message;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@FeignClient(name = "authorization",url="https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6")
public interface AuthorizationService {
    @GetMapping
    Message authorization();

}
