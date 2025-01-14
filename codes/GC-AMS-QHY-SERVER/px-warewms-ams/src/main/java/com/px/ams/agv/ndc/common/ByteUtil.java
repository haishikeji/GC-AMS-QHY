package com.px.ams.agv.ndc.common;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/8/20
 */
public class ByteUtil {

    public static String arrayShow(byte[] b1) {
        return Arrays.toString(b1);
    }

    public static byte[] hexString2ByteArray(String s1) {
        if(s1 == null || s1.length() == 0){
            return null;
        }
        s1 = s1.replaceAll(" ", "").toUpperCase();
        return DatatypeConverter.parseHexBinary(s1);
    }

    public static String byteArray2HexString(byte[] b1) {
        return DatatypeConverter.printHexBinary(b1);
    }

    public static byte[] string2byteArray(String s1) {
        return s1.getBytes();
    }

    public static String byteArray2String(byte[] b1) {
        if (b1 != null)
            return new String(b1);
        else
            return "";
    }

    public static int string2Int(String s1, int n1) {
        return Integer.parseInt(s1, n1);
    }

    public static String ascii2Bin(String s1) {
        return byteArray2String(hexString2ByteArray(s1));
    }

    public static String bin2Ascii(String s1) {
        return byteArray2HexString(string2byteArray(s1));
    }

    public static byte[] int2byte2(int i) {
        byte[] a = new byte[2];
        a[1] = (byte) (0xff & i);
        a[0] = (byte) ((0xff00 & i) >> 8);
        //a[2] = (byte) ((0xff0000 & i) >> 16);
        //a[3] = (byte) ((0xff000000 & i) >> 24);
        //a  为转换后的byte
        for (int j = 0; j < a.length; j++) {
            System.out.println(a[j]);
        }
        return a;
    }

    /**
     * 把b2的内容复制到b1指定位置
     *
     * @param sp
     * @param b1
     * @param b2
     * @return
     */
    public static byte[] cpbyte(int sp, byte[] b1, byte[] b2) {
        //byte[] code=.int2byte4(fcode);
        for (int i = 0; i < b2.length; i++) {
            b1[i + sp] = b2[i];
        }
        return b1;
    }

    public static byte[] concat(byte[] a, byte[] b) {
        byte[] c = new byte[a.length + b.length];
        for (int i = 0; i < a.length; i++) {
            c[i] = a[i];
        }
        for (int i = 0; i + a.length < c.length; i++) {
            c[i + a.length] = b[i];
        }
        return c;
    }

    public String int2DoubleStr(int i) {
        if (i >= 0) {
            int j = i % 100;
            if (j < 0) {
                return "";
            }
            if (j < 10) {
                return i / 100 + ".0" + j;
            } else {
                return i / 100 + "." + j;
            }
        } else {
            int j = -i % 100;
            if (j < 0) {
                return "";
            }
            if (i / 100 == 0) {
                if (j < 10) {
                    return "-" + i / 100 + ".0" + j;
                } else {
                    return "-" + i / 100 + "." + j;
                }
            } else {
                if (j < 10) {
                    return i / 100 + ".0" + j;
                } else {
                    return i / 100 + "." + j;
                }
            }
        }
    }

    /**
     * 通过byte数组取到无符号short
     * @param b
     * @return
     * @throws IOException
     */
    public static int getUnsignedShort(byte b[], int index){
        return (b[index+0] << 8& 0xFF00) + (b[index+1]<< 0& 0xFF);
    }

    /**
     * 通过byte数组取到short
     *
     * @param b
     * @param index
     *            第几位开始取
     * @return
     */
    public static short getShort(byte[] b, int index) {
        return (short) (((b[index + 0] << 8) | b[index + 1] & 0xff));
    }
    public static short getShortSmallEndia(byte[] b, int index) {
        return (short) (((b[index + 1] << 8) | b[index ] & 0xff));
    }

    /**
     * 通过byte数组取到int
     *
     * @param bb
     * @param index
     *            第几位开始
     * @return
     */
    public static int getInt(byte[] bb, int index) {
        return (int) ((((bb[index + 0] & 0xff) << 24)
                | ((bb[index + 1] & 0xff) << 16)
                | ((bb[index + 2] & 0xff) << 8) | ((bb[index + 3] & 0xff) << 0)));
    }

    /**
     * 16进制转ASCII
     *
     * @param hex
     * @return
     */
    public static String hex2Str(String hex) {
        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for (int i = 0; i < hex.length() - 1; i += 2) {
            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char) decimal);
            temp.append(decimal);
        }
        return sb.toString();
    }

    private static byte toByte(char c){
        byte b = (byte)"0123456789abcdef".indexOf(c);
        return b;
    }

    public static byte[] hexStringToByte(String hex){
        int len = (hex.length()/2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for(int i=0;i<len;i++){
            int pos = i*2;
            result[i] = (byte)(toByte(achar[pos])<< 4 | toByte(achar[pos+1]));
        }
        return result;
    }

    /**
     * 16进制字符串转2进制字符串
     */
    public static String hexString2binaryString(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0)
            return null;
        String bString = "", tmp;
        for (int i = 0; i < hexString.length(); i++) {
            tmp = "0000" + Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
        //return Integer.toBinaryString(Integer.parseInt(hexString, 16));
    }


    public static void main(String[] args) {
        String str = "001205";
        byte[] bytes = DatatypeConverter.parseHexBinary(str);
        System.out.println((bytes[3] & 0xff));
        //System.out.println(ByteUtil.getUnsignedShort(bytes,4));
    }
}