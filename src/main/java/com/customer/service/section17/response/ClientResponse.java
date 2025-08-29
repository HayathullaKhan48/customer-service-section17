package com.customer.service.section17.response;

import com.customer.service.section17.enums.CustomerStatus;
import com.customer.service.section17.request.ClientAddress;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder(toBuilder = true)
public class ClientResponse {
    private Long clientId;
    private String clientName;
    private Integer clientAge;
    private String clientMobileNumber;
    private String clientEmailAddress;
    private ClientAddress clientAddress;
    private String status;
    private boolean verified;
    private LocalDateTime createDate;
    private LocalDateTime updatedDate;
}
