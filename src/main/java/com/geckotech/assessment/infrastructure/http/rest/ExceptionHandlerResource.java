//package com.geckotech.assessment.infrastructure.http.rest;
//
//import com.geckotech.assessment.application.service.RecipeService;
//import com.geckotech.assessment.domain.repository.exception.SaveFailed;
//import com.geckotech.assessment.infrastructure.http.model.ErrorMessage;
//import com.geckotech.assessment.infrastructure.http.payload.RecipePayload;
//import com.geckotech.assessment.infrastructure.http.responseDto.Recipe;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.media.ArraySchema;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@ControllerAdvice
//public class ExceptionHandlerResource {
////    todo public ExceptionHandlerResource(logger)
////    todo private logger = LoggerFactory.getLogger(SyncProductService::class.java)
////    todo final Enum<ExceptionMapping> mapping
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> handleException(Exception e) {
//    //        todo logger.error("Error occurred", e);
//    //        mapping = ExceptionMapping.findByExceptionClass(e::class.java)
//
//        return ResponseEntity
////            .status(mapping.httpStatus)
//            .status(HttpStatus.BAD_REQUEST)
//            .body("Unexpected Exception: " + e.getMessage());
//    }
//
//    @ExceptionHandler(Throwable.class)
//    public ResponseEntity<?> handleException(Throwable e) {
////        todo logger.error("Error occurred", e);
////        mapping = ExceptionMapping.findByExceptionClass(e::class.java)
//
//        return ResponseEntity
////            .status(mapping.httpStatus)
//            .status(HttpStatus.BAD_REQUEST)
//            .body("Unexpected Error: " + e.getMessage());
//    }
//
////    todo ? private ResponseEntity<ErrorResponseDto> buildErrorResponse(RestApiErrorCode errorCode, List<String> errorMessages, HttpStatus httpStatus) {
////        HttpHeaders headers = new HttpHeaders();
////        headers.setContentType(APPLICATION_JSON);
////
////        ErrorResponseDto error = new ErrorResponseDto(errorCode, errorMessages);
////        return new ResponseEntity<>(error, headers, httpStatus);
////    }
//}
