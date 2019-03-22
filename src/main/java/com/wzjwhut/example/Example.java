package com.wzjwhut.example;

import com.dangdang.ddframe.rdb.sharding.keygen.KeyGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

@RestController
@SpringBootApplication
@SpringBootConfiguration
@ControllerAdvice
public class Example  implements ApplicationRunner{
    private final static Logger log = LogManager.getLogger(Example.class);

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private KeyGenerator keyGenerator; /** 默认为snowflake算法 */

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }


    public static void main(String[] args) {
        log.info("main");
        SpringApplication.run(Example.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("============= springboot started ==============");
        {
//            Person person = new Person();
//            person.setId(1);
//            person.setEmailAddress("wzj_whut@163.com");
//            person.setFirstname("wzj");
//            person.setLastname("whut");
//            personRepository.save(person);
        }

        {
            //personRepository.findById((long) 1);
            userRepository.findById(1);
        }

        {
            //personRepository.findByIdBetween(1, 10);
        }

        {
//            userRepository.findById(1);
        }

        {
            //personRepository.findByLastnameIgnoreCase("wzj");
        }
    }

}
