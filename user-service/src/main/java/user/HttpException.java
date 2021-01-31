package user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HttpException extends RuntimeException {

    private int httpStatus;
    private String errorMessage;
}
