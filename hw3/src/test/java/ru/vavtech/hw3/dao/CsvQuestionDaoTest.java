package ru.vavtech.hw3.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.vavtech.hw3.config.AppProperties;
import ru.vavtech.hw3.domain.Answer;
import ru.vavtech.hw3.domain.Question;
import ru.vavtech.hw3.exceptions.QuestionReadException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class CsvQuestionDaoTest {

    @Mock
    private AppProperties fileNameProvider;

    @InjectMocks
    private CsvQuestionDao csvQuestionDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        when(fileNameProvider.getTestFileName()).thenReturn("questions_en.csv");

        List<Question> questions = csvQuestionDao.findAll();

        assertNotNull(questions);
        assertEquals(2, questions.size());

        Question question1 = questions.get(0);
        assertEquals("Question 1", question1.text());
        assertEquals(List.of(new Answer("Answer 1", false), new Answer("Answer 2", true)), question1.answers());

        Question question2 = questions.get(1);
        assertEquals("Question 2", question2.text());
        assertEquals(List.of(new Answer("Answer 3", false), new Answer("Answer 4", true)), question2.answers());
    }

    @Test
    void testFindAllWithException() {
        String testFileName = "dummy.csv";
        when(fileNameProvider.getTestFileName()).thenReturn(testFileName);
        Assertions.assertThrows(QuestionReadException.class, () -> csvQuestionDao.findAll());
    }

}