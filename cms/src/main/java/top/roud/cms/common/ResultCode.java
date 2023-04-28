package top.roud.cms.common;

/**
 * 统一并自定义返回状态码，如有需求可以另外增加
 */
public enum ResultCode {
	
	/* 成功状态码 */
	SUCCESS(1, "操作成功"),
	SEND_VERTIFYCODE_SUCCESS(1,"验证码已发送至您的邮箱，三分钟内有效，请注意查收"),
	
	/* 参数错误：10001-19999 */
	PARAM_IS_INVALID(10001, "参数无效"),
	PARAM_IS_BLANK(10002, "参数为空"),
	PARAM_TYPE_BIND_ERROR(10003, "参数类型错误"),
	PARAM_NOT_COMPLETE(10004, "参数缺失"),
	
	
	/* 用户错误：20001-29999*/
	USER_NOT_LOGIN(20001, "用户未登录"),
	USER_LOGIN_ERROR(20002, "账号不存在或密码错误"),
	USER_ACCOUNT_FORBIDDEN(20003, "账号已被禁用"),
	USER_NOT_EXIST(20004, "用户不存在"),
	USER_HAS_EXISTED(20005, "用户已存在"),
	EMAIL_HAS_EXISTED(2006, "邮箱已被注册"),
	
	/* 业务错误：30001-39999 */
	SPECIFIED_QUESTIONED_USER_NOT_EXIST(30001, "业务逻辑出现问题"),
	TAG_NAME_FORMAT_ERROR(30002, "标签名格式错误"),
	
	/* 系统错误：40001-49999 */
	SYSTEM_INNER_ERROR(40001, "系统内部错误，请稍后重试"),
	REQUEST_FAST_REFUSE(40002, "请求过快，请稍后重试"),
	SYSTEM_ERROR(40003, "系统繁忙"),

	/* 数据错误：50001-599999 */
	DATA_NONE(50001, "数据未找到"),
	DATA_WRONG(50002, "数据错误"),
	DATA_EXISTED(50003, "数据已存在"),
	
	
	/* 接口错误：60001-69999 */
	INTERFACE_INNER_INVOKE_ERROR(60001, "内部系统接口调用异常"),
	INTERFACE_OUTTER_INVOKE_ERROR(60002, "外部系统接口调用异常"),
	INTERFACE_FORBID_VISIT(60003, "该接口禁止访问"),
	INTERFACE_ADDRESS_INVALID(60004, "接口地址无效"),
	INTERFACE_REQUEST_TIMEOUT(60005, "接口请求超时"),
	REGISTER_CLOSED(60006, "系统未开放注册功能"),
	
	/* 权限错误：70001-79999 */
	PERMISSION_NO_ACCESS(70001, "无访问权限"),
	USER_NO_ACCESS(70002, "用户无操作权限"),
	TOKEN_NULL(80001,"token为空，无法登录"),
	TOKEN_INVALID(80002, "无效token"),
	TOKEN_EXPIRED(80003, "token过期"),
	SIGN_INVALID(80004, "无效签名"),
	TOKEN_ARITHMETERROR(80004, "算法不一致"),
	CAPTCHA_TIMEOUT(80007,"图片验证码过期，请点击刷新图片验证码"),
	CAPTCHA_ERROR(80008,"验证码错误");

	private Integer code;

	private String message;

	ResultCode(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer code() {
		return this.code;
	}

	public String message() {
		return this.message;
	}
}
