package com.trevari.test.domain.book.seed.service;

import com.trevari.test.domain.book.entity.Book;
import com.trevari.test.domain.book.repository.BookRepository;
import com.trevari.test.domain.book.seed.dto.SeedDetailResponse;
import com.trevari.test.domain.book.seed.dto.SeedSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeedService {
    private final SeedHttpClient client;
    private final BookRepository bookRepository;

    private static final String[] TARGETS = {"mongoDB", "javascript", "sql", "tdd", "docker"};
    private static final int PAGES_PER_KEYWORD = 5;

    /** 키워드 4개를 4개의 스레드에서 병렬 실행 */
    public void setSeeds() {
        try (ExecutorService pool = Executors.newFixedThreadPool(
                TARGETS.length,
                r -> {
                    Thread t = new Thread(r);
                    t.setName("seed-" + UUID.randomUUID());
                    t.setDaemon(false);
                    return t;
                }
        )) {

            List<Future<Integer>> futures = new ArrayList<>();
            for (String keyword : TARGETS) {
                futures.add(pool.submit(() -> seedOneKeyword(keyword)));
            }

            int totalSaved = 0;
            for (Future<Integer> f : futures) {
                try {
                    totalSaved += f.get(); // 각 스레드 결과 합산
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                } catch (ExecutionException ee) {
                    // 최소 로깅만
                    log.error("[seed error] {}", String.valueOf(ee.getCause()));
                }
            }
            log.info("== seed done: totalSaved={}", totalSaved);
        }
    }

    /** 한 키워드에 대해 /search/keyword/1..5 → 각 아이템 isbn13으로 상세 조회 & 저장 */
    private int seedOneKeyword(String keyword) {
        int saved = 0;
        Set<String> seen = new LinkedHashSet<>(); // 중복 방지(페이지 간)

        for (int page = 1; page <= PAGES_PER_KEYWORD; page++) {
            SeedSearchResponse res;
            try {
                res = client.search(keyword, page);
            } catch (Exception e) {
                log.error("[{}] search page {} failed: {}", keyword, page, e.getMessage());
                continue;
            }
            if (res == null || res.books() == null || res.books().isEmpty()) break;

            for (var item : res.books()) {
                String isbn = item.isbn13();
                if (!seen.add(isbn)) continue; // 이미 처리한 isbn은 스킵

                try {
                    SeedDetailResponse detailResponse = client.get(isbn);
                    Book book = toEntity(detailResponse);
                    bookRepository.save(book);
                    saved++;
                } catch (Exception e) {
                    log.error("[{}] detail {} skip: {}", keyword, isbn, e.getMessage());
                }
            }
        }
        log.info("{} [{}] saved={}", Thread.currentThread().getName(), keyword, saved);
        return saved;
    }

    private Book toEntity(SeedDetailResponse response) {
        return new Book(
                Long.parseLong(response.isbn13()),
                response.title(),
                response.subtitle(),
                response.authors(),
                response.publisher(),
                response.image(),
                Integer.parseInt(response.pages()),
                new BigDecimal(response.price().trim().replace("$", "")),
                LocalDate.of(Integer.parseInt(response.year()), 1, 1)
                        .plusDays(ThreadLocalRandom.current().nextInt(LocalDate.of(2018,1,1).lengthOfYear()))
        );
    }
}
