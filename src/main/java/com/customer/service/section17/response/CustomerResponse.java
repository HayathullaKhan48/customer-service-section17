package com.customer.service.section17.response;

import com.customer.service.section17.enums.CustomerStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {
    private Long customerId;
    private String userName;
    private Integer customerAge;
    private String customerEmailAddress;
    private String customerMobileNumber;
    private String customerAddress;
    private CustomerStatus userStatus;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}