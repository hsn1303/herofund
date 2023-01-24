package com.fpt.edu.herofundbackend.controller.admin;

import com.fpt.edu.herofundbackend.constant.SystemConstant;
import com.fpt.edu.herofundbackend.dto.CommonResponse;
import com.fpt.edu.herofundbackend.dto.StatusAndIdsRequest;
import com.fpt.edu.herofundbackend.dto.channel.PaymentChannelAdminDto;
import com.fpt.edu.herofundbackend.dto.payment.paypal.request.PaypalPaymentRequest;
import com.fpt.edu.herofundbackend.entity.PaymentChannel;
import com.fpt.edu.herofundbackend.service.PaymentChannelService;
import com.fpt.edu.herofundbackend.util.MyCollectionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@Validated
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/admin/channels")
public class PaymentChannelAdminController {

    private final PaymentChannelService paymentChannelService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CommonResponse> save(@RequestBody @Valid PaypalPaymentRequest request) {
        CommonResponse response = CommonResponse.builder()
                .status(false)
                .message(SystemConstant.Message.SAVE_FAIL)
                .build();
        PaymentChannel paymentChannel = paymentChannelService.save(request);
        if (null == paymentChannel) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessWithDataResponse(paymentChannel, SystemConstant.Message.SAVE_SUCCESS);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<CommonResponse> update(@RequestBody @Valid PaypalPaymentRequest request) {
        CommonResponse response = CommonResponse.builder()
                .status(false)
                .message(SystemConstant.Message.UPDATE_FAIL)
                .build();
        PaymentChannel paymentChannel = paymentChannelService.update(request);
        if (null == paymentChannel) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessWithDataResponse(paymentChannel, SystemConstant.Message.UPDATE_SUCCESS);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<PaymentChannelAdminDto>> getAll() {
        return ResponseEntity.ok(paymentChannelService.getAll().stream()
                .map(PaymentChannelAdminDto::new)
                .collect(Collectors.toList()));
    }

    @RequestMapping(path = "detail", method = RequestMethod.GET)
    public ResponseEntity<PaymentChannelAdminDto> getDetail(@RequestParam(name = "id") long id) {
        return ResponseEntity.ok(paymentChannelService.getDetail(id));
    }

    @RequestMapping(path = "update/status", method = RequestMethod.PUT)
    public ResponseEntity<CommonResponse> updateStatus(
            @RequestParam(name = "id") long id,
            @Min(value = 0, message = SystemConstant.Message.STATUS_VALIDATION)
            @Max(value = 1, message = SystemConstant.Message.STATUS_VALIDATION)
            @Param("status") int status) {
        CommonResponse response = new CommonResponse(false, SystemConstant.Message.UPDATE_FAIL);

        PaymentChannel channel = paymentChannelService.updateStatusById(id, status);
        if (channel == null) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessWithDataResponse(channel, SystemConstant.Message.UPDATE_SUCCESS);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(path = "update-multiple/status", method = RequestMethod.PUT)
    public ResponseEntity<CommonResponse> updateStatusChannels(@RequestBody @Valid StatusAndIdsRequest request) {
        CommonResponse response = new CommonResponse(false, SystemConstant.Message.UPDATE_FAIL);
        List<PaymentChannel> channel = paymentChannelService.updateStatusChannels(request.getIds(), request.getStatus());
        if (MyCollectionUtils.listIsNullOrEmpty(channel)) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessWithDataResponse(channel, SystemConstant.Message.UPDATE_SUCCESS);
        return ResponseEntity.ok(response);
    }

}
