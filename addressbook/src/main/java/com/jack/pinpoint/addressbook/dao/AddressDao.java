package com.jack.pinpoint.addressbook.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import com.jack.pinpoint.addressbook.domain.Address;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AddressDao {

    @Select("select id, name, address, mobile from addresses where id=#{id}")
    Address findById(Integer id);

    @Select("select id, name, address, mobile from addresses where name=#{name}")
    Address findByName(String name);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into addresses (name,address,mobile) values (#{name},#{address},#{mobile})")
    Integer insertAddress(Address alarmSource);

    @Select("select id, name, address, mobile from addresses")
    List<Address> getAllAddress();
}
