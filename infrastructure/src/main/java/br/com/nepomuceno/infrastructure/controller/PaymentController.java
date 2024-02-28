package br.com.nepomuceno.infrastructure.controller;

import br.com.nepomuceno.infrastructure.dto.request.PayloadRequest;
import br.com.nepomuceno.infrastructure.mapper.PaymentMapper;
import br.com.nepomuceno.usecase.CreatePaymentLinkUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/payment")
public class PaymentController {

    @Autowired
    public PaymentMapper paymentMapper;

    private final CreatePaymentLinkUseCase createPaymentLinkUseCase;

    public PaymentController(CreatePaymentLinkUseCase createPaymentLinkUseCase) {
        this.createPaymentLinkUseCase = createPaymentLinkUseCase;
    }

    @PostMapping("/create-link")
    public ResponseEntity<List<String>> createPaymentLink(@RequestBody PayloadRequest request) {
        try {
            List<String> links = createPaymentLinkUseCase.createLink(paymentMapper.toPayload(request));
            System.out.println(links);
            return ResponseEntity.status(HttpStatus.CREATED).body(links);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonList(e.getMessage()));
        }
    }

}
