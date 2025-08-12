package com.trevari.test.domain.search.applicateion;

import com.trevari.test.domain.search.adapter.out.redis.PopularKeywordRecorder;
import com.trevari.test.domain.search.adapter.out.redis.event.PopularKeywordEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import static com.trevari.test.domain.search.util.PopularKeywordSupport.normalize;

@Service
@RequiredArgsConstructor
public class PopularKeywordRegisterService {
    private final PopularKeywordRecorder popularKeywordRecorder;

    @EventListener(PopularKeywordEvent.class)
    public void registerKeyword(PopularKeywordEvent event) {
        popularKeywordRecorder.record(normalize(event.Keyword()));
    }
}
