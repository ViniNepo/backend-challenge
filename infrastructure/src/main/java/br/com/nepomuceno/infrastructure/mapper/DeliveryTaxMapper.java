package br.com.nepomuceno.infrastructure.mapper;

import br.com.nepomuceno.domain.DeliveryTax;
import br.com.nepomuceno.infrastructure.dto.DeliveryTaxDTO;
import org.springframework.stereotype.Component;

@Component
public class DeliveryTaxMapper {

    public DeliveryTax toDelivery(DeliveryTaxDTO deliveryTaxDTO) {
        return new DeliveryTax(deliveryTaxDTO.getTaxTypeEnum(), deliveryTaxDTO.getAmount());
    }

}
