package server.domain.dto.user.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
public class ErrorMsgResponse {

    @Builder
    public ErrorMsgResponse(int statusCode, Date timestmap, String message, String description) {
        this.statusCode = statusCode;
        this.timestmap = timestmap;
        this.message = message;
        this.description = description;
    }

    private int statusCode;
    private Date timestmap;
    private String message;
    private String description;

}
