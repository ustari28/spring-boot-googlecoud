package com.alan.springbootgooglecloud.dto;

import com.alan.springbootgooglecloud.annotation.ClassInfoParse;
import com.alan.springbootgooglecloud.annotation.StringInfoParse;
import lombok.Data;

/**
 * @author Alan Dávila<br>
 *         28 jun. 2017 23:27
 */
@Data
@ClassInfoParse(separtor = "@")
public class UsuarioDTO {

    @StringInfoParse(order = 0)
    private String idUsuario;
    @StringInfoParse(order = 1)
    private String temporal;

}
