package com.alan.springbootgooglecloud.dto;

import com.alan.springbootgooglecloud.annotation.StringInfoParser;
import lombok.Data;

/**
 * @author Alan Dávila<br>
 *         26 jun. 2017 18:54
 */
@Data
public class PaidDTO {
    @StringInfoParser(start = 9, width = 3)
    private String id;
    @StringInfoParser(start = 12, width = 4)
    private Integer cantidad;
}
