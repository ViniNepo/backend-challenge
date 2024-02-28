package br.com.nepomuceno.infrastructure.controller;

import br.com.nepomuceno.domain.*;
import br.com.nepomuceno.domain.enums.PaymentMethodEnum;
import br.com.nepomuceno.domain.enums.TaxTypeEnum;
import br.com.nepomuceno.exceptions.PaymentException;
import br.com.nepomuceno.infrastructure.controller.PaymentController;
import br.com.nepomuceno.infrastructure.dto.DeliveryTaxDTO;
import br.com.nepomuceno.infrastructure.dto.PayerDTO;
import br.com.nepomuceno.infrastructure.dto.ServiceTaxDTO;
import br.com.nepomuceno.infrastructure.dto.VoucherDTO;
import br.com.nepomuceno.infrastructure.dto.request.PayloadRequest;
import br.com.nepomuceno.infrastructure.mapper.PaymentMapper;
import br.com.nepomuceno.usecase.CreatePaymentLinkUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import static br.com.nepomuceno.domain.enums.ErrorCodeEnum.P002;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PaymentController.class)
@AutoConfigureMockMvc
public class PaymentControllerTest {

    static String PAYMENT_API = "/api/payment";

    @Autowired
    MockMvc mvc;

    @MockBean
    CreatePaymentLinkUseCase paymentLinkUseCase;

    @MockBean
    PaymentMapper paymentMapper;

    @Autowired
    ObjectMapper objectMapper;

    private PayloadRequest createRequest(
            List<PayerDTO> payersDTO,
            ServiceTaxDTO serviceTaxDTO,
            DeliveryTaxDTO deliveryTaxDTO,
            VoucherDTO voucherDTO,
            double totalAmount) {
        return new PayloadRequest(
                payersDTO, serviceTaxDTO, deliveryTaxDTO, voucherDTO, totalAmount);
    }

    private Payload createPayload(
            List<Payer> payers,
            ServiceTax serviceTax,
            DeliveryTax deliveryTax,
            Voucher voucher,
            double totalAmount) {
        return new Payload(
                payers, serviceTax, deliveryTax, voucher, totalAmount);
    }

    @Test
    @DisplayName("Success - example case")
    public void createSuccessExampleTest() throws Exception {
        List<PayerDTO> payersDTO = new ArrayList<>();
        payersDTO.add(new PayerDTO(PaymentMethodEnum.PAYPAL, 42d));
        payersDTO.add(new PayerDTO(PaymentMethodEnum.PAYPAL, 8d));

        PayloadRequest payloadRequest = createRequest(
                payersDTO,
                new ServiceTaxDTO(TaxTypeEnum.REAL, 0d),
                new DeliveryTaxDTO(TaxTypeEnum.REAL, 8d),
                new VoucherDTO(TaxTypeEnum.REAL, 0d),
                50d);

        List<String> links = new ArrayList<>();
        links.add("link1");
        links.add("link2");

        List<Payer> payers = new ArrayList<>();
        payers.add(new Payer(PaymentMethodEnum.PAYPAL, 42d));
        payers.add(new Payer(PaymentMethodEnum.PAYPAL, 8d));

        Payload payload = createPayload(
                payers,
                new ServiceTax(TaxTypeEnum.REAL, 0d),
                new DeliveryTax(TaxTypeEnum.REAL, 8d),
                new Voucher(TaxTypeEnum.REAL, 0d),
                50d);

        given(paymentLinkUseCase.createLink(paymentMapper.toPayload(payloadRequest))).willReturn(links);
        given(paymentMapper.toPayload(payloadRequest)).willReturn(payload);

        String json = objectMapper.writeValueAsString(payloadRequest);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(PAYMENT_API+"/create-link")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    @DisplayName("Success - one payer case")
    public void onePayerTest() throws Exception {
        List<PayerDTO> payersDTO = new ArrayList<>();
        payersDTO.add(new PayerDTO(PaymentMethodEnum.PAYPAL, 42d));

        PayloadRequest payloadRequest = createRequest(
                payersDTO,
                new ServiceTaxDTO(TaxTypeEnum.REAL, 0d),
                new DeliveryTaxDTO(TaxTypeEnum.REAL, 8d),
                new VoucherDTO(TaxTypeEnum.REAL, 0d),
                42d);

        List<String> links = new ArrayList<>();
        links.add("link1");

        List<Payer> payers = new ArrayList<>();
        payers.add(new Payer(PaymentMethodEnum.PAYPAL, 42d));

        Payload payload = createPayload(
                payers,
                new ServiceTax(TaxTypeEnum.REAL, 0d),
                new DeliveryTax(TaxTypeEnum.REAL, 8d),
                new Voucher(TaxTypeEnum.REAL, 0d),
                42d);

        given(paymentLinkUseCase.createLink(paymentMapper.toPayload(payloadRequest))).willReturn(links);
        given(paymentMapper.toPayload(payloadRequest)).willReturn(payload);

        String json = objectMapper.writeValueAsString(payloadRequest);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(PAYMENT_API+"/create-link")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", hasSize(1)));

    }

    @Test
    @DisplayName("Error - error case")
    public void errorCaseTest() throws Exception {
        List<PayerDTO> payersDTO = new ArrayList<>();
        payersDTO.add(new PayerDTO(PaymentMethodEnum.PAYPAL, 42d));

        PayloadRequest payloadRequest = createRequest(
                payersDTO,
                new ServiceTaxDTO(TaxTypeEnum.REAL, 0d),
                new DeliveryTaxDTO(TaxTypeEnum.REAL, 8d),
                new VoucherDTO(TaxTypeEnum.REAL, 0d),
                42d);

        List<Payer> payers = new ArrayList<>();
        payers.add(new Payer(PaymentMethodEnum.PAYPAL, 42d));

        Payload payload = createPayload(
                payers,
                new ServiceTax(TaxTypeEnum.REAL, 0d),
                new DeliveryTax(TaxTypeEnum.REAL, 8d),
                new Voucher(TaxTypeEnum.REAL, 0d),
                42d);

        given(paymentLinkUseCase.createLink(paymentMapper.toPayload(payloadRequest))).willThrow(new PaymentException("Invalid option " + "paypal", P002.getCode()));
        given(paymentMapper.toPayload(payloadRequest)).willReturn(payload);

        String json = objectMapper.writeValueAsString(payloadRequest);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(PAYMENT_API+"/create-link")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)));

    }

}
