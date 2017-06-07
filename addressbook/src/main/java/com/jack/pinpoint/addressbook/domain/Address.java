package com.jack.pinpoint.addressbook.domain;

import com.sun.scenario.effect.Merge;
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
