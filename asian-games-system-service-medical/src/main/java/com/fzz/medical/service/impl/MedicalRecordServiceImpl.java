package com.fzz.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzz.medical.mapper.MedicalRecordMapper;
import com.fzz.medical.service.MedicalRecordService;
import com.fzz.model.bo.AddMedicalRecordBO;
import com.fzz.model.entity.MedicalRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class MedicalRecordServiceImpl extends ServiceImpl<MedicalRecordMapper, MedicalRecord> implements MedicalRecordService {
    @Override
    @Transactional
    public boolean addTreatmentRegister(AddMedicalRecordBO addMedicalRecordBO) {
        MedicalRecord medicalRecord=new MedicalRecord();
        BeanUtils.copyProperties(addMedicalRecordBO,medicalRecord);
        medicalRecord.setCreateTime(new Date());
        return this.save(medicalRecord);
    }
}
