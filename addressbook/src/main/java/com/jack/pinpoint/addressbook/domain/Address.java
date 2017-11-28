package com.jack.pinpoint.addressbook.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Address {
    Integer id;
    String name;
    String address;
    String mobile;
}
