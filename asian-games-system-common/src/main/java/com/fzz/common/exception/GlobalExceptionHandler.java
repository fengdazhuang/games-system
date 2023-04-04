package com.fzz.common.exception;


import com.fzz.common.result.ReturnResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 */

@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {


   /**
     * 运行时异常处理方法
    */
    @ExceptionHandler(CustomException.class)
    public ReturnResult exceptionHandler(CustomException exception){
        return ReturnResult.exception(exception.getResponseStatusEnum());
    }

//    /**
//     * 越界异常处理
//     * @param exception 越界异常
//     * @return 返回
//     */
//    @ExceptionHandler(MaxUploadSizeExceededException.class)
//    public ReturnResult returnMaxUploadSizeExceededException(MaxUploadSizeExceededException exception){
//        return ReturnResult.errorCustom(ResponseStatusEnum.FILE_MAX_SIZE_ERROR);
//    }


    /**
     * bo对象不匹配处理
     * @param exception bo对象不匹配异常
     * @return 返回
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ReturnResult methodErrorHandler(MethodArgumentNotValidException exception){
        BindingResult bindingResult = exception.getBindingResult();
        return ReturnResult.errorMap(getErrors(bindingResult));
    }

    /**
     * 获取BO中的错误信息
     * @param result bo校验错误信息
     */
    public Map<String, String> getErrors(BindingResult result) {
        Map<String, String> map = new HashMap<>();
        List<FieldError> errorList = result.getFieldErrors();
        for (FieldError error : errorList) {
            // 发送验证错误的时候所对应的某个属性
            String field = error.getField();
            // 验证的错误消息
            String msg = error.getDefaultMessage();
            map.put(field, msg);
        }
        return map;
    }
}
