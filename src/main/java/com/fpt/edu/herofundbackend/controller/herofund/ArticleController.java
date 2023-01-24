package com.fpt.edu.herofundbackend.controller.herofund;

import com.fpt.edu.herofundbackend.dto.CommonResponse;
import com.fpt.edu.herofundbackend.dto.PageDto;
import com.fpt.edu.herofundbackend.entity.Article;
import com.fpt.edu.herofundbackend.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/articles")
public class ArticleController {

    private final ArticleService articleService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<PageDto<Article>> getPageByStatusAndCampaignId(
            @RequestParam(name = "offset", required = false, defaultValue = "1") int offset,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(name = "campaignId") int campaignId
    ) {
        if (offset <= 0) offset = 1;
        if (limit <= 0) offset = 10;
        return ResponseEntity.ok(articleService.getPageByStatusAndCampaignId(offset, limit, campaignId));
    }
}
