package com.fzz.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzz.model.bo.AddMedicalRecordBO;
import com.fzz.model.entity.MedicalRecord;

public interface MedicalRecordService extends IService<MedicalRecord> {
    boolean addTreatmentRegister(AddMedicalRecordBO addMedicalRecordBO);
}
