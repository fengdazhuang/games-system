package com.fzz.api.controller.medical;

import com.fzz.common.result.ReturnResult;
import com.fzz.model.bo.AddMedicineBO;
import com.fzz.model.bo.ReplenishInventoryBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api7/medicine")
@Api(value = "药品管理")
public interface MedicineControllerApi {

    @GetMapping("/pageMedicine")
    @ApiOperation(value = "分页查看药品库存信息")
    ReturnResult pageMedicine(@RequestParam Integer pageNumber,
                              @RequestParam Integer pageSize,
                              @RequestParam String name);

    @PostMapping("/addMedicine")
    @ApiOperation(value = "添加药品")
    ReturnResult addMedicine(@RequestBody AddMedicineBO addMedicineBO);


    @PutMapping("/replenishInventory")
    @ApiOperation(value = "扩充药品库存")
    ReturnResult replenishInventory(@RequestBody ReplenishInventoryBO replenishInventoryBO);

    @GetMapping("/queryMedicines")
    @ApiOperation(value="查询所有的药品")
    ReturnResult queryAllMedicines();

}
