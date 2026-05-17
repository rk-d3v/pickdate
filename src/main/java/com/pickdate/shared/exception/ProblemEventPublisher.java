package com.pickdate.shared.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "pickdate.observability.problem.enabled", havingValue = "true")
public class ProblemEventPublisher {

    private final ApplicationEventPublisher applicationEvents;

    public void publish(ProblemCapturedEvent event) {
        applicationEvents.publishEvent(event);
    }
}
