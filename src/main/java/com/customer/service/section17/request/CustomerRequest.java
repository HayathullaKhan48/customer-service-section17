package com.customer.service.section17.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequest {
    private String userName;
    private Integer customerAge;
    private String customerMobileNumber;
    private String customerEmailAddress;
    private String customerAddress;
}
