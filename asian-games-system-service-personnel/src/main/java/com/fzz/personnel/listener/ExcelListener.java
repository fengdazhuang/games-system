package com.fzz.personnel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.fzz.model.entity.excel.ExcelPlayer;

public class ExcelListener implements ReadListener<ExcelPlayer> {


    @Override
    public void invoke(ExcelPlayer excelPlayer, AnalysisContext analysisContext) {

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
