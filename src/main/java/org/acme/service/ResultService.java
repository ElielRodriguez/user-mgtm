package org.acme.service;

import org.acme.dto.ResultRequest;
import org.acme.dto.results.Result;

public interface ResultService<T> {
    Result<T> createResultResponse(ResultRequest<T> resultRequest);
}
