package com.alan.springbootgooglecloud;

import com.alan.springbootgooglecloud.dto.ClientDTO;
import com.alan.springbootgooglecloud.parser.ParserStringJson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Alan Dávila<br>
 *         24 jun. 2017 11:05
 */
@RestController
@RequestMapping("/api")
public class RestServices {

    @RequestMapping("/test")
    public ResponseEntity<?> test(@RequestParam("text") final String text) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ClientDTO client = ParserStringJson.parserString(text, ClientDTO.class);
        return ResponseEntity.ok(client);
    }
}
