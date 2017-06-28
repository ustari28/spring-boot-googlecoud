package com.alan.springbootgooglecloud.dto;

import com.alan.springbootgooglecloud.annotation.StringInfoParse;
import lombok.Data;

/**
 * @author Alan Dávila<br>
 *         26 jun. 2017 17:01
 */
@Data
public class ClientDTO {
    @StringInfoParse(start = 0, width = 3)
    private String id;
    @StringInfoParse(start = 3, width = 4)
    private String name;
    @StringInfoParse(start = 7, width = 2)
    private Integer cantidad;
    @StringInfoParse(start = 9, width = 11)
    private PaidDTO paidDTO;
    @StringInfoParse(start = 20, width = 10, size = 2)
    private String[] strings;
    @StringInfoParse(start = 30, width = 36, size = 6)
    private PaidDTO[] paidArray;
}
