package br.com.nepomuceno.infrastructure.mapper;

import br.com.nepomuceno.domain.Payer;
import br.com.nepomuceno.domain.Payload;
import br.com.nepomuceno.infrastructure.dto.PayerDTO;
import br.com.nepomuceno.infrastructure.dto.request.PayloadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PaymentMapper {
    @Autowired
    public PayerMapper payerMapper;
    @Autowired
    public VoucherMapper voucherMapper;
    @Autowired
    public ServiceTaxMapper serviceTaxMapper;
    @Autowired
    public DeliveryTaxMapper deliveryTaxMapper;

    public Payload toPayload(PayloadRequest request) {

        List<Payer> payers = new ArrayList<>();
        for(PayerDTO payerDTO : request.getPayersDTO()) {
            Payer payer = payerMapper.toPayer(payerDTO);
            payers.add(payer);
        }

        return new Payload(
                payers,
                serviceTaxMapper.toServiceTax(request.getServiceTaxDTO()),
                deliveryTaxMapper.toDelivery(request.getDeliveryTaxDTO()),
                voucherMapper.toVoucher(request.getVoucherDTO()),
                request.getTotalAmount()
        );
    }
}
