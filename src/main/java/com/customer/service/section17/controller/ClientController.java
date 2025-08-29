package com.customer.service.section17.controller;

import com.customer.service.section17.request.ClientRequest;
import com.customer.service.section17.response.APIClientResponse;
import com.customer.service.section17.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity<APIClientResponse> createClient(@RequestBody @Valid ClientRequest request,
                                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    APIClientResponse.builder()
                            .errorCode(HttpStatus.BAD_REQUEST.value())
                            .errorMessage("Validation Failed")
                            .data(bindingResult.getFieldErrors()
                                    .stream()
                                    .map(fieldError -> fieldError.getDefaultMessage())
                                    .toList())
                            .build()
            );
        }
        return clientService.createClient(request);
    }

    @GetMapping("/getAllClients")
    public ResponseEntity<APIClientResponse> getAllClient() {
        return clientService.getAllClient();
    }

    @GetMapping("/getById/{clientId}")
    public ResponseEntity<APIClientResponse> getByClientId(@PathVariable long clientId) {
        return clientService.getByClientId(clientId);
    }

    @DeleteMapping("/deleteById/{clientId}")
    public ResponseEntity<APIClientResponse> deleteByClientId(@PathVariable long clientId) {
        return clientService.deleteByClientId(clientId);
    }

    @PutMapping("/updateById/{clientId}")
    public ResponseEntity<APIClientResponse> updateClient(@PathVariable long clientId, @RequestBody ClientRequest request) {
        return clientService.updateClientDetails(clientId, request);
    }
}
