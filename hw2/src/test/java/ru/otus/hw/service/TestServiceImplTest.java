package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TestServiceImplTest {

    private IOService ioService;
    private QuestionDao questionDao;
    private TestServiceImpl testService;

    @BeforeEach
    void setUp() {
        ioService = mock(IOService.class);
        questionDao = mock(QuestionDao.class);
        testService = new TestServiceImpl(ioService, questionDao);
    }

    @Test
    void executeTestFor() {
        Student student = new Student("John", "Doe");

        Question question1 = new Question("What is 2 + 2?", Arrays.asList(new Answer("3", false), new Answer("4", true)));
        Question question2 = new Question("What is the capital of Grate Britain?", Arrays.asList(new Answer("Berlin", false), new Answer("London", true)));

        when(questionDao.findAll()).thenReturn(Arrays.asList(question1, question2));
        when(ioService.readIntForRangeWithPrompt(anyInt(), anyInt(), anyString(), anyString())).thenReturn(1, 0);

        TestResult result = testService.executeTestFor(student);

        verify(ioService, times(6)).printFormattedLine(anyString(), anyInt(), anyString());
        verify(ioService, times(6)).printFormattedLine(anyString(), anyInt(), anyString());
        verify(ioService, times(2)).readIntForRangeWithPrompt(anyInt(), anyInt(), anyString(), anyString());

        assertThat(result.getRightAnswersCount()).isEqualTo(1);
    }

}