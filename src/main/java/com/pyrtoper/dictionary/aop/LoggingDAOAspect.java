package com.pyrtoper.dictionary.aop;

import com.pyrtoper.dictionary.entity.MissingWord;
import com.pyrtoper.dictionary.entity.Translation;
import com.pyrtoper.dictionary.entity.Word;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Aspect
@Component
public class LoggingDAOAspect {

    private static final Logger logger = Logger.getLogger(LoggingDAOAspect.class.getName());


    @After("execution(* setWorkState(..))")
    public void afterChangingWorkState(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        logger.info("Polish To Russian work state of a bot was changed to " + args[0]);
    }

    @Before("com.pyrtoper.dictionary.aop.Pointcuts.forDAOPackage()")
    public void loggingDAOMethodArgs(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        logger.info(methodName + " is now executing with args: " + Arrays.stream(joinPoint.getArgs())
                .map((arg) -> arg.getClass().toString() + ": " + arg)
                .collect(Collectors.toList()));
    }

    @AfterReturning(pointcut = "execution(* com.pyrtoper.dictionary.dao.*.getSimilar*(*))",
    returning = "similarWords")
    public void loggingDAOSimilarWords(JoinPoint joinPoint, List<String> similarWords) {
        String methodName = joinPoint.getSignature().toShortString();
        logger.info("Method: " + methodName + " for word " + joinPoint.getArgs()[0]
                + " returned " + similarWords);
    }

    @AfterReturning(pointcut = "execution(* com.pyrtoper.dictionary.dao.WordDAO.getWordByName(*))",
    returning = "word")
    public void loggingDAOWords(JoinPoint joinPoint, Word word) {
        String methodName = joinPoint.getSignature().toShortString();
        logger.info("Method: " + methodName + " for word " + joinPoint.getArgs()[0]
        + " returned " + word.toString());
    }

    @AfterReturning(pointcut = "execution(* com.pyrtoper.dictionary.dao.TranslationDAO.getTranslationByName(String))",
            returning = "translation")
    public void loggingDAOTranslations(JoinPoint joinPoint, Translation translation) {
        String methodName = joinPoint.getSignature().toShortString();
        logger.info("Method: " + methodName + " for word " + joinPoint.getArgs()[0]
                + " returned " + translation.toString());
    }

    @AfterReturning(pointcut = "execution(* com.pyrtoper.dictionary.dao.MissingWordDAO.getMissingWordByName(*))",
    returning = "missingWord")
    public void missingWordIsAlreadyInDB(MissingWord missingWord) {
        logger.warning("Trying to add word " + missingWord.getWordName() + " is failed: word is already in DB");
    }
}
