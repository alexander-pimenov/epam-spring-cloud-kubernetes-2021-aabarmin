package com.naya.exams.examinator.controllers;

import com.naya.exams.examinator.model.Exam;
import com.naya.exams.examinator.model.Exercise;
import com.naya.exams.examinator.model.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @author Evgeny Borisov
 */
@RestController
@RequestMapping("/exams")
public class ExamComposerController {
    private final RestTemplate restTemplate;

    //что бы понять как это работает заинжектим DiscoveryClient
    //этот бин приходит в подарок от EurekaClient
    //с его помощью можно получить много информации
    //но им мы не пользуемся))))
    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${exam.title}")
    private String title;

    private int number = 1;

    @Autowired
    public ExamComposerController(@LoadBalanced RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/exam")
    public Exam createExam(@RequestBody Map<String, Integer> examSpec) {
        /*есть пары и против каждой пары нужно получить url*/
        List<Section> sections = examSpec.entrySet().stream().map(entry -> {
            String title = entry.getKey();
            String url = getServiceUrl(title, entry.getValue());
            //с помощью restTemplate обращаемся по url и получаем данные
            Exercise[] exercises = restTemplate.getForObject(url, Exercise[].class); //на выходе получили массив, который дальше конвертнем в список
            return Section.builder().exercises(Arrays.asList(exercises)).title(title).build();
        }).collect(toList());

        //или так:
        //List<Section> sections = spec.entrySet().stream()
        //                .map(entry -> restTemplate.getForObject(getUrl(entry), Question[].class))
        //                .map(questions -> Section.builder().questions(List.of(questions)).build())
        //                .collect(Collectors.toList());

        return Exam.builder().sections(sections).title(title + " #" + number++).build();
    }

    public String getServiceUrl(final String service, final int amount) {
        return "http://" + service + "/exercise/random?amount=" + amount;
    }

    /**
     * список всех инстансов для сервиса "dossier-client"
     *
     * @return
     */
    @GetMapping("/list")
    public List<String> instancesList() {
        return this.discoveryClient.getInstances("client")
                                   .stream().map(t -> t.getUri().toString() + "\n").collect(Collectors.toList());
    }

    /**
     * Запрос данных от инстанса
     *
     * @return
     */
    @GetMapping("/getData")
    public String serviceGetData() {
        RestTemplate restTemplate = new RestTemplate();
        String url = this.discoveryClient.getInstances("client").get(0).getUri().toString()
                + "client/sources";
        System.out.println(url);
        String val = restTemplate.getForObject(url, String.class);
        return "Источник: " + url + " данные: " + val;
    }

    /**
     * Съедаем процессор
     *
     * @return
     */
    @GetMapping("/kill")
    public String kill() {
        Double a = 0D;
        for (int i = 1; i < 100000000; i++) {
            a += (Math.cos(i) + Math.sin(i) + Math.random());
        }
        return "Done: " + a;
    }




}
