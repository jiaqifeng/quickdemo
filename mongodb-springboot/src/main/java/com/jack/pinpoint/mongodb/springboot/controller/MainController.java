package com.jack.pinpoint.mongodb.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.mongodb.core.MongoTemplate;
    
import com.jack.pinpoint.mongodb.springboot.dao.StudentRepository;
import com.jack.pinpoint.mongodb.springboot.dao.Student;

@RestController
public class MainController {
    private Logger logger =  LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;
    
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Student query() {
	return studentRepository.findByName("jack");
        //return "stub ok";
    }

    @RequestMapping(value = "/insertone", method = RequestMethod.GET)
    @ResponseBody
    public String insertone() {
	Student st=new Student();
	st.setName("jack");
	st.setAge(23);
	st.setEmail("a@a.com");
	studentRepository.save(st);
        return "stub ok";
    }

    @RequestMapping(value = "/insertone", method = RequestMethod.POST)
    @ResponseBody
    public String sendMessage() {
	//TODO: send a text message
        return "ok";
    }

}
