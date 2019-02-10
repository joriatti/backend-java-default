package com.backend.api.v1.interceptor;

import com.backend.exceptions.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BuilderErrorResponseEntity {

    private static final Logger LOG = LoggerFactory.getLogger(BuilderErrorResponseEntity.class);

    private static final String INTERNAL_SERVER_ERROR_CODE = "BACKEND_GENERIC_ERROR_MESSAGE";

    @Autowired
    private TranslatorService translatorService;

    public ResponseEntity<ErrorResponse> build(Exception ex) {
        try {
            String code;
            int status;
            String message;
            Object[] args;
            List<ErrorResponse> details = new ArrayList<>();

            if (ex instanceof BaseException) {
                if (ex.getCause() != null && ex.getCause() instanceof ErrorResponseContentException) {
                    ErrorResponseContentException cause = (ErrorResponseContentException) ex.getCause();
                    code = cause.getCode();
                    status = cause.getStatus();
                    message = cause.getMessage();
                } else {
                    code = ((BaseException) ex).getCode();
                    status = ((BaseException) ex).getStatus();
                    args = ((BaseException) ex).getParameters();

                    MessageDetailInfo errorInfo = getMessageDetailInfo(code, args);
                    message = errorInfo.getMessage();

                    for(BaseException error : ((BaseException) ex).getErrors()) {
                        MessageDetailInfo errorInfoDetail = getMessageDetailInfo(error.getCode(), error.getParameters());
                        details.add(ErrorResponse.builder(
                                error.getCode(),
                                errorInfoDetail.getMessage())
                                .build());
                    }
                }
            } else {
                code = INTERNAL_SERVER_ERROR_CODE;
                status = HttpStatus.INTERNAL_SERVER_ERROR.value();
                message = (ex.getLocalizedMessage() != null) ? ex.getLocalizedMessage() : "";
            }

            ErrorResponse errorResponse = ErrorResponse.builder(code, message).details(details).build();

            LOG.error(message, ex);

            return ResponseEntity.status(status).body(errorResponse);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            ErrorResponse errorResponse = ErrorResponse
                    .builder(INTERNAL_SERVER_ERROR_CODE, e.getLocalizedMessage()).build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    private MessageDetailInfo getMessageDetailInfo(String code, Object[] args) {
        return translatorService.translateError(code, args);
    }
}