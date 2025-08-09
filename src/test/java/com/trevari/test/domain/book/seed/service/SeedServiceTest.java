package com.trevari.test.domain.book.seed.service;

import com.trevari.test.domain.book.repository.BookRepository;
import com.trevari.test.domain.book.seed.dto.SeedDetailResponse;
import com.trevari.test.domain.book.seed.dto.SeedSearchResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SeedServiceTest {

    @InjectMocks
    private SeedService seedService;

    @Mock
    private SeedHttpClient client;

    @Mock
    private BookRepository bookRepository;

    private SeedSearchResponse response;
    private SeedDetailResponse responseDetail;

    @BeforeEach
    void setUp() {
        response = new SeedSearchResponse(
                "0", "1", "1",
                List.of(new SeedSearchResponse.SeedListItem(
                        "SQL and Relational Theory",
                        "How to Write Accurate SQL Code",
                        "9780596523060",
                        "$3.50",
                        "https://itbook.store/img/books/9780596523060.png",
                        "https://itbook.store/books/9780596523060"
                ))
        );

        responseDetail = new SeedDetailResponse(
                "SQL and Relational Theory",
                "How to Write Accurate SQL Code",
                "C. J. Date",
                "O'Reilly Media",
                "9780596523060",
                "428",
                "2009",
                "$3.50",
                "https://itbook.store/img/books/9780596523060.png"
        );

    }

    @Test
    void setSeed() {
        final String KW = "mongoDB";

        when(client.search(eq(KW), eq(1))).thenReturn(response);
        when(client.search(eq(KW), eq(2))).thenReturn(null);
        when(client.search(argThat(q -> !KW.equals(q)), anyInt())).thenReturn(null);

        when(client.get("9780596523060")).thenReturn(responseDetail);
        when(bookRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        seedService.setSeeds();

        verify(bookRepository, times(1)).save(any());
        verify(client, times(1)).get("9780596523060");
    }
}