package com.fpt.edu.herofundbackend.service;

import com.fpt.edu.herofundbackend.constant.SystemConstant;
import com.fpt.edu.herofundbackend.dto.CommonResponse;
import com.fpt.edu.herofundbackend.dto.like.LikeRequest;
import com.fpt.edu.herofundbackend.entity.Account;
import com.fpt.edu.herofundbackend.entity.Article;
import com.fpt.edu.herofundbackend.entity.Comment;
import com.fpt.edu.herofundbackend.entity.Like;
import com.fpt.edu.herofundbackend.enums.BaseStatusEnum;
import com.fpt.edu.herofundbackend.enums.TypeEnum;
import com.fpt.edu.herofundbackend.repository.AccountRepository;
import com.fpt.edu.herofundbackend.repository.ArticleRepository;
import com.fpt.edu.herofundbackend.repository.CommentRepository;
import com.fpt.edu.herofundbackend.repository.LikeRepository;
import com.fpt.edu.herofundbackend.util.MapUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class LikeService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final AccountRepository accountRepository;
    private final LikeRepository likeRepository;

    public CommonResponse saveOrUpdate(LikeRequest request) {
        CommonResponse response = new CommonResponse();
        Like like = new Like();

        Account account = accountRepository.getAccountByIdAndStatus(request.getAccountId(),
                BaseStatusEnum.ENABLE.getKey()).orElse(null);
        if (null == account) {
            response.setFailResponse(SystemConstant.Message.ACCOUNT_NOT_FOUND);
            return response;
        }
        if (TypeEnum.ARTICLE.getValue() == request.getTypeId()){
            Article article = articleRepository.getArticleByIdAndStatus(request.getItemId(),
                    BaseStatusEnum.ENABLE.getKey()).orElse(null);
            if (null == article) {
                response.setFailResponse(SystemConstant.Message.ARTICLE_NOT_FOUND);
                return response;
            }
            like = likeRepository.getLikeByAccountIdAndItemIdAndTypeId(account.getId(), article.getId()
                    , TypeEnum.ARTICLE.getValue()).orElse(null);
        }else if (TypeEnum.COMMENT.getValue() == request.getTypeId()){
            Comment comment = commentRepository.getCommentByIdAndStatus(request.getItemId(),
                    BaseStatusEnum.ENABLE.getKey()).orElse(null);
            if (null == comment) {
                response.setFailResponse(SystemConstant.Message.COMMENT_NOT_FOUND);
                return response;
            }
            like = likeRepository.getLikeByAccountIdAndItemIdAndTypeId(account.getId(), comment.getId()
                    , TypeEnum.COMMENT.getValue()).orElse(null);
        }else {
            response.setFailResponse("type not found");
            return response;
        }
        //like lan dau
        if (null == like) {
            like = MapUtils.fromRequestToLike(request);
            like.setStatus(BaseStatusEnum.ENABLE.getKey());
            response.setSuccessWithDataResponse(likeRepository.save(like));
            return response;
        }
        like.setStatus(request.getStatus());
        response.setSuccessWithDataResponse(likeRepository.save(like));
        return response;
    }

    public CommonResponse delete(long id) {
        CommonResponse response = new CommonResponse();
        Like existLike = likeRepository.getLikeByIdAndStatus(id, BaseStatusEnum.ENABLE.getKey()).orElse(null);
        if (null == existLike) {
            response.setFailResponse(SystemConstant.Message.LIKE_NOT_FOUND);
            return response;
        }
        existLike.setStatus(BaseStatusEnum.DISABLE.getKey());
        likeRepository.save(existLike);
        response.setSuccessResponse(SystemConstant.Message.SUCCESS);
        return response;
    }
}
