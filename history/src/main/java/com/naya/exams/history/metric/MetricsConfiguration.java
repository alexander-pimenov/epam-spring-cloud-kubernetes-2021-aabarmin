package com.naya.exams.history.metric;

import com.naya.exams.history.services.HistoryExerciseService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Aleksandr Barmin
 */
@Configuration
public class MetricsConfiguration {
    @Bean
    public MeterBinder availableHistoryQuestionsMetric(HistoryExerciseService service) {
        return registry ->
                Gauge.builder("questions.max.available", service::countAllRecords)
                     .baseUnit("NUMBER")
                     .description("Maximal value available in the history questions")
                     .register(registry);
    }
}
