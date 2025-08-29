package com.customer.service.section17.service;

import com.customer.service.section17.request.ClientRequest;
import com.customer.service.section17.response.APIClientResponse;
import org.springframework.http.ResponseEntity;

public interface ClientService {
    ResponseEntity<APIClientResponse> createClient(ClientRequest request);
    ResponseEntity<APIClientResponse> getAllClient();
    ResponseEntity<APIClientResponse> getByClientId(long clientId);
    ResponseEntity<APIClientResponse> deleteByClientId(long clientId);
    ResponseEntity<APIClientResponse> updateClientDetails(long clientId, ClientRequest request);
}
