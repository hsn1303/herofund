package com.fpt.edu.herofundbackend.service;

import com.cloudinary.api.exceptions.BadRequest;
import com.fpt.edu.herofundbackend.constant.SystemConstant;
import com.fpt.edu.herofundbackend.dto.CommonResponse;
import com.fpt.edu.herofundbackend.dto.PageDto;
import com.fpt.edu.herofundbackend.dto.article.ArticleRequest;
import com.fpt.edu.herofundbackend.dto.article.FilterArticleRequest;
import com.fpt.edu.herofundbackend.dto.article.JpaFilterArticleRequest;
import com.fpt.edu.herofundbackend.dto.comment.CommentRequest;
import com.fpt.edu.herofundbackend.dto.comment.FilterCommentRequest;
import com.fpt.edu.herofundbackend.dto.comment.JpaFilterCommentRequest;
import com.fpt.edu.herofundbackend.entity.Account;
import com.fpt.edu.herofundbackend.entity.Article;
import com.fpt.edu.herofundbackend.entity.Comment;
import com.fpt.edu.herofundbackend.enums.ActionEnum;
import com.fpt.edu.herofundbackend.enums.BaseStatusEnum;
import com.fpt.edu.herofundbackend.exception.MyBadRequestException;
import com.fpt.edu.herofundbackend.exception.MyNotFoundException;
import com.fpt.edu.herofundbackend.repository.AccountRepository;
import com.fpt.edu.herofundbackend.repository.ArticleRepository;
import com.fpt.edu.herofundbackend.repository.CommentRepository;
import com.fpt.edu.herofundbackend.util.MapUtils;
import com.fpt.edu.herofundbackend.util.MyCollectionUtils;
import com.fpt.edu.herofundbackend.util.SystemUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.checkerframework.checker.units.qual.C;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final AccountRepository accountRepository;
    private final AuthenticateService authenticateService;


//    public CommonResponse saveOrUpdate(CommentRequest request, int action) {
//        CommonResponse response = new CommonResponse();
//        articleRepository.getArticleByIdAndStatus(request.getArticleId(), BaseStatusEnum.ENABLE.getKey())
//                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.ARTICLE_NOT_FOUND));
//        accountRepository.getAccountByIdAndStatus(request.getAccountId(), BaseStatusEnum.ENABLE.getKey())
//                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.ACCOUNT_NOT_FOUND));
//        if (action == ActionEnum.SAVE.getKey()) {
//            Comment newComment = MapUtils.fromRequestToComment(request);
//            newComment.setStatus(BaseStatusEnum.ENABLE.getKey());
//            response.setSuccessWithDataResponse(commentRepository.save(newComment));
//            return response;
//        }
//        Comment commentExist = commentRepository
//                .getCommentByIdAndStatus(request.getId(), BaseStatusEnum.ENABLE.getKey())
//                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.COMMENT_NOT_FOUND));
//        commentExist.setContent(request.getContent());
//        response.setSuccessWithDataResponse(commentRepository.save(commentExist));
//        return response;
//    }
    public Comment saveCommentAdmin(CommentRequest request, Account account) {
        articleRepository.findById(request.getArticleId())
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.ARTICLE_NOT_FOUND));
        try {
            Comment commentNew = MapUtils.fromRequestToComment(request);
            commentNew.setStatus(BaseStatusEnum.ENABLE.getKey());
            commentNew.setAccountId(account.getId());
            return commentRepository.save(commentNew);
        } catch (Exception e) {
            return null;
        }
    }
    //admin k cần check accountId của comment với id account
    public Comment updateCommentAdmin(CommentRequest request) {
        articleRepository.findById(request.getId())
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.ARTICLE_NOT_FOUND));
        Comment commentExist = commentRepository
                .findById(request.getId())
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.COMMENT_NOT_FOUND));
        try {
            commentExist.update(request);
            return commentRepository.save(commentExist);
        } catch (Exception e) {
            return null;
        }
    }
    //k thể update comment ng khác
    public Comment updateComment(CommentRequest request, Account account) {
        articleRepository.findById(request.getId())
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.ARTICLE_NOT_FOUND));
        Comment commentExist = commentRepository
                .findById(request.getId())
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.COMMENT_NOT_FOUND));
        if (commentExist.getAccountId() != account.getId()){
            throw new MyNotFoundException(SystemConstant.Message.COMMENT_NOT_FOUND);
        }
        try {
            commentExist.update(request);
            return commentRepository.save(commentExist);
        } catch (Exception e) {
            return null;
        }
    }

    public PageDto<Comment> getPageAdmin(int offset, int limit) {
        Pageable pageable = SystemUtils.createPageable(offset, limit);
        Page<Comment> items = commentRepository.findAll(pageable);
        return PageDto.<Comment>builder()
                .items(items.getContent())
                .totalPages(items.getTotalPages())
                .totalElements(items.getTotalElements())
                .offset(offset)
                .limit(limit)
                .build();
    }

    public List<Comment> getAllAdmin() {
        return commentRepository.findAll();
    }

    public CommonResponse getAll() {
        CommonResponse response = new CommonResponse();
        response.setSuccessWithDataResponse(commentRepository.getAllCommentByStatus(BaseStatusEnum.ENABLE.getKey()));
        return response;
    }
    public CommonResponse getPage(int offset, int limit) {
        CommonResponse response = new CommonResponse();
        Pageable pageable = SystemUtils.createPageable(offset, limit);
        Page<Comment> page = commentRepository.getPageCommentByStatus(BaseStatusEnum.ENABLE.getKey(), pageable);
        PageDto<Comment> pageDto = PageDto.<Comment>builder()
                .items(page.getContent())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .offset(offset)
                .limit(limit)
                .build();
        response.setSuccessWithDataResponse(pageDto);
        return response;
    }

    public CommonResponse delete(long id) {
        CommonResponse response = new CommonResponse();
        Comment commentExist = commentRepository
                .getCommentByIdAndStatus(id, BaseStatusEnum.ENABLE.getKey())
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.COMMENT_NOT_FOUND));
        commentExist.setStatus(BaseStatusEnum.DISABLE.getKey());
        commentRepository.save(commentExist);
        response.setSuccessResponse(SystemConstant.Message.SUCCESS);
        return response;
    }
    public CommonResponse getDetail(long id) {
        CommonResponse response = new CommonResponse();
        Comment commentExist = commentRepository
                .getCommentByIdAndStatus(id, BaseStatusEnum.ENABLE.getKey())
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.COMMENT_NOT_FOUND));
        response.setSuccessWithDataResponse(commentExist);
        return response;
    }

    public Comment updateStatus(long id, int status) {
        try {
            Comment commentExist = commentRepository
                    .findById(id)
                    .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.COMMENT_NOT_FOUND));
            commentExist.setStatus(status);
            commentRepository.save(commentExist);
            return commentExist;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Comment> updateMultipleStatusArticleByIds(List<Long> ids, int status) {
        try {
            List<Comment> comments = commentRepository.getCommentByIds(ids);
            if (MyCollectionUtils.listIsNullOrEmpty(comments)) {
                return null;
            }
            comments.forEach(c -> c.setStatus(status));
            return commentRepository.saveAll(comments);
        } catch (Exception e) {
            return null;
        }
    }

    public Comment getDetailAdmin(long id) {
        return commentRepository
                .findById(id)
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.COMMENT_NOT_FOUND));
    }

    public CommonResponse getPageByStatusAndArticleId(int offset, int limit, long articleId) {
        CommonResponse response = new CommonResponse();
        Pageable pageable = SystemUtils.createPageable(offset, limit);
        Page<Comment> page = commentRepository.getPageCommentByStatusAndArticleId(articleId, BaseStatusEnum.ENABLE.getKey(), pageable);
        PageDto<Comment> pageDto = PageDto.<Comment>builder()
                .items(page.getContent())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .offset(offset)
                .limit(limit)
                .build();
        response.setSuccessWithDataResponse(pageDto);
        return response;
    }

    public PageDto<Comment> findCommentPaging(FilterCommentRequest request) {
        int limit = request.getLimit() <= 0 ? 10 : request.getLimit();
        int offset = request.getOffset() <= 0 ? 1 : request.getOffset();
        JpaFilterCommentRequest jpaRequest = new JpaFilterCommentRequest(request);
        Pageable pageable = SystemUtils.createPageable(offset, limit);
        Page<Comment> page = commentRepository.findCommentPaging(jpaRequest, pageable);
        return PageDto.<Comment>builder()
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .limit(limit)
                .offset(offset)
                .items(page.getContent())
                .build();
    }

}
