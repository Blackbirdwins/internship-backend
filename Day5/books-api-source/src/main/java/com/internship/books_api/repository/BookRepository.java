package com.internship.books_api.repository;

import com.internship.books_api.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthor(String author);
    List<Book> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT b FROM Book b WHERE b.price > :minPrice")
    List<Book> findBooksAbovePrice(@Param("minPrice") Double minPrice);

    @Query("SELECT b FROM Book b WHERE b.publisher.name = :publisherName")
    List<Book> findBooksByPublisherName(@Param("publisherName") String publisherName);

    @Query("SELECT b FROM Book b WHERE LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Book> searchByAuthorOrTitle(@Param("keyword") String keyword);
}