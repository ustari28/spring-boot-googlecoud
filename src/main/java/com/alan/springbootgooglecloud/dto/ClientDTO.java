package com.alan.springbootgooglecloud.dto;

import com.alan.springbootgooglecloud.annotation.StringInfoParser;
import lombok.Data;

/**
 * @author Alan Dávila<br>
 *         26 jun. 2017 17:01
 */
@Data
public class ClientDTO {
    @StringInfoParser(start = 0, width = 3)
    private String id;
    @StringInfoParser(start = 3, width = 4)
    private String name;
    @StringInfoParser(start = 7, width = 2)
    private Integer cantidad;
    private PaidDTO paidDTO;
}
