
package net.protol;
public class MsgIDDefineDic
{
	public static final int LOGIN_REQ_LOGIN = 1001; //登录请求
	public static final int LOGIN_RSP_LOGIN = 1002; //登录回复
	public static final int LOGIN_NTF_LOGOUT = 1101; //登出
	public static final int USER_REQ_CREATE_USER = 3001; 
	public static final int USER_RSP_CREATE_USER = 3002; 
	public static final int USER_REQ_CHANGE_NAME = 3003; 
	public static final int USER_RSP_CHANGE_NAME = 3004; 
	public static final int ROOM_REQ_JOIN_MATCH = 3001; 
	public static final int ROOM_RSP_JOIN_MATCH = 3002; 
	public static final int ROOM_REQ_CANCEL_JOIN_MATCH = 3003; 
	public static final int ROOM_RSP_CANCEL_JOIN_MATCH = 3004; 
	public static final int ROOM_NTF_JOIN_MATCH_RESULT = 3101; 
	public static final int ROOM_NTF_JOIN_MATCH_MATEINFO = 3102; 
}
