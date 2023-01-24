package com.fpt.edu.herofundbackend.controller.herofund;

import com.fpt.edu.herofundbackend.dto.CommonResponse;
import com.fpt.edu.herofundbackend.dto.comment.CommentRequest;
import com.fpt.edu.herofundbackend.enums.ActionEnum;
import com.fpt.edu.herofundbackend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/comments")
public class CommentController {

    private final CommentService commentService;

//    @RequestMapping(method = RequestMethod.POST)
//    public CommonResponse save(@RequestBody @Valid CommentRequest request) {
//        return commentService.saveOrUpdate(request, ActionEnum.SAVE.getKey());
//    }

}
