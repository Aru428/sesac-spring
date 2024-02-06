package com.sesac.sesacspring.jpa.DTO;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StudentDTO {
    private String name;
    private String nickname;
}
