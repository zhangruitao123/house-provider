package cn.house.houseprovider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.house.houseprovider.mapper")
public class HouseProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(HouseProviderApplication.class, args);
    }

}
