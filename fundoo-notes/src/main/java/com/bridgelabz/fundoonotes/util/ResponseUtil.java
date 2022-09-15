package com.bridgelabz.fundoonotes.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Ashwini Rathod
 * @version: 1.0
 * @since : 09-09-2022
 * Purpose : Response used to handle the exception
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUtil {
    private int errorCode;
    private String message;
    private Object token;

}
