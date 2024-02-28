package br.com.nepomuceno.usecase;

import br.com.nepomuceno.domain.Payload;

import java.util.List;

public interface CreatePaymentLinkUseCase {

    List<String> createLink(Payload payload) throws Exception;
}
