package com.example.react.controller;

import com.example.react.dto.MemberDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "MemberAPI", description = "회원 도메인 API") // 라벨
@RestController
@RequestMapping("api/v1") // URL의 공통부분을 묶는다
public class SwaggerController { //http://localhost:10000/api/v1/members
    List<MemberDto> DB = new ArrayList<>();
    @GetMapping("members")
    // 성공, 실패 UI 설정
    @Operation( //개별 API 설명
            summary = "모든 사용자 Read",
            description = "회원 목록 조회"
    )
    @ApiResponses({ //API 응답(상태코드, 설명)을 Swagger에 정의
            //성공
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content( //요청/응답 바디의 내용을 설명
                            mediaType = "application/json", // 응답형식 지정
                            array = @ArraySchema(//배열 형태의 데이터 구조를 설명
                                    schema = @Schema(implementation = MemberDto.class) // 이 데이터의 실제 모델 클래스는 뭐다
                            ) //API요청/응답에 사용되는 데이터 모델의 구조를 설명
                    )
            ),
            //사용자 없음(실패)
            @ApiResponse(responseCode = "404", description = "사용자 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value="[]") //Swagger에서 보여줄 예시데이터
                    )
            )
    })
    public ResponseEntity<List<MemberDto>> apiTest(){
        //더미 데이터
        if(DB.size() == 0) {
            DB.add(new MemberDto(0, "aaaS", "aaa", "a-role"));
            DB.add(new MemberDto(1, "bbbS", "bbb", "b-role"));
            DB.add(new MemberDto(2, "cccS", "ccc", "c-role"));
        }
        return ResponseEntity.ok(DB);
        //return ResponseEntity.status(HttpStatus.NOT_FOUND).body(DB);
    }
    @GetMapping("members/{id}")
    // 성공, 실패 UI 설정
    @Operation(
            summary = "특정 사용자 Read",
            description = "사용자의 id를 파라미터로 전달"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = MemberDto.class)
                )
            ),
            @ApiResponse(responseCode = "404", description = "사용자 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value="null")
                    )
            )
    })

    public ResponseEntity<MemberDto> getOne(@PathVariable("id") int id){
        if(id == 0) //localhost:10000/members/번호(0)
            return ResponseEntity.ok(new MemberDto(0,"aaa","aaa","a-role"));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    @GetMapping("members/one")
    public ResponseEntity<MemberDto> getOne2(@RequestParam("id") int id){ //URL 또는 form 데이터에 있는 값을 하나씩 꺼내오는 것
        if(id == 0) //localhost:10000/members/one?id=0
            return ResponseEntity.ok(new MemberDto(0,"aaa","aaa","a-role"));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping("members")
    // 성공, 실패 UI 설정
    @Operation(
            summary = "사용자 추가",
            description = "사용자를 추가 합니다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value="추가 성공")
                    )
            ),
            @ApiResponse(responseCode = "409", description = "존재하는 사용자",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value="동일 id 존재")
                    )
            )
    })
    public ResponseEntity<String> insert(@ParameterObject @ModelAttribute MemberDto memberDto){
        if(memberDto.getId() == 0)      // @parameterobject: 이게 있어야 스웨거 UI에서 formdata로 인식
            return ResponseEntity.status(HttpStatus.CONFLICT).body("중복id");
        DB.add(memberDto);
        return ResponseEntity.ok("추가 성공");
    }
}
