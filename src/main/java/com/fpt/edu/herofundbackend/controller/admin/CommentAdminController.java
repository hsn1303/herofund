package com.fpt.edu.herofundbackend.controller.admin;

import com.fpt.edu.herofundbackend.constant.SystemConstant;
import com.fpt.edu.herofundbackend.dto.CommonResponse;
import com.fpt.edu.herofundbackend.dto.PageDto;
import com.fpt.edu.herofundbackend.dto.StatusAndIdsRequest;
import com.fpt.edu.herofundbackend.dto.article.FilterArticleRequest;
import com.fpt.edu.herofundbackend.dto.comment.CommentRequest;
import com.fpt.edu.herofundbackend.dto.comment.FilterCommentRequest;
import com.fpt.edu.herofundbackend.entity.Article;
import com.fpt.edu.herofundbackend.entity.Comment;
import com.fpt.edu.herofundbackend.enums.ActionEnum;
import com.fpt.edu.herofundbackend.service.AuthenticateService;
import com.fpt.edu.herofundbackend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/admin/comments")
public class CommentAdminController {

    private final CommentService commentService;
    private final AuthenticateService authenticateService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CommonResponse> save(@RequestBody @Valid CommentRequest request, HttpServletRequest servlet) {
        CommonResponse response = new CommonResponse(false, SystemConstant.Message.SAVE_FAIL);
        Comment comment = commentService.saveCommentAdmin(request,
                authenticateService.getAccountByUsername(servlet.getUserPrincipal().getName()));
        if (null == comment) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessWithDataResponse(comment, SystemConstant.Message.SAVE_SUCCESS);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<CommonResponse> update(@RequestBody @Valid CommentRequest request) {
        CommonResponse response = new CommonResponse(false, SystemConstant.Message.UPDATE_FAIL);
        Comment comment = commentService.updateCommentAdmin(request);
        if (null == comment) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessWithDataResponse(comment, SystemConstant.Message.UPDATE_SUCCESS);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<PageDto<Comment>> getPage(
            @RequestParam(name = "offset", required = false, defaultValue = "1") int offset,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit
    ) {
        if (offset <= 0) offset = 1;
        if (limit <= 0) offset = 10;
        return ResponseEntity.ok(commentService.getPageAdmin(offset, limit));
    }

    @RequestMapping(path = "all", method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> getAll() {
        return ResponseEntity.ok(commentService.getAllAdmin());
    }

    @RequestMapping(path = "update/status",method = RequestMethod.PUT)
    public ResponseEntity<CommonResponse> updateStatus(
            @RequestParam(name = "id") long id,
            @Min(value = 0, message = SystemConstant.Message.STATUS_VALIDATION)
            @Max(value = 1, message = SystemConstant.Message.STATUS_VALIDATION)
            @RequestParam(name = "status") int status) {
        CommonResponse response = new CommonResponse(false, SystemConstant.Message.DELETE_FAIL);
        Comment comment = commentService.updateStatus(id, status);
        if (null == comment) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessWithDataResponse(comment, SystemConstant.Message.DELETE_SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @RequestMapping(path = "update-multiple/status", method = RequestMethod.PUT)
    public ResponseEntity<CommonResponse> updateStatusMultiple(@RequestBody @Valid StatusAndIdsRequest request) {
        CommonResponse response = new CommonResponse(false, SystemConstant.Message.UPDATE_FAIL);
        List<Comment> comments = commentService.updateMultipleStatusArticleByIds(request.getIds(), request.getStatus());
        if (comments == null) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessWithDataResponse(comments, SystemConstant.Message.UPDATE_SUCCESS);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(path = "detail", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse> getDetail(@RequestParam(name = "id") long id) {
        CommonResponse response = new CommonResponse();
        response.setSuccessWithDataResponse(commentService.getDetailAdmin(id));
        return ResponseEntity.ok(response);
    }

    @RequestMapping(path = "search", method = RequestMethod.POST)
    public PageDto<Comment> search(@RequestBody FilterCommentRequest request) {
        return commentService.findCommentPaging(request);
    }
}
