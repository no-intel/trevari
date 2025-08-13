package com.trevari.test.domain.search.adapter.out.book;

import com.trevari.test.domain.book.port.in.BookFinderUseCase;
import com.trevari.test.domain.search.enums.SearchStrategyEnum;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;

@Component
public class BookSearchRouter {
    private final EnumMap<SearchStrategyEnum, BookFinderUseCase> map;

    public BookSearchRouter(List<BookFinderUseCase> useCases) {
        this.map = new EnumMap<>(SearchStrategyEnum.class);
        useCases.forEach(useCase -> {
            this.map.put(SearchStrategyEnum.valueOf(useCase.getType()), useCase);
        });
    }

    public BookFinderUseCase get(SearchStrategyEnum searchStrategy) {
        return this.map.get(searchStrategy);
    }
}
