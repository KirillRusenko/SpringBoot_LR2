package org.example.lr02_spring.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.lr02_spring.exception.UnsupportedCodeException;
import org.example.lr02_spring.exception.ValidationFailedException;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    @NotBlank()
    @NotNull()
    private String uid;

    @NotBlank()
    private String operationUid;

    private String systemName;

    @NotBlank()
    private String systemTime;

    private String source;

    @Min(value = 1)
    @Max(value = 100000)
    private int communicationId;

    private int templateId;

    private String productCode;

    private int smsCode;

    public void validateRequest() throws ValidationFailedException, UnsupportedCodeException {
        validateUid();
        validateOperationUid();
    }

    private void validateUid() throws ValidationFailedException, UnsupportedCodeException {
        if (uid.length() > 32) {
            throw new ValidationFailedException("uid не может быть длиннее 32 символов");
        }
        if (uid.equals("123")) {
            throw new UnsupportedCodeException("uid не может быть равен 123");
        }
    }

    private void validateOperationUid() throws ValidationFailedException {
        if (operationUid.length() > 32) {
            throw new ValidationFailedException("operationUid не может быть длиннее 32 символов");
        }
    }
}