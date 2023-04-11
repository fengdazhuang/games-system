package com.fzz.model.constant;

import lombok.Data;

import java.util.List;


@Data
public class Email {
    String title;

    String body;

    List<String> receiver;
}
