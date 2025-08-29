package com.customer.service.section17.mapper;

import com.customer.service.section17.entity.ClientModel;
import com.customer.service.section17.enums.CustomerStatus;
import com.customer.service.section17.request.ClientRequest;
import com.customer.service.section17.response.ClientResponse;

import static com.customer.service.section17.util.CustomerUtil.generateOtp;
import static com.customer.service.section17.util.CustomerUtil.generatePassword;

public class ClientMapper {

    public static ClientResponse modelToResponseMapper(ClientModel clientModel) {
        return ClientResponse.builder()
                .clientId(clientModel.getClientId())
                .clientName(clientModel.getClientName())
                .clientAge(clientModel.getClientAge())
                .clientMobileNumber(clientModel.getClientMobileNumber())
                .clientEmailAddress(clientModel.getClientEmailAddress())
                .clientAddress(clientModel.getClientAddress())
                .createDate(clientModel.getCreateDate())
                .status(clientModel.getStatus().name())
                .verified(clientModel.isVerified())
                .updatedDate(clientModel.getUpdatedDate())
                .build();
    }

    public static ClientModel requestToModel(ClientRequest request){
        return ClientModel.builder()
                .clientName(request.getClientName())
                .clientPassword(generatePassword())
                .clientAge(request.getClientAge())
                .clientMobileNumber(request.getClientMobileNumber())
                .clientEmailAddress(request.getClientEmailAddress())
                .clientAddress(request.getClientAddress())
                .status(CustomerStatus.ACTIVE)
                .clientOtp(generateOtp())
                .verified(false)
                .build();
    }
}
