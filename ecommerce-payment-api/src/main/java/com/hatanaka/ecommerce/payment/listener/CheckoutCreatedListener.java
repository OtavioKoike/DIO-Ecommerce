package com.hatanaka.ecommerce.payment.listener;

import com.hatanaka.ecommerce.checkout.event.CheckoutCreatedEvent;
import com.hatanaka.ecommerce.payment.entity.PaymentEntity;
import com.hatanaka.ecommerce.payment.event.PaymentCreatedEvent;
import com.hatanaka.ecommerce.payment.service.PaymentService;
import com.hatanaka.ecommerce.payment.streaming.CheckoutProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
//Quando subir a aplicação vai plugar no topico e ficar escutando
public class CheckoutCreatedListener {

    private final CheckoutProcessor checkoutProcessor;

    private final PaymentService paymentService;

//  Quem vai escutar
    @StreamListener(CheckoutProcessor.INPUT)
    public void handler(CheckoutCreatedEvent checkoutCreatedEvent) {
//      Processa pagamento
//      Salva os dados do pagamento
        log.info("checkoutCreatedEvent={}", checkoutCreatedEvent);
        final PaymentEntity paymentEntity = paymentService.create(checkoutCreatedEvent).orElseThrow();
        final PaymentCreatedEvent paymentCreatedEvent = PaymentCreatedEvent.newBuilder()
                .setCheckoutCode(paymentEntity.getCheckoutCode())
                .setPaymentCode(paymentEntity.getCode())
                .build();
        //      Enviar evendo do pagamento processado
        checkoutProcessor.output().send(MessageBuilder.withPayload(paymentCreatedEvent).build());
    }
}
