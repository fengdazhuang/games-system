package com.fzz.medical.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzz.api.BaseController;
import com.fzz.api.controller.medical.MedicineControllerApi;
import com.fzz.common.enums.ResponseStatusEnum;
import com.fzz.common.result.ReturnResult;
import com.fzz.medical.service.MedicineService;
import com.fzz.model.bo.AddMedicineBO;
import com.fzz.model.bo.ReplenishInventoryBO;
import com.fzz.model.entity.Medicine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MedicineController extends BaseController implements MedicineControllerApi {

    @Autowired
    private MedicineService medicineService;


    @Override
    public ReturnResult pageMedicine(Integer pageNumber, Integer pageSize, String name) {
        if(pageNumber<=0){
            pageNumber=COMMON_START_PAGE;
        }
        if(pageSize<=0){
            pageSize=COMMON_PAGE_SIZE;
        }
        Page<Medicine> page =medicineService.pageMedicine(pageNumber,pageSize,name);
        return ReturnResult.ok(page);
    }

    @Override
    public ReturnResult addMedicine(AddMedicineBO addMedicineBO) {
        boolean res = medicineService.saveMedicine(addMedicineBO);
        if(res){
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.MEDICINE_ADD_FAILED);
    }

    @Override
    public ReturnResult replenishInventory(ReplenishInventoryBO replenishInventoryBO) {
        boolean res = medicineService.replenishMedicineInventory(replenishInventoryBO);
        if(res){
            return ReturnResult.ok();
        }
        return ReturnResult.error(ResponseStatusEnum.MEDICINE_ADD_FAILED);
    }

    @Override
    public ReturnResult queryAllMedicines() {
        List<Medicine> list = medicineService.listMedicines();
        return ReturnResult.ok(list);
    }
}
