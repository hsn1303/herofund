package com.fpt.edu.herofundbackend.constant;

public class PaypalConstant {

    public final static String URL_LOGIN = "/v1/oauth2/token";
    public final static String URL_PAYMENT = "/v2/checkout/orders";
//    public final static String URL_PAYMENT_EXECUTE = "/v1/payments/payment/%s/execute";
    public final static String URL_PAYMENT_EXECUTE = "/v2/checkout/orders/%s/capture";

    public final static String GRANT_TYPE = "grant_type";
    public final static String CLIENT_CREDENTIALS = "client_credentials";
    public final static String BASIC = "Basic ";
    public final static String STATE_CREATED = "created";
    public final static String REL_APPROVAL_URL = "approve";
}
