package com.fzz.medical.controller;

import com.fzz.api.BaseController;
import com.fzz.api.controller.medical.MedicalControllerApi;
import com.fzz.common.enums.ResponseStatusEnum;
import com.fzz.common.result.ReturnResult;
import com.fzz.medical.service.MedicalRecordService;
import com.fzz.model.bo.AddMedicalRecordBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MedicalController extends BaseController implements MedicalControllerApi {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Override
    public ReturnResult treatmentRegister(AddMedicalRecordBO addMedicalRecordBO) {
        boolean res = medicalRecordService.addTreatmentRegister(addMedicalRecordBO);
        if(res){
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.TREATMENT_REGISTER_FAILED);
    }
}
