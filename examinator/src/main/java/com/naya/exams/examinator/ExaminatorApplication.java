package com.naya.exams.examinator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class ExaminatorApplication {

    @Value("${exam.title}")
    private String title;

    @Scheduled(fixedDelay = 10000)
    public void printTitle() {
        System.out.println("title = " + title);
    }

    //бин ресттемплейта
    @Bean
    @Primary
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /* бин ресттемплейта - RestTemplate
     * чтобы не сбились настройки прописанные в проперти, нужно делать бин
     * через RestTemplateBuilder, т.к. он есть в стартере spring-boot-starter-web
     * и настройки из пропертей попадают в этот билдер.
     *
     * @LoadBalanced добавляет в RestTemplate интерсептор (перехватчик) и этот интерсептор занет
     * что при построении url в методе getServiceUrl(...) нужно создать физический адрес
     * Также @LoadBalanced делает балансировку, т.е. если запущено несколько инстансов микросервиса,
     * то интервептор будет пихать по алгоритму Round Robbin в url адрес и порт следующую в порядке
     * очереди копию микросервиса
     *
     * Все микросервисы у нас общаются через RestTemplate, но никто не знает ни прокакой физический адрес,
     * они сроятся динамически.*/
    @Bean
    @LoadBalanced //приходит из EurekaClient
    public RestTemplate restTemplateWithZul(RestTemplateBuilder builder) {
        return builder.build();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ExaminatorApplication.class);
        System.out.println();
    }
}
