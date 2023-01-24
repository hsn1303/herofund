package com.fpt.edu.herofundbackend.controller.admin;

import com.fpt.edu.herofundbackend.constant.SystemConstant;
import com.fpt.edu.herofundbackend.dto.CommonResponse;
import com.fpt.edu.herofundbackend.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/admin/profiles")
public class ProfileAdminController {

    private final ProfileService profileService;

    @RequestMapping(method = RequestMethod.GET)
    public CommonResponse getProfileByAccountId(@RequestParam(name = "accountId") long accountId) {
        CommonResponse commonResponse = new CommonResponse();
        try {
            commonResponse.setSuccessWithDataResponse(profileService.getProfileById(accountId));
            return commonResponse;
        }catch (Exception e){
            log.error("API (GET) api/v1/profile fail: {}", e.getMessage());
            commonResponse.setFailResponse(SystemConstant.Message.SYSTEM_ERROR);
        }
        return commonResponse;
    }

}
