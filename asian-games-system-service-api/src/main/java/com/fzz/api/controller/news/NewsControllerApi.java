package com.fzz.api.controller.news;

import com.fzz.common.result.ReturnResult;
import com.fzz.model.bo.AddNewsBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RequestMapping("/api2/news")
@Api(value = "newsController")
public interface NewsControllerApi {

    @PostMapping("/addNews")
    @ApiOperation(value = "创建新闻")
    ReturnResult addNews(@Valid @RequestBody AddNewsBO addNewsBO);

    @GetMapping("/queryNews")
    @ApiOperation(value = "按条件分页查询新闻")
    ReturnResult queryNews(@RequestParam Integer pageNumber,
                           @RequestParam Integer pageSize,
                           @RequestParam Date startDate,
                           @RequestParam Date endDate,
                           @RequestParam String keyword,
                           @RequestParam Integer status);

    @DeleteMapping("/deleteNews")
    @ApiOperation(value = "删除新闻")
    ReturnResult deleteNews(@RequestParam Long id);

    @PutMapping("/withdraw")
    @ApiOperation(value = "撤回新闻")
    ReturnResult withdrawNews(@RequestParam Long id);

    @PutMapping("/update")
    @ApiOperation(value = "更新新闻")
    ReturnResult updateNews(@Valid @RequestBody AddNewsBO addNewsBO);
}
