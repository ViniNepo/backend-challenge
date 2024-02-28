package br.com.nepomuceno.infrastructure.mapper;

import br.com.nepomuceno.domain.ServiceTax;
import br.com.nepomuceno.infrastructure.dto.ServiceTaxDTO;
import org.springframework.stereotype.Component;

@Component
public class ServiceTaxMapper {

    public ServiceTax toServiceTax(ServiceTaxDTO serviceTaxDTO) {
        return new ServiceTax(serviceTaxDTO.getTaxTypeEnum(), serviceTaxDTO.getAmount());
    }

}
