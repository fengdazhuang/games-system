package com.fzz.medical.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fzz.model.bo.AddMedicineBO;
import com.fzz.model.bo.ReplenishInventoryBO;
import com.fzz.model.entity.Medicine;

public interface MedicineService extends IService<Medicine> {
    Page<Medicine> pageMedicine(Integer pageNumber, Integer pageSize, String name);

    boolean saveMedicine(AddMedicineBO addMedicineBO);

    boolean replenishMedicineInventory(ReplenishInventoryBO replenishInventoryBO);
}
