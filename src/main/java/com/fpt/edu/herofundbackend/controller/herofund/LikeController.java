package com.fpt.edu.herofundbackend.controller.herofund;

import com.fpt.edu.herofundbackend.dto.CommonResponse;
import com.fpt.edu.herofundbackend.dto.like.LikeRequest;
import com.fpt.edu.herofundbackend.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/like")
public class LikeController {
    private final LikeService likeService;

    @RequestMapping(method = RequestMethod.POST)
    public CommonResponse save(@RequestBody @Valid LikeRequest request) {
        return likeService.saveOrUpdate(request);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public CommonResponse update(@RequestBody @Valid LikeRequest request) {
        return likeService.saveOrUpdate(request);
    }

}
