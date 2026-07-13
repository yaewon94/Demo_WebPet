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
    @ResponseBody
    public Map<String, List<String>> getAddressList(){
        Map<String, List<String>> result = new HashMap<>();
        result.put("address1",
                service.getAddressList().stream()
                .map(Address::getName)
                .toList());
        return result;
    }

    @GetMapping(params = "address1")
    @ResponseBody
    public Map<String, List<String>> getAddress2(@RequestParam String address1) {
        Map<String, List<String>> result = new HashMap<>();
        result.put("address2",
                service.getAddressList(address1).stream()
                        .map(Address::getName)
                        .toList());
        return result;
    }
}
