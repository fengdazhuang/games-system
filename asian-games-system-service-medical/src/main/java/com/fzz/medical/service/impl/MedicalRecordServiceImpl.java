package com.fzz.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzz.common.enums.ResponseStatusEnum;
import com.fzz.common.exception.CustomException;
import com.fzz.common.utils.JsonUtils;
import com.fzz.common.utils.RedisUtil;
import com.fzz.medical.mapper.MedicalRecordMapper;
import com.fzz.medical.service.MedicalRecordService;
import com.fzz.medical.service.MedicineRecordService;
import com.fzz.model.bo.AddMedicalRecordBO;
import com.fzz.model.dto.ObjectCountDto;
import com.fzz.model.entity.MedicalRecord;
import com.fzz.model.entity.MedicineRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class MedicalRecordServiceImpl extends ServiceImpl<MedicalRecordMapper, MedicalRecord> implements MedicalRecordService {

    @Autowired
    private MedicineRecordService medicineRecordService;

    @Autowired
    private RedisUtil redisUtil;

    private static final String REDIS_MEDICINE_INVENTORY = "redis_medicine_inventory";


    @Override
    @Transactional
    public boolean addTreatmentRegister(AddMedicalRecordBO addMedicalRecordBO) {
        MedicalRecord medicalRecord=new MedicalRecord();
        BeanUtils.copyProperties(addMedicalRecordBO,medicalRecord);
        medicalRecord.setCreateTime(new Date());
        String medicine = addMedicalRecordBO.getMedicine();
        List<ObjectCountDto> medicineList = JsonUtils.jsonToList(medicine, ObjectCountDto.class);
        if(medicineList!=null){
            List<MedicineRecord> medicineRecordList = null;
            for(ObjectCountDto objectCountDto:medicineList){
                MedicineRecord medicineRecord=new MedicineRecord();
                medicineRecord.setMedicineId(objectCountDto.getId());
                medicineRecord.setCreateTime(new Date());
                medicineRecord.setNumber(objectCountDto.getCount());
                medicineRecord.setMedicineName(objectCountDto.getName());
                medicineRecord.setType(0);
                medicineRecordList.add(medicineRecord);
            }
            boolean res = medicineRecordService.saveBatch(medicineRecordList);
            if(res){
                for (ObjectCountDto objectCountDto:medicineList){
                    redisUtil.decrement(REDIS_MEDICINE_INVENTORY+":"+objectCountDto.getId(),objectCountDto.getCount());
                }
            }else{
                throw new CustomException(ResponseStatusEnum.SYSTEM_BUSY_ERROR);
            }
        }

        return this.save(medicalRecord);
    }
}
