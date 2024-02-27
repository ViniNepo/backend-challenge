package br.com.nepomuceno.usecase;

import br.com.nepomuceno.domain.Fee;
import br.com.nepomuceno.domain.Payer;
import br.com.nepomuceno.domain.Voucher;

import java.util.List;

public interface CreatePaymentUseCase {

    void process(List<Payer> payers, List<Fee> fees, Voucher voucher, Double totalAmount);
}
