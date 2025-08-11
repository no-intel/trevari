package com.trevari.test.domain.search.util;

import com.trevari.test.domain.search.enums.SearchStrategyEnum;
import com.trevari.test.domain.search.exception.InvalidSearchPatternException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.regex.Pattern;

@Component
public class BookSearchParsingUtil {

    private static final String OR ="|";
    private static final String NOT ="-";


    public SearchStrategyEnum parsingAndCheck(String query) {
        if (ObjectUtils.isEmpty(query)) {
            return SearchStrategyEnum.KEYWORD;
        }

        boolean hasOr = query.contains(OR);
        boolean hasNot = query.contains(NOT);

        if (hasOr && hasNot) {
            throw new InvalidSearchPatternException();
        }
        if (hasOr) {
            checkOrStrategy(query);
            return SearchStrategyEnum.OR_OPERATION;
        }
        if (hasNot) {
            checkNotStrategy(query);
            return SearchStrategyEnum.NOT_OPERATION;
        }

        return SearchStrategyEnum.KEYWORD;
    }

    private void checkOrStrategy(String query) {
        String[] parts = query.split(Pattern.quote(OR));
        if (parts.length != 2 || parts[0].isBlank() || parts[1].isBlank()) {
            throw new InvalidSearchPatternException();
        }
    }

    private void checkNotStrategy(String query) {
        String[] parts = query.split(Pattern.quote(NOT));
        if (parts.length != 2 || parts[0].isBlank() || parts[1].isBlank()) {
            throw new InvalidSearchPatternException();
        }
    }
}
