package com.fpt.edu.herofundbackend.controller.herofund;

import com.fpt.edu.herofundbackend.entity.Sponsor;
import com.fpt.edu.herofundbackend.enums.BaseStatusEnum;
import com.fpt.edu.herofundbackend.service.SponsorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/sponsors")
public class SponsorController {

    private final SponsorService sponsorService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Sponsor>> getAll() {
        return ResponseEntity.ok(sponsorService.getAllByStatus(BaseStatusEnum.ENABLE.getKey()));
    }

    @RequestMapping(path = "detail", method = RequestMethod.GET)
    public ResponseEntity<Sponsor> getDetail(@RequestParam(name = "id") long id) {
        return ResponseEntity.ok(sponsorService.getDetailByStatus(id, BaseStatusEnum.ENABLE.getKey()));
    }

}
