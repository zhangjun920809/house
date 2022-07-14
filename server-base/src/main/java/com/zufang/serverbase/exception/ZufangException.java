package com.zufang.serverbase.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author User
 * @date 2022/7/14 18:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZufangException extends RuntimeException{

    public Integer code;
    public String message;

}
