package com.fpt.edu.herofundbackend.constant;

public class SystemConstant {

    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static class Number{
        public static final long MIN_TARGET_AMOUNT = 50;
        public static final long MAX_TARGET_AMOUNT = 1000000;
    }

    public static class Message {
        public static final String SYSTEM_ERROR = "Hệ thống bận vui lòng thử lại";
        public static final String SUCCESS = "SUCCESS!";
        public static final String FAIL = "FAIL!";
        public static final String CATEGORY_NOT_FOUND = "category không tồn tại!";
        public static final String SPONSOR_NOT_FOUND = "sponsor không tồn tại!";
        public static final String NAME_NOT_EMPTY = "Name không được để trống!";
        public static final String IMAGE_NOT_EMPTY = "image không được để trống";
        public static final String DESCRIPTION_NOT_EMPTY = "description không được để trống";
        public static final String DATE_OF_BIRTH_NOT_EMPTY = "date of birth không được để trống";
        public static final String DATE_OF_BIRTH_INVALID = "date of birth phải dưới dạng dd/mm/yyyy";
        public static final String ADDRESS_NOT_EMPTY = "address không được để trống";
        public static final String PHONE_NOT_EMPTY = "phone không được để trống";
        public static final String LAST_NAME_NOT_EMPTY = "last name không được để trống";
        public static final String FIRST_NAME_NOT_EMPTY = "first name không được để trống";
        public static final String AVATAR_NOT_EMPTY = "avatar không được để trống";
        public static final String ACCOUNT_NOT_EMPTY = "account không được để trống";
        public static final String ACCOUNT_NOT_FOUND = "account không tồn tại hoặc đã bị xóa!";
        public static final String DETAIL_NOT_EMPTY = "detail không được để trống";
        public static final String CAMPAIGN_NOT_FOUND = "campaign không tồn tại hoặc đã bị xóa!";
        public static final String CAMPAIGN_PAYMENT_CHANNEL_NOT_FOUND = "campaign hoặc payment channel không tồn tại";
        public static final String RETURN_URL_NOT_EMPTY = "return url không tồn tại";
        public static final String CANCEL_URL_NOT_EMPTY = "cancel url không tồn tại";
        public static final String AMOUNT_GREATER_THAN_ZERO = "Số tiền phải lớn hơn 0";
        public static final String PAYMENT_SUCCESS = "Thanh toán thành công";
        public static final String PAYMENT_UNPAID = "Chưa thanh toán";
        public static final String PAYMENT_CHANNEL_NOT_FOUND = "Kênh thanh toán không tồn tại";
        public static final String TITLE_NOT_EMPTY = "title không được để trống";
        public static final String START_DATE_NOT_EMPTY = "start date không được để trống";
        public static final String END_DATE_NOT_EMPTY = "end date không được để trống";
        public static final String TARGET_AMOUNT_GREATER_THAN_ZERO = "target amount > 0";
        public static final String PERSONAL_QUANTITY_GREATER_THAN_ZERO = "personal quantity > 0";
        public static final String COUNT_DONOR_GREATER_THAN_ZERO = "count donor > 0";
        public static final String CURRENT_AMOUNT_GREATER_THAN_ZERO = "current amount > 0";
        public static final String FAQ_NOT_FOUND = "faq không tìm thấy";
        public static final String ARTICLE_NOT_FOUND = "article không tồn tại hoặc đã bị xóa!";
        public static final String TOKEN_INVALID = "token invalid";
        public static final String FAIL_UPLOAD = "upload ảnh lỗi! Vui lòng thử lại!";
        public static final String FILE_NOT_EMPTY = "file không được để trống";
        public static final String UPDATE_SUCCESS = "Cập nhật thành công";
        public static final String USERNAME_NOT_EMPTY = "username không được bỏ trống";
        public static final String PASSWORD_NOT_EMPTY = "password không được bỏ trống";
        public static final String CONFIRM_PASSWORD_NOT_EMPTY = "confirm password không được bỏ trống";
        public static final String PASSWORD_NOT_EQUAL_CONFIRM_PASSWORD = "password với confirm password không giống nhau";
        public static final String SIGN_UP_SUCCESS = "Đăng kí thành công";
        public static final String ACCOUNT_OR_PASSWORD_INVALID = "Tài khoản hoặc mật khâu không đúng";
        public static final String ACCOUNT_EXIST = "Tài khoản đã tồn tại!";
        public static final String MIN_TARGET_AMOUNT = "min 50$ is required";
        public static final String MAX_TARGET_AMOUNT = "max 1.000.000$ is required";
        public static final String STATUS_VALIDATION = "status có giá trị là 0 đến 4";
        public static final String INVALID_CONFIG = "Vui lòng kiểm tra lại thông tin";
        public static final String ANONYMOUS_FALSE = "Vui lòng nhập tên khi bạn không muốn ủng hộ ẩn danh";
        public static final String DATE_ERROR_FORMAT = "Định dạng ngày sai";
        public static final String TOKEN_ERROR = "TOKEN INVALID";
        public static final String COMMENT_NOT_FOUND = "bình luận không tồn tại";
        public static final String LIKE_NOT_FOUND = "like không tồn tại hoặc đã bị xóa";
        public static final String LIKE_SPAMMING = "không thể thực hiện hành động, vui lòng thử lại sau";
        public static final String CONTENT_NOT_EMPTY = "nội dung bình luận không được bỏ trống";

        public static final String SAVE_FAIL = "Tạo mới thất bại!";
        public static final String DELETE_FAIL = "Xóa thất bại!";
        public static final String UPDATE_FAIL = "Cập nhật thất bại!";
        public static final String DELETE_SUCCESS = "Xóa thành công!";
        public static final String SAVE_SUCCESS = "Tạo mới thành công!";
        public static final String SIGN_UP_FAIL = "Đăng kí thất bại! Vui lòng thử lại";
        public static final String SAVE_PROFILE_FAIL = "Cập nhập thông tin cá nhân thất bại!";
        public static final String SAVE_PROFILE_SUCCESS = "Cập nhập thông tin cá nhân thành công!";
        public static final String PERMISSION_EDIT_DO_NOT = "Bạn không có quyền chỉnh sửa!";
        public static final String IDS_VALIDATION = "Danh sách id không được để trống";
        public static final String CLIENT_ID_NOT_EMPTY = "ClientId không được để trống";
        public static final String SECRET_ID_NOT_EMPTY = "SecretId không được để trống";
        public static final String PAYER_ID_NOT_EMPTY = "PayerId không được để trống";
    }

    public static class FIELD {
        public static final String ACCOUNT = "account";
        public static final String NAME = "name";
        public static final String IMAGE = "image";
        public static final String DESCRIPTION = "description";
        public static final String AVATAR = "avatar";
        public static final String FIRST_NAME = "first name";
        public static final String LAST_NAME = "last name";
        public static final String PHONE = "phone";
        public static final String ADDRESS = "address";
        public static final String DATE_OF_BIRTH = "date of birth";
        public static final String DETAIL = "detail";
        public static final String RETURN_URL = "returnUrl";
        public static final String CANCEL_URL = "cancelUrl";
        public static final String AMOUNT = "amount";
        public static final String CAMPAIGN = "campaign";
        public static final String PAYMENT_CHANNEL = "paymentChannel";
        public static final String TITLE = "title";
        public static final String START_DATE = "start date";
        public static final String END_DATE = "end date";
        public static final String TARGET_AMOUNT = "target amount";
        public static final String CURRENT_AMOUNT = "current amount";
        public static final String COUNT_DONOR = "count donor";
        public static final String PERSONAL_QUANTITY = "personal quantity";
        public static final String CATEGORY = "category";
        public static final String FIELD_CREATE_AT = "createdAt";
        public static final String SENDING_TIME = "sendingTime";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String CONFIRM_PASSWORD = "confirm password";
        public static final String PAYER_ID = "payer_id";
    }
}
