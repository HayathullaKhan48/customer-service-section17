package com.customer.service.section17.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder(toBuilder = true)
@ToString
@EqualsAndHashCode
public class ClientRequest {

    @JsonProperty("clientName")
    @NotBlank(message = "Please provide username")
    private String clientName;

    @JsonProperty("ClientAge")
    private int clientAge;

    @JsonProperty("Client")
    @NotBlank(message = "Please provide clientMobileNumber")
    @Size(min = 10, max = 10,message = "Please provide the valid mobile number..")
    private String clientMobileNumber;

    @JsonProperty("clientEmailAddress")
    @NotBlank(message = "Please provide clientEmailAddress")
    @Email(message = "Please provide valid email address..")
    private String clientEmailAddress;

    @JsonProperty("clientAddress")
    @Valid
    private ClientAddress clientAddress;
}
