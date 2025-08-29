package com.customer.service.section17.service.impl;

import com.customer.service.section17.entity.ClientModel;
import com.customer.service.section17.mapper.ClientMapper;
import com.customer.service.section17.repository.ClientRepository;
import com.customer.service.section17.request.ClientRequest;
import com.customer.service.section17.response.APIClientResponse;
import com.customer.service.section17.response.ClientResponse;
import com.customer.service.section17.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.customer.service.section17.constant.CustomerConstant.*;
import static com.customer.service.section17.mapper.ClientMapper.modelToResponseMapper;
import static com.customer.service.section17.mapper.ClientMapper.requestToModel;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public ResponseEntity<APIClientResponse> createClient(ClientRequest request) {
        ClientModel clientModel = clientRepository.save(requestToModel(request));
        return ResponseEntity.ok(
                APIClientResponse.builder()
                        .errorCode(SUCCESS_CODE)
                        .errorMessage(SUCCESSFULLY_STORED)
                        .data(modelToResponseMapper(clientModel))
                        .build()
        );
    }

    @Override
    public ResponseEntity<APIClientResponse> getAllClient() {
        List<ClientModel> clientDetails = clientRepository.findAll();
        List<ClientResponse> clients = clientDetails
                .stream()
                .map(clientModel-> modelToResponseMapper(clientModel))
                .toList();

        return ResponseEntity.ok(
                APIClientResponse.builder()
                        .errorCode(SUCCESS_CODE)
                        .errorMessage(SUCCESSFULLY_RETRIEVED)
                        .data(clients)
                        .build()
        );
    }

    @Override
    public ResponseEntity<APIClientResponse> getByClientId(long clientId) {
        Optional<ClientModel> modelOptional = clientRepository.findById(clientId);

        if (modelOptional.isEmpty()) {
            return ResponseEntity.ok(
                    APIClientResponse.builder()
                            .errorCode(CUSTOMER_NOT_EXISTS_CODE)
                            .errorMessage(CUSTOMER_NOT_EXISTS)
                            .data(List.of())
                            .build()
            );
        }

        ClientModel model = modelOptional.get();
        return ResponseEntity.ok(
                APIClientResponse.builder()
                        .errorCode(SUCCESS_CODE)
                        .errorMessage(SUCCESSFULLY_RETRIEVED)
                        .data(modelToResponseMapper(model))
                        .build()
        );
    }

    @Override
    public ResponseEntity<APIClientResponse> deleteByClientId(long clientId) {
        Optional<ClientModel> modelOptional = clientRepository.findById(clientId);

        if (modelOptional.isEmpty()) {
            return ResponseEntity.ok(
                    APIClientResponse.builder()
                            .errorCode(CUSTOMER_NOT_EXISTS_CODE)
                            .errorMessage(CUSTOMER_NOT_EXISTS)
                            .data(List.of())
                            .build()
            );
        }

        ClientModel model = modelOptional.get();
        model.setStatus(com.customer.service.section17.enums.CustomerStatus.INACTIVE);
        clientRepository.save(model);

        return ResponseEntity.ok(
                APIClientResponse.builder()
                        .errorCode(SUCCESS_CODE)
                        .errorMessage(SUCCESSFULLY_DELETED)
                        .data(modelToResponseMapper(model))
                        .build()
        );
    }

    @Override
    public ResponseEntity<APIClientResponse> updateClientDetails(long clientId, ClientRequest request) {
        Optional<ClientModel> modelOptional = clientRepository.findById(clientId);

        if (modelOptional.isEmpty()) {
            return ResponseEntity.ok(
                    APIClientResponse.builder()
                            .errorCode(CUSTOMER_NOT_EXISTS_CODE)
                            .errorMessage(CUSTOMER_NOT_EXISTS)
                            .data(List.of())
                            .build()
            );
        }

        ClientModel model = modelOptional.get();
        model.setClientName(request.getClientName());
        model.setClientAge(request.getClientAge());
        model.setClientMobileNumber(request.getClientMobileNumber());
        model.setClientEmailAddress(request.getClientEmailAddress());
        model.setClientAddress(request.getClientAddress());
        model = clientRepository.save(model);

        return ResponseEntity.ok(
                APIClientResponse.builder()
                        .errorCode(SUCCESS_CODE)
                        .errorMessage(SUCCESSFULLY_UPDATED)
                        .data(modelToResponseMapper(model))
                        .build()
        );
    }
}
