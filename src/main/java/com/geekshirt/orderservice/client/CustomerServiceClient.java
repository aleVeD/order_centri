package com.geekshirt.orderservice.client;

import com.geekshirt.orderservice.config.OrderServiceConfig;
import com.geekshirt.orderservice.dto.AccountDto;
import com.geekshirt.orderservice.dto.AddressDto;
import com.geekshirt.orderservice.dto.CreditCardDto;
import com.geekshirt.orderservice.dto.CustomerDto;
import com.geekshirt.orderservice.util.AccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component

public class CustomerServiceClient {

    @Autowired
    private OrderServiceConfig config;

    private RestTemplate restTemplate;

    public CustomerServiceClient(RestTemplateBuilder builder) {
        restTemplate = builder.build();
    }

    public Optional<AccountDto> findAccountById(String accountId){
        Optional<AccountDto> result = Optional.empty();
        try{
           result = Optional.ofNullable(restTemplate.getForObject(config.getCustomerServiceUrl() + "/{id}", AccountDto.class, accountId));
        }catch (HttpClientErrorException ex){
            if(ex.getStatusCode() != HttpStatus.NOT_FOUND){
                throw ex;
            }
        }

        return  result;
    }

    public AccountDto createAccount(AccountDto account){
        return restTemplate.postForObject(config.getCustomerServiceUrl(), account, AccountDto.class);
    }

    public AccountDto createDummyAccount(){

        AddressDto adress = AddressDto.builder().street("Los lunes").city("Nueva York").country("USA").zipCode("46346").build();
        CustomerDto customer = CustomerDto.builder().lastName("Perez").firstName("Francisco").email("los@gmail.com").build();
        CreditCardDto cardDto = CreditCardDto.builder().nameOnCard("Juan Perez")
                .ccv("675").expirationMonth("12").expirationYear("2026").number("3285729375032").build();
        AccountDto account = AccountDto.builder().address(adress).customer(customer).creditCard(cardDto)
                .status(AccountStatus.ACTIVE).build();
        return account;
    }

    public AccountDto createAccountBody(AccountDto account){
        ResponseEntity<AccountDto> responseAccount = restTemplate.postForEntity(config.getCustomerServiceUrl(), account, AccountDto.class);
        return responseAccount.getBody();
    }

    public void updateAccount(AccountDto accountDto){
        restTemplate.put(config.getCustomerServiceUrl()+ "/{id}", accountDto, accountDto.getId());
    }

    public void deleteAccount(AccountDto accountDto){
        restTemplate.delete(config.getCustomerServiceUrl()+ "/{id}", accountDto.getId());
    }
}
