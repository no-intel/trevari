
package com.trevari.test.domain.search.exception;

import com.trevari.test.exception.TrevariException;

public class NotMatchedSearchStrategyException extends TrevariException {
    public NotMatchedSearchStrategyException() {
        super(404, "searchStrategy", "존재 하지 않는 검색 타입");
    }
}
