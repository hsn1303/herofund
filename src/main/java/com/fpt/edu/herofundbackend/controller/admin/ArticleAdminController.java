package com.fpt.edu.herofundbackend.controller.admin;

import com.fpt.edu.herofundbackend.constant.SystemConstant;
import com.fpt.edu.herofundbackend.dto.CommonResponse;
import com.fpt.edu.herofundbackend.dto.PageDto;
import com.fpt.edu.herofundbackend.dto.StatusAndIdsRequest;
import com.fpt.edu.herofundbackend.dto.article.ArticleRequest;
import com.fpt.edu.herofundbackend.dto.article.FilterArticleRequest;
import com.fpt.edu.herofundbackend.dto.campaign.FilterCampaignRequest;
import com.fpt.edu.herofundbackend.entity.Article;
import com.fpt.edu.herofundbackend.entity.Campaign;
import com.fpt.edu.herofundbackend.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Validated
@CrossOrigin("*")
@RequestMapping("api/v1/admin/articles")
public class ArticleAdminController {

    private final ArticleService articleService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CommonResponse> save(@RequestBody @Valid ArticleRequest request) {
        CommonResponse response = new CommonResponse(false, SystemConstant.Message.SAVE_FAIL);
        Article article = articleService.save(request);
        if (null == article) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessWithDataResponse(article, SystemConstant.Message.SAVE_SUCCESS);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<CommonResponse> update(@RequestBody @Valid ArticleRequest request) {
        CommonResponse response = new CommonResponse(false, SystemConstant.Message.UPDATE_FAIL);
        Article article = articleService.update(request);
        if (null == article) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessWithDataResponse(article, SystemConstant.Message.UPDATE_SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @RequestMapping(path = "update/status",method = RequestMethod.PUT)
    public ResponseEntity<CommonResponse> updateStatus(
            @RequestParam(name = "id") long id,
            @Min(value = 0, message = SystemConstant.Message.STATUS_VALIDATION)
            @Max(value = 1, message = SystemConstant.Message.STATUS_VALIDATION)
            @RequestParam(name = "status") int status) {
        CommonResponse response = new CommonResponse(false, SystemConstant.Message.DELETE_FAIL);
        Article article = articleService.updateStatus(id, status);
        if (null == article) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessWithDataResponse(article, SystemConstant.Message.DELETE_SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @RequestMapping(path = "update-multiple/status", method = RequestMethod.PUT)
    public ResponseEntity<CommonResponse> updateStatusMultiple(@RequestBody @Valid StatusAndIdsRequest request) {
        CommonResponse response = new CommonResponse(false, SystemConstant.Message.UPDATE_FAIL);
        List<Article> articles = articleService.updateMultipleStatusArticleByIds(request.getIds(), request.getStatus());
        if (articles == null) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessWithDataResponse(articles, SystemConstant.Message.UPDATE_SUCCESS);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<PageDto<Article>> getPage(
            @RequestParam(name = "offset", required = false, defaultValue = "1") int offset,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit
    ) {
        if (offset <= 0) offset = 1;
        if (limit <= 0) offset = 10;
        return ResponseEntity.ok(articleService.getPageAdmin(offset, limit));
    }

    @RequestMapping(path = "all", method = RequestMethod.GET)
    public ResponseEntity<List<Article>> getAll() {
        return ResponseEntity.ok(articleService.getAllAdmin());
    }

    @RequestMapping(path = "detail", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse> getDetail(@RequestParam(name = "id") long id) {
        CommonResponse response = new CommonResponse();
        response.setSuccessWithDataResponse(articleService.getDetailAdmin(id));
        return ResponseEntity.ok(response);
    }

    @RequestMapping(path = "search", method = RequestMethod.POST)
    public PageDto<Article> search(@RequestBody FilterArticleRequest request) {
        return articleService.findArticlePaging(request);
    }

}
