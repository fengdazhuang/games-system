package com.fzz.service.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fzz.model.entity.Volunteer;
import com.fzz.model.vo.PreVolunteerVO;
import com.fzz.model.vo.VolunteerVO;

public interface VolunteerService extends IService<Volunteer> {
    Page<VolunteerVO> pageVolunteers(Integer pageNumber, Integer pageSize, Integer volunteerType, String risk);

    Page<PreVolunteerVO> pagePreVolunteers(Integer pageNumber, Integer pageSize, Integer orderType);
}
