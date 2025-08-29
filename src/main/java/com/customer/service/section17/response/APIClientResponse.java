package com.customer.service.section17.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder(toBuilder = true)
public class APIClientResponse {
    private int errorCode;
    private String errorMessage;
    private Object data;
}
