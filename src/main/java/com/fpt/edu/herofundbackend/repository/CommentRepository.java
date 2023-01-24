package com.fpt.edu.herofundbackend.repository;

import com.fpt.edu.herofundbackend.dto.article.JpaFilterArticleRequest;
import com.fpt.edu.herofundbackend.dto.comment.JpaFilterCommentRequest;
import com.fpt.edu.herofundbackend.entity.Article;
import com.fpt.edu.herofundbackend.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c where c.id = :id and c.status = :status")
    Optional<Comment> getCommentByIdAndStatus(@Param("id") long id, @Param("status") int status);

    @Query("select c from Comment c where c.status = :status")
    List<Comment> getAllCommentByStatus(@Param("status") int status);

    @Query("select c from Comment c where c.status = :status and c.articleId = :articleId")
    Page<Comment> getPageCommentByStatusAndArticleId(@Param("articleId") long articleId,
                                                     @Param("status") int status, Pageable pageable);


    @Query("select count(c) from Comment c where c.status = :status")
    long countCommentByStatus(@Param("status") int status);

    @Query("select c from Comment c where c.status = :status")
    Page<Comment> getPageCommentByStatus(@Param("status") int status, Pageable pageable);

    @Query("select c from Comment c where c.id in (:ids)")
    List<Comment> getCommentByIds(@Param("ids") List<Long> ids);

    @Query(value = "select c from Comment c " +
            "WHERE ( :#{#filter.content} is null or c.content like %:#{#filter.content}% ) " +
            "AND ( :#{#filter.articleTitle} is null or c.articleTitle like %:#{#filter.articleTitle}% ) " +
            "AND ( :#{#filter.username} is null or c.username like %:#{#filter.username}% ) " +
            "AND ( :#{#filter.startDateCreateAt} is null or :#{#filter.startDateCreateAt} <= c.createdAt ) " +
            "AND ( :#{#filter.endDateCreateAt} is null or c.createdAt <= :#{#filter.endDateCreateAt} ) " +
            "AND ( :#{#filter.articleId} is null or :#{#filter.articleId} = c.articleId) " +
            "AND ( :#{#filter.accountId} is null or :#{#filter.accountId} = c.accountId) " +
            "AND ( :#{#filter.id} is null or :#{#filter.id} = c.id) " +
            "AND ( :#{#filter.status} is null or :#{#filter.status} = c.status) ")
    Page<Comment> findCommentPaging(@Param("filter") JpaFilterCommentRequest filter,
                                     Pageable pageable);
}
