package br.com.nepomuceno.infrastructure.mapper;

import br.com.nepomuceno.domain.Payer;
import br.com.nepomuceno.infrastructure.dto.PayerDTO;
import org.springframework.stereotype.Component;

@Component
public class PayerMapper {

    public Payer toPayer(PayerDTO payerDTO) {
        return new Payer(payerDTO.getPaymentMethod(), payerDTO.getAmount());
    }
}
