package com.fzz.api.controller.news;

import com.fzz.common.result.ReturnResult;
import com.fzz.model.bo.AddNewsBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/ReadNews")
    @ApiOperation(value = "阅读新闻")
    ReturnResult readNews(@RequestParam Long id, HttpServletRequest request);

    @GetMapping("/getReadCounts")
    @ApiOperation(value = "获取新闻阅读数")
    ReturnResult getReadCounts(@RequestParam Long id);

    @GetMapping("/hotNews")
    @ApiOperation(value = "最热的十条资讯")
    ReturnResult getHotNews();

}
