package com.fzz.api.controller.medical;

import com.fzz.common.result.ReturnResult;
import com.fzz.model.bo.AddMedicalRecordBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api7/medical")
@Api(value = "医疗就诊管理")
public interface MedicalControllerApi {

    @PostMapping("/treatmentRegister")
    @ApiOperation(value = "就诊登记")
    ReturnResult treatmentRegister(@RequestBody AddMedicalRecordBO addMedicalRecordBO);
}
