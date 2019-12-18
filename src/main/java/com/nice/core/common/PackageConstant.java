package com.nice.core.common;

public class PackageConstant {
    public static final int LENGTH_FIELD_LEN = 2;  //网络包的前两个字节是有效数据的长度
    public static final int UID_FIELD_LEN = 4;  //从其他服务器回包到gateway时，长度后的四个字节是玩家ID
    public static final int MSG_ID_FIELD_LEN = 4;  // 跟网络消息的消息号
    public static final int RESULT_LEN = 4;//请求结果
    public static final int INDEXT_FIELD_LEN = 4;
    public static final int ROUTERTYPE_LEN = 1;//路由类型
    public static final int INDICATION_INDEX_FIELD_LEN = 2;//indication序列号
    public static final int TOKEN_FIELD_LEN = 4;//token检测
    public static final int SERVER_ID_LEN = 4;//serverId
    public static final int MAX_LENGTH_FIELD_LEN = 32768;//允许包长的最大长度

}
