package swa.application.swarestclient.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInfo {
    private String customerId;
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String zip;
    private String phone;
    private String email;

}
