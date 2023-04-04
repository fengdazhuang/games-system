package com.fzz.common.result;

import com.fzz.common.enums.ResponseStatusEnum;
import lombok.Data;

import java.util.Map;

@Data
public class ReturnResult{

    private Integer code;

    private Boolean success;

    private String message;

    private Object data;

    public ReturnResult(Object data) {
        this.code = ResponseStatusEnum.SUCCESS.code();
        this.message = ResponseStatusEnum.SUCCESS.message();
        this.success = ResponseStatusEnum.SUCCESS.success();
        this.data = data;
    }

    public ReturnResult(ResponseStatusEnum responseStatus) {
        this.code = responseStatus.code();
        this.message = responseStatus.message();
        this.success = responseStatus.success();
    }

    public ReturnResult(ResponseStatusEnum responseStatus,Object data) {
        this.code = responseStatus.code();
        this.message = responseStatus.message();
        this.success = responseStatus.success();
        this.data=data;
    }

    /**
     * 直接返回数据
     * @param data 数据
     * @return 包装数据
     */
    public static ReturnResult ok(Object data){
        return new ReturnResult(data);
    }

    /**
     * 无返回值的成功响应
     * @return 成功的包装类
     */
    public static ReturnResult ok(){
        return new ReturnResult(ResponseStatusEnum.SUCCESS);
    }

    /**
     * 返回自定义错误信息
     * @param responseStatusEnum 自定义状态
     * @return 包装自定义状态
     */
    public static ReturnResult error(ResponseStatusEnum responseStatusEnum){
        return new ReturnResult(responseStatusEnum);
    }


    /**
     * 异常返回
     * @param responseStatusEnum 异常类型
     * @return 异常包装类
     */
    public static ReturnResult exception(ResponseStatusEnum responseStatusEnum) {
        return new ReturnResult(responseStatusEnum);
    }

    /**
     * 返回错误集合
     * @param errors 错误信息集合
     * @return 错误集合包装对象
     */
    public static ReturnResult errorMap(Map<String, String> errors) {
        return new ReturnResult(ResponseStatusEnum.FAILED,errors);
    }
}
