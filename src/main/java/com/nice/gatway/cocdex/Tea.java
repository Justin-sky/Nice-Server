package com.nice.gatway.cocdex;

import com.nice.core.netty.cocdex.ClientDate;
import com.nice.core.netty.cocdex.NetData;
import com.nice.core.utils.TimeUtil;
import com.nice.gatway.parser.PacketNetData;

import java.util.Random;

/**
 * Tea算法
 * 每次操作可以处理8个字节数据
 * KEY为16字节,应为包含4个int型数的int[]，一个int为4个字节
 * 加密解密轮数应为8的倍数，推荐加密轮数为64轮
 */
public class Tea {
    public static int[] KEY = new int[]{//加密解密所用的KEY
            0x789f5645, 0xf68bd5a4,
            0x81963ffa, 0x458fac58
    };
    private static int TIMES = 32;

    public static int[] generateSecretKey(){
        Random rand = new Random();
        int[] secretKey = new int[4];
        for(int i=0; i<4; i++){
            secretKey[i] = rand.nextInt();
        }
        return secretKey;
    }
    //若某字节被解释成负的则需将其转成无符号正数
    private static int transform(byte temp){
        int tempInt = (int)temp;
        if(tempInt < 0){
            tempInt += 256;
        }
        return tempInt;
    }

    public static int byteToInt(byte[] content, int offset){
        int result = transform(content[offset+3]) | transform(content[offset+2]) << 8 |
                transform(content[offset+1]) << 16 | (int)content[offset] << 24;
        return result;
    }

    public static void intToByte(byte[] bys, int offset, int content){
        bys[offset+3] = (byte)(content & 0xff);
        bys[offset+2] = (byte)((content >> 8) & 0xff);
        bys[offset+1] = (byte)((content >> 16) & 0xff);
        bys[offset] = (byte)((content >> 24) & 0xff);
    }

    //加密
    public static byte[] encrypt(byte[] content, int offset, int[] key, int times){//times为加密轮数
        int delta=0x9e3779b9; //这是算法标准给的值
        int a = key[0], b = key[1], c = key[2], d = key[3];
        for(int m=offset; m<content.length; m+=8)
        {
            int y = byteToInt(content, m), z = byteToInt(content, m+4), sum = 0, i;
            for (i = 0; i < times; i++) {
                sum += delta;
                y += ((z<<4) + a) ^ (z + sum) ^ ((z>>5) + b);
                z += ((y<<4) + c) ^ (y + sum) ^ ((y>>5) + d);
            }
            intToByte(content, m, y);
            intToByte(content, m+4, z);
        }
        return content;
    }
    //解密
    public static byte[] decrypt(byte[] encryptContent, int offset, int[] key, int times){
        int delta = 0x9e3779b9; //这是算法标准给的值
        int a = key[0], b = key[1], c = key[2], d = key[3];

        for(int m=offset; m<encryptContent.length; m+=8) {
            int y = byteToInt(encryptContent, m), z = byteToInt(encryptContent, m+4), sum = 0xC6EF3720, i;
            for (i = 0; i < times; i++) {
                z -= ((y << 4) + c) ^ (y + sum) ^ ((y >> 5) + d);
                y -= ((z << 4) + a) ^ (z + sum) ^ ((z >> 5) + b);
                sum -= delta;
            }

            intToByte(encryptContent, m, y);
            intToByte(encryptContent, m+4, z);
        }
        return encryptContent;
    }

    public static void shortToByte(byte[] bys, int offset, int content){
        bys[offset+1] = (byte)(content & 0xff);
        bys[offset] = (byte)((content >> 8) & 0xff);
    }

    public static int byteToShort(byte[] content, int offset){
        int result = transform(content[offset+1]) | transform(content[offset]) << 8;
        return result;
    }

    public static byte[] encrypt2(byte[] content, int[] key) {
        int tobalLen = (content.length+7)/8*8 + 2;
        byte[] bys = new byte[tobalLen];
        System.arraycopy(content, 0, bys, 2, content.length);

        byte[] encryptContent = encrypt(bys, 2, key, TIMES);
        shortToByte(encryptContent, 0, content.length);
        return encryptContent;
    }
    public static byte[] decrypt2(byte[] encryptContent, int len, int offset, int[] key){
        byte[] decryptContent = decrypt(encryptContent, offset, key, TIMES);
        if(len == decryptContent.length){
            return decryptContent;
        }
        byte[] bys = new byte[len];
        System.arraycopy(decryptContent, 0, bys, 0, len);
        return bys;
    }


    //byte[]型数据转成int[]型数据
    private static int[] byteToIntArray(byte[] content, int offset) {

        int[] result = new int[content.length >> 2]; //除以2的n次方 == 右移n位 即 content.length / 4 == content.length >> 2
        for (int i = 0, j = offset; j < content.length; i++, j += 4) {
            result[i] = byteToInt(content, j);
        }
        return result;
    }

    //int[]型数据转成byte[]型数据
    private static byte[] intArrayToByte(int[] content, int offset) {
        byte[] result = new byte[content.length << 2]; //乘以2的n次方 == 左移n位 即 content.length * 4 == content.length << 2
        for (int i = 0, j = offset; j < result.length; i++, j += 4) {
            intToByte(result, j, content[i]);
        }
        return result;
    }



}
