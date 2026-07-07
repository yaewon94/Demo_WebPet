package com.example.demo_webPet.common.address;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
final class AddressController {

    private static final Map<String, List<String>> ADDRESS = Map.of(
            "서울특별시", List.of(
                    "종로구",
                    "중구",
                    "용산구",
                    "광진구",
                    "강남구"
            ),
            "경기도", List.of(
                    "수원시",
                    "성남시",
                    "용인시"
            ),
            "부산광역시", List.of(
                    "해운대구",
                    "수영구"
            )
    );

    @GetMapping
    @ResponseBody
    public Map<String, List<String>> getAddressList(){
        Map<String, List<String>> result = new HashMap<>();
        result.put("address1", new ArrayList<>(ADDRESS.keySet()));
        return result;
    }

    @GetMapping(params = "address1")
    @ResponseBody
    public Map<String, List<String>> getAddress2(@RequestParam String address1) {
        Map<String, List<String>> result = new HashMap<>();
        result.put("address2", ADDRESS.getOrDefault(address1, List.of()));
        return result;
    }
}
