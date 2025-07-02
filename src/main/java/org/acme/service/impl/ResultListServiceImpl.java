package org.acme.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.dto.ResultRequest;
import org.acme.dto.UserRequestResponse;
import org.acme.dto.results.Result;
import org.acme.dto.results.ResultStatus;
import org.acme.service.ResultService;
import java.util.List;

@ApplicationScoped
public class ResultListServiceImpl implements ResultService<List<UserRequestResponse>> {
    /**
     * @param resultRequest
     * @return a result object response
     * @see Result<UserRequestResponse>
     * @see ResultRequest<UserRequestResponse>
     */
    @Override
    public Result<List<UserRequestResponse>> createResultResponse(ResultRequest<List<UserRequestResponse>> resultRequest) {
        ResultStatus status = new ResultStatus();
        status.setCode(resultRequest.getCode());
        status.setMessage(resultRequest.getMessage());
        status.setPage(resultRequest.getPage());
        status.setPages(resultRequest.getPages());
        status.setSize(resultRequest.getSize());

        Result<List<UserRequestResponse>> result = new Result<>();
        result.setStatus(status);
        result.setData(resultRequest.getData());
        result.setErrors(resultRequest.getErrors());
        return result;
    }
}
