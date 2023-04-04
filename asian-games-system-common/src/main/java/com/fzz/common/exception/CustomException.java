package com.fzz.common.exception;

import com.fzz.common.enums.ResponseStatusEnum;
import lombok.Data;

@Data
public class CustomException extends RuntimeException{

    private ResponseStatusEnum responseStatusEnum;

    public CustomException(ResponseStatusEnum responseStatusEnum){
        super(responseStatusEnum.code()+":"+responseStatusEnum.message());
        this.responseStatusEnum=responseStatusEnum;

    }

}
