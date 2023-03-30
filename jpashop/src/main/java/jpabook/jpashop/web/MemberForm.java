package jpabook.jpashop.web;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {

    @NotEmpty(message = "name is mandatory value ")
    private String name;

    private String city;
    private String street;
    private String zipcode;
}
