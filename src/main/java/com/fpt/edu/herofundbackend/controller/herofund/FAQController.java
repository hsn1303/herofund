package com.fpt.edu.herofundbackend.controller.herofund;

import com.fpt.edu.herofundbackend.dto.CommonResponse;
import com.fpt.edu.herofundbackend.entity.FAQ;
import com.fpt.edu.herofundbackend.service.FAQService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/faqs")
public class FAQController {

    private final FAQService FAQService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<CommonResponse> getDetail(@RequestParam(name = "id") long id) {
        CommonResponse response = new CommonResponse();
        FAQ faq = FAQService.getDetail(id);
        response.setSuccessWithDataResponse(faq);
        return ResponseEntity.ok(response);
    }

}
