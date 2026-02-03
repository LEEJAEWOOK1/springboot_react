package com.example.react.config;

import com.example.react.dto.MemberDto;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
//Swagger(OpenAPI 설정)
@Configuration // 이 클래스는 스프링 설정 클래스
public class OpenAPiConfig {
    @Bean //객체로 만들어서 추가해 줘라(객체를 생성해서 스프링이 관리하도록 등록한다)
    public MemberDto memberDto(){
        return new MemberDto();
    }
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(
                        new Info() //API 정보 설정
                                .title("연습 OpenAPI") //API 이름
                                .description("스웨거 연습") //설명
                                .version("v1.0.0") //버전
                )
                .servers(List.of( //서버 정보 설정
                        new Server().url("http://localhost:10000") //요청 보낼 서버 주소
                                .description("개발용 서버 주소") //설명
        ));
    }
}
