package com.fpt.edu.herofundbackend.entity;

import com.fpt.edu.herofundbackend.dto.article.ArticleRequest;
import com.fpt.edu.herofundbackend.dto.comment.CommentRequest;
import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "comments")
@Builder
public class Comment extends BaseEntity{

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "account_id")
    private long accountId;

    @Column(name = "article_id")
    private long articleId;

    @Formula("(select count(l.id) from likes l where l.item_id = id and l.type_id = 2)")
    private long numberOfLike;

    @Formula("(select a.username from accounts a where a.id = account_id)")
    private String username;

    @Formula("(select at.title from articles at where at.id = article_id)")
    private String articleTitle;

    public void update(CommentRequest request) {
        this.content = request.getContent();
        this.articleId = request.getArticleId();
    }
}
