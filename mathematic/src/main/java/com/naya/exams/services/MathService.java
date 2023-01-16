package com.naya.exams.services;

import com.naya.exams.model.Exercise;

/**
 * @author Evgeny Borisov
 */
public interface MathService {
    Exercise getRandomExercise();

    /**
     * Получите максимально доступное значение в упражнении.
     * @return
     */
    int getMaxAvailableNumber();
}
