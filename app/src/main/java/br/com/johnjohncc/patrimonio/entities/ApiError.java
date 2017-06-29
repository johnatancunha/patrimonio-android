package br.com.johnjohncc.patrimonio.entities;

import java.util.List;
import java.util.Map;

/**
 * Created by WGL003 on 29/06/2017.
 */

public class ApiError {

    String message;
    Map<String, List<String>> errors;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, List<String>> errors) {
        this.errors = errors;
    }
}
