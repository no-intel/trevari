package com.trevari.test.domain.book.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.trevari.test.domain.book.dto.Projection.BooksResponseDto;
import com.trevari.test.domain.book.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.trevari.test.domain.book.entity.QBook.book;

@Repository
@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements  BookRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BooksResponseDto> findBooks(Pageable pageable) {
        List<BooksResponseDto> content = queryFactory
                .select(Projections.constructor(BooksResponseDto.class,
                        book.isbn,
                        book.title,
                        book.subtitle,
                        book.image,
                        book.author,
                        book.isbn,
                        book.publishDate
                ))
                .from(book)
                .orderBy(getOrderSpecifiers(pageable.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> total = queryFactory
                .select(book.count())
                .from(book);

        return PageableExecutionUtils.getPage(content, pageable, total::fetchOne);
    }

    ;

    private OrderSpecifier<?>[] getOrderSpecifiers(Sort sort) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();
        for (Sort.Order order : sort) {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            PathBuilder<Book> pathBuilder = new PathBuilder<>(book.getType(), book.getMetadata());
            switch (order.getProperty()) {
                case "title" -> orders.add(new OrderSpecifier<>(direction, book.title));
                case "subtitle" -> orders.add(new OrderSpecifier<>(direction, book.subtitle));
                case "author" -> orders.add(new OrderSpecifier<>(direction, book.author));
                case "publisher" -> orders.add(new OrderSpecifier<>(direction, book.publisher));
                case "publishDate" -> orders.add(new OrderSpecifier<>(direction, book.publishDate));
                case "createdAt" -> orders.add(new OrderSpecifier<>(direction, book.createdAt));
                default -> orders.add(new OrderSpecifier<>(Order.DESC, book.createdAt));
            }

        }
        return orders.toArray(new OrderSpecifier[0]);
    }
//    // 참고: 단건 조회 예시 (이미 작성한 메서드 개선본)
//    public Book findBookByIsbn(Long isbn) {
//        QBook b = QBook.book;
//        return queryFactory.selectFrom(b)
//                .where(b.isbn.eq(isbn))
//                .fetchOne();
//    }
}