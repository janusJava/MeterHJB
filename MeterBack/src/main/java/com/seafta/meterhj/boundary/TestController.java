package com.seafta.meterhj.boundary;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.seafta.meterhj.domain.dto.meter.MeterDetails;
import io.swagger.v3.oas.annotations.Operation;
import jdk.security.jarsigner.JarSigner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class TestController {


    @Operation(summary = "GetTest", description = "TEST_GET")
    @GetMapping(value = "/{longtitude}/{latitude}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    String getTest(@PathVariable("longtitude") String longtitude, @PathVariable("latitude") String latitude) {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("https://us1.locationiq.com/v1/reverse?key=pk.dfc8d842c17c35e748779f219267176e&lat="+latitude+"&lon="+longtitude+"&format=json" ,String.class);

        String myJSONString = response.getBody().toString();
        JsonObject jsonObject = new Gson().fromJson(myJSONString, JsonObject.class);

        String result = jsonObject.get("display_name").toString();

        return result;
    }

}
