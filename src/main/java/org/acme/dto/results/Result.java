package org.acme.dto.results;

import java.util.List;

public class Result<T> {
    private ResultStatus status;
    private T data;
    private List<String> errors;

    public ResultStatus getStatus() {
        return this.status;
    }

    public void setStatus(ResultStatus status) {
        this.status = status;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<String> getErrors() {
        return this.errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
