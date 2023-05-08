package com.fzz.medical.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzz.common.utils.RedisUtil;
import com.fzz.medical.mapper.MedicineMapper;
import com.fzz.medical.service.MedicineRecordService;
import com.fzz.medical.service.MedicineService;
import com.fzz.model.bo.AddMedicineBO;
import com.fzz.model.bo.ReplenishInventoryBO;
import com.fzz.model.entity.Medicine;
import com.fzz.model.entity.MedicineRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.fzz.api.BaseController.REDIS_MEDICINE_INVENTORY;

@Service
public class MedicineServiceImpl extends ServiceImpl<MedicineMapper, Medicine> implements MedicineService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MedicineRecordService medicineRecordService;

    @Override
    public Page<Medicine> pageMedicine(Integer pageNumber, Integer pageSize, String name) {
        Page<Medicine> page = new Page<>(pageNumber,pageSize);
        LambdaQueryWrapper<Medicine> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name),Medicine::getName,name);
        queryWrapper.orderByDesc(Medicine::getCreateTime);
        this.page(page,queryWrapper);
        List<Medicine> newList = page.getRecords().stream().map(((item -> {
            Integer nowNumber = Integer.valueOf(redisUtil.get(REDIS_MEDICINE_INVENTORY + ":" + item.getId()));
            item.setInventory(nowNumber);
            return item;
        }))).collect(Collectors.toList());
        page.setRecords(newList);
        return page;
    }

    @Override
    @Transactional
    public boolean saveMedicine(AddMedicineBO addMedicineBO) {
        Medicine medicine = new Medicine();
        BeanUtils.copyProperties(addMedicineBO,medicine);
        medicine.setInventory(0);
        medicine.setCreateTime(new Date());
        return this.save(medicine);
    }

    @Override
    @Transactional
    public boolean replenishMedicineInventory(ReplenishInventoryBO replenishInventoryBO) {
        Integer addNumber = replenishInventoryBO.getAddNumber();
        Long id = replenishInventoryBO.getId();
//        LambdaUpdateWrapper<Medicine> updateWrapper=new LambdaUpdateWrapper<>();
//        updateWrapper.eq(Medicine::getId,id);
//        updateWrapper.set(Medicine::getInventory,oldNumber+addNumber);
        redisUtil.increment(REDIS_MEDICINE_INVENTORY+":"+id,addNumber);
        Integer nowNumber = Integer.valueOf(redisUtil.get(REDIS_MEDICINE_INVENTORY + ":" + id));
        MedicineRecord medicineRecord =new MedicineRecord();
        medicineRecord.setMedicineId(id);
        medicineRecord.setCreateTime(new Date());
        medicineRecord.setType(1);
        medicineRecord.setNowNumber(nowNumber);
        medicineRecord.setNumber(addNumber);
        medicineRecord.setMedicineName(replenishInventoryBO.getMedicineName());
        return medicineRecordService.save(medicineRecord);


    }

    @Override
    public List<Medicine> listMedicines() {
        return this.list();
    }

}
