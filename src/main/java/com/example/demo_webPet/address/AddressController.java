package com.example.demo_webPet.address;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
final class AddressController {

    private final AddressService service;

    @GetMapping
    public Map<String, List<AddressResponse>> getAddressList(){
        Map<String, List<AddressResponse>> result = new HashMap<>();
        result.put("address1", service.getAddressList());
        return result;
    }

    @GetMapping(params = "address1")
    public Map<String, List<AddressResponse>> getAddress2(@RequestParam String address1) {
        Map<String, List<AddressResponse>> result = new HashMap<>();
        result.put("address2", service.getAddressList(address1));
        return result;
    }
}
