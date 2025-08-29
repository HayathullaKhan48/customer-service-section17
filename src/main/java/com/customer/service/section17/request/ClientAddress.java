package com.customer.service.section17.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString
@EqualsAndHashCode
@Builder(toBuilder = true)
@Embeddable
public class ClientAddress {

    @JsonProperty("address")
    @NotBlank(message = "Please provide the customer address")
    private String address;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("country")
    @NotBlank(message = "Please provide the country")
    private String country;
}
