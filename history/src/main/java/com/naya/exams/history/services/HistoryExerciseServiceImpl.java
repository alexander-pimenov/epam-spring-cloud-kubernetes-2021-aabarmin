package com.naya.exams.history.services;

import com.naya.exams.history.dao.ExerciseDao;
import com.naya.exams.history.model.Exercise;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Evgeny Borisov
 */
@Service
@Transactional
public class HistoryExerciseServiceImpl implements HistoryExerciseService {
    @Autowired
    @Setter
    private ExerciseDao dao;

    @Setter
    @Getter
    private int version;


    @Override
    public List<Exercise> getRandom(int amount) {
        //достаем из БД все вопросы и вернем в контролер указанное amount количество
        List<Exercise> all = dao.findAll();
        /*Если нужно доставать из БД не все, а только некоторые, то можно написать запрос к БД в репозитории
         * и использовать этот метод*/
        Collections.shuffle(all);
        return all.subList(0,amount); //возвращает несколько элементов: от 0 до amount
    }

    @Override
    @EventListener(ContextRefreshedEvent.class)
    public void fillDB() {
        System.out.println("Заполнение БД некоторыми данными (происходит перед стартом программы)");
        List<Exercise> exercises = Arrays.asList(
                Exercise.builder().question("How old is Java?").answer("27").build(),
                Exercise.builder().question("How old is Groovy?").answer("16").build(),
                Exercise.builder().question("How old is JS?").answer("26").build()
                                                );
        dao.saveAll(exercises);
    }

    @Override
    public long countAllRecords() {
        return dao.count();
    }
}












