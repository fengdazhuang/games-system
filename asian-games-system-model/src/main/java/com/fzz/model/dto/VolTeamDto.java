package com.fzz.model.dto;

import com.fzz.model.vo.VolBaseInfoVO;
import lombok.Data;

import java.util.List;

@Data
public class VolTeamDto {

    private String teamId;

    private String name;

    private String position;


    List<VolBaseInfoVO> members;
}
