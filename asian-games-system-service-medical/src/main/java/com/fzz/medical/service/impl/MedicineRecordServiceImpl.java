package com.fzz.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzz.medical.mapper.MedicineRecordMapper;
import com.fzz.medical.service.MedicineRecordService;
import com.fzz.model.entity.MedicineRecord;
import org.springframework.stereotype.Service;

@Service
public class MedicineRecordServiceImpl extends ServiceImpl<MedicineRecordMapper, MedicineRecord> implements MedicineRecordService {
}
