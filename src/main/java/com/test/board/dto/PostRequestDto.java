package com.test.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class PostRequestDto {

    @NotBlank(message = "게시판 코드를 작성해주세요.")
    private String code;

    @NotBlank(message = "제목을 작성해주세요.")
    private String title;

    @NotBlank(message = "게시물 내용을 작성해주세요.")
    private String content;

    @NotBlank(message = "작성자 아이디를 작성해주세요.")
    @Pattern(regexp = "^[a-zA-Z]{1}[a-zA-Z0-9_-]{4,11}$", message = "아이디 형식에 맞지 않습니다.")
    @Size(max = 10, message = "아이디는 최대 10자까지 가능합니다.")
    private String authorId;
}
