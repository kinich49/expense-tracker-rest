package mx.kinich49.expensetracker.base;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;
import lombok.Data;

@ResponseBody
@SuppressWarnings("unused")
@Data
@AllArgsConstructor
public class RestResponse<T> {

    @Nullable
    private T data;
    private boolean isSuccess;
    @Nullable
    private RestError restError;
    
}
