package com.sesac.sesacspring.jpaboard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDTO {
    private int boardID;
    private String title, content, writer, registered;
    private int no;
}
