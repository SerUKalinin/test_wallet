package com.example.wallet.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Глобальный обработчик исключений.
 * Обрабатывает все исключения, возникающие в приложении,
 * и преобразует их в соответствующие HTTP-ответы с понятными сообщениями об ошибках.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обрабатывает исключение, возникающее при попытке выполнить операцию с несуществующим кошельком.
     *
     * @param ex исключение WalletNotFoundException
     * @return ResponseEntity с HTTP-статусом 404 и сообщением об ошибке
     */
    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleWalletNotFoundException(WalletNotFoundException ex) {
        log.error("Кошелек не найден: {}", ex.getMessage());
        return createErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    /**
     * Обрабатывает исключение, возникающее при попытке снять сумму, превышающую баланс кошелька.
     *
     * @param ex исключение InsufficientFundsException
     * @return ResponseEntity с HTTP-статусом 400 и сообщением об ошибке
     */
    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<Map<String, String>> handleInsufficientFundsException(InsufficientFundsException ex) {
        log.error("Недостаточно средств: {}", ex.getMessage());
        return createErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    /**
     * Обрабатывает исключения валидации аргументов методов контроллера.
     *
     * @param ex исключение MethodArgumentNotValidException
     * @return ResponseEntity с HTTP-статусом 400 и списком ошибок валидации
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage())
        );
        log.error("Ошибка валидации: {}", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    /**
     * Обрабатывает исключения нарушения ограничений валидации.
     *
     * @param ex исключение ConstraintViolationException
     * @return ResponseEntity с HTTP-статусом 400 и списком ошибок валидации
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> 
            errors.put(violation.getPropertyPath().toString(), violation.getMessage())
        );
        log.error("Ошибка валидации: {}", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    /**
     * Обрабатывает все необработанные исключения.
     *
     * @param ex исключение Exception
     * @return ResponseEntity с HTTP-статусом 500 и общим сообщением об ошибке
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        log.error("Неожиданная ошибка: ", ex);
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Произошла внутренняя ошибка сервера");
    }

    /**
     * Создает ответ с ошибкой.
     *
     * @param status HTTP-статус ответа
     * @param message сообщение об ошибке
     * @return ResponseEntity с указанным статусом и сообщением об ошибке
     */
    private ResponseEntity<Map<String, String>> createErrorResponse(HttpStatus status, String message) {
        Map<String, String> error = new HashMap<>();
        error.put("error", message);
        return ResponseEntity.status(status).body(error);
    }
} 