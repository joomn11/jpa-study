package com.jpa.doamin;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {

    @Column(length = 10)
    private String city;

    @Column(length = 20)
    private String street;

    @Column(length = 5)
    private String zipcode;

    public String fullAddress() {
        return getCity() + " " + getStreet() + " " + getZipcode();
    }
}
