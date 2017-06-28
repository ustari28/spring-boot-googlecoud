package com.alan.springbootgooglecloud.dto;

import com.alan.springbootgooglecloud.annotation.StringInfoParse;
import lombok.Data;

/**
 * @author Alan Dávila<br>
 *         26 jun. 2017 18:54
 */
@Data
public class PaidDTO {
    @StringInfoParse(start = 0, width = 3)
    private String id;
    @StringInfoParse(start = 3, width = 3)
    private Integer cantidad;
}
