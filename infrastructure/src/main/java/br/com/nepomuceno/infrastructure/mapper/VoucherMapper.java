package br.com.nepomuceno.infrastructure.mapper;

import br.com.nepomuceno.domain.Voucher;
import br.com.nepomuceno.infrastructure.dto.VoucherDTO;
import org.springframework.stereotype.Component;

@Component
public class VoucherMapper {

    public Voucher toVoucher(VoucherDTO voucherDTO) {
        return new Voucher(voucherDTO.getTaxTypeEnum(), voucherDTO.getAmount());
    }

}
