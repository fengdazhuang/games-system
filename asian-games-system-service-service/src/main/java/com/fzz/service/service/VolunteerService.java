package com.fzz.service.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fzz.model.bo.*;
import com.fzz.model.dto.VolTeamDto;
import com.fzz.model.entity.VolPosition;
import com.fzz.model.entity.Volunteer;
import com.fzz.model.vo.PreVolunteerVO;
import com.fzz.model.vo.VolunteerVO;

import java.util.List;

public interface VolunteerService extends IService<Volunteer> {
    Page<VolunteerVO> pageVolunteers(Integer pageNumber, Integer pageSize, Integer volunteerType, Integer risk);

    Page<PreVolunteerVO> pagePreVolunteers(Integer pageNumber, Integer pageSize, Integer orderType);

    Volunteer getVolunteerByEmail(String email);

    boolean doReview(DoReviewBO doReviewBO);

    boolean saveVolunteer(Volunteer volunteer);

    boolean updateVolunteerRisk(ResetVolunteerRiskBO resetVolunteerRiskBO);

    Volunteer volunteerIsExists(String email);

    boolean updateVolunteerPassword(ModifyPasswordBO modifyPasswordBO);

    boolean forgetVolunteerPassword(VolunteerRegisterBO volunteerRegisterBO);

    boolean perfectOrUpdateVolunteerInfo(VolunteerInfoBO volunteerInfoBO);

    boolean updateVolunteerType(Long id, Integer volunteerType);

    boolean applyVolunteer(ApplyVolunteerBO applyVolunteerBO);

    Volunteer getVolunteerDetailById(Long id);

    VolTeamDto getVolTeamInfo(String teamId);

    List<Volunteer> getVolunteersByTeamId(String teamId);
}
