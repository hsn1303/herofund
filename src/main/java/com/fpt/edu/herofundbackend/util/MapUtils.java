package com.fpt.edu.herofundbackend.util;

import com.fpt.edu.herofundbackend.dto.article.ArticleRequest;
import com.fpt.edu.herofundbackend.dto.campaign.CampaignRequest;
import com.fpt.edu.herofundbackend.dto.comment.CommentRequest;
import com.fpt.edu.herofundbackend.dto.like.LikeRequest;
import com.fpt.edu.herofundbackend.dto.payment.PaymentOder;
import com.fpt.edu.herofundbackend.dto.profile.ProfileRequest;
import com.fpt.edu.herofundbackend.dto.sponsor.SponsorRequest;
import com.fpt.edu.herofundbackend.entity.*;

public class MapUtils {

    public static Sponsor formRequestToSponsorEntity(SponsorRequest request){
        Sponsor sponsor = new Sponsor();
        sponsor.setDescription(request.getDescription());
        sponsor.setImage(request.getImage());
        sponsor.setDetail(request.getDetail());
        sponsor.setName(request.getName());
        return  sponsor;
    }

    public static Profile formRequestToProfileEntity(ProfileRequest request){
        Profile profile = new Profile();
        profile.setFirstName(request.getFirstName());
        profile.setEmail(request.getEmail());
        profile.setLastName(request.getLastName());
        profile.setAddress(request.getAddress());
        profile.setPhone(request.getPhone());
        profile.setAvatar(request.getAvatar());
        profile.setDateOfBirth(DateUtils.stringToLocalDateDMY(request.getDateOfBirth()));
        return profile;
    }

    public static Transaction fromPaypalOrderToTransaction(PaymentOder order){
        return Transaction.builder()
                .amount(order.getAmount())
                .campaignId(order.getCampaignId())
                .message(order.getMessage())
                .senderName(order.getSenderName())
                .accountId(order.getAccountId())
                .paymentChannel(order.getPaymentChannel())
                .build();
    }

    public static Campaign fromRequestToCampaign(CampaignRequest request){
        return Campaign.builder()
                .title(request.getTitle())
                .detail(request.getDetail())
                .description(request.getDescription())
                .image(request.getImage())
                .startDate(DateUtils.stringToLocalDateYMD(request.getStartDate()))
                .endDate(DateUtils.stringToLocalDateYMD(request.getEndDate()))
                .targetAmount(request.getTargetAmount())
                .categoryId(request.getCategoryId())
                .sponsorId(request.getSponsorId())
                .build();
    }

    public static Article fromRequestToArticle(ArticleRequest request) {
        return Article.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .detail(request.getDetail())
                .image(request.getImage())
                .campaignId(request.getCampaignId())
                .build();
    }

    public static Like fromRequestToLike(LikeRequest request) {
        return Like.builder()
                .typeId(request.getTypeId())
                .itemId(request.getItemId())
                .accountId(request.getAccountId())
                .build();
    }

    public static Comment fromRequestToComment(CommentRequest request) {
        return Comment.builder()
                .articleId(request.getArticleId())
                .content(request.getContent())
                .build();
    }
}
