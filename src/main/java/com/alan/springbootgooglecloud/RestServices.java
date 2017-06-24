package com.alan.springbootgooglecloud;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alan Dávila<br>
 *         24 jun. 2017 11:05
 */
@RestController
@RequestMapping("/api")
public class RestServices {

    @RequestMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("OK");
    }
}
