package com.alan.springbootgooglecloud;

import com.alan.springbootgooglecloud.dto.ClientDTO;
import com.alan.springbootgooglecloud.dto.UsuarioDTO;
import com.alan.springbootgooglecloud.parser.ParserStringJson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alan Dávila<br>
 *         24 jun. 2017 11:05
 */
@RestController
@RequestMapping("/api")
public class RestServices {

    @RequestMapping("/test")
    public ResponseEntity<?> cliente(@RequestParam("text") final String text) {
        ClientDTO client = ParserStringJson.parseString(text, ClientDTO.class);
        return ResponseEntity.ok(client);
    }

    @RequestMapping("/usuario")
    public ResponseEntity<?> usuario(@RequestParam("text") final String text) {
        UsuarioDTO client = ParserStringJson.parseString(text, UsuarioDTO.class);
        return ResponseEntity.ok(client);
    }
}
