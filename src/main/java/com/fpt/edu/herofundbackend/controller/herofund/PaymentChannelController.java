package com.fpt.edu.herofundbackend.controller.herofund;

import com.fpt.edu.herofundbackend.dto.channel.PaymentChannelDto;
import com.fpt.edu.herofundbackend.entity.BaseEntity;
import com.fpt.edu.herofundbackend.entity.PaymentChannel;
import com.fpt.edu.herofundbackend.enums.BaseStatusEnum;
import com.fpt.edu.herofundbackend.service.PaymentChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/channels")
public class PaymentChannelController {

    private final PaymentChannelService paymentChannelService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<PaymentChannelDto>> getAll() {
        List<PaymentChannel> list = paymentChannelService.getPaymentChannelByStatus(BaseStatusEnum.ENABLE.getKey());
        return ResponseEntity.ok(list.stream().map(PaymentChannelDto::new).collect(Collectors.toList()));
    }
}
