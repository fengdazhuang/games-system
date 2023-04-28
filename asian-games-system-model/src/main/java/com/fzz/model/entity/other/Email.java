package com.fzz.model.entity.other;

import lombok.Data;

import java.util.List;


@Data
public class Email {
    String title;

    String body;

    List<String> receiver;
}
