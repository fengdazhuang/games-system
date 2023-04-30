package com.fzz.model.dto;

import com.fzz.model.vo.VolBaseInfoVO;
import lombok.Data;

import java.util.List;

@Data
public class VolTeamDto {

    private String teamId;

    private String name;

    private String position;

    private String principal;




    List<VolBaseInfoVO> members;
}
