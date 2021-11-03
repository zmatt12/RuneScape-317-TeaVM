package web.impl.js;

import web.IAllocator;

import java.util.Arrays;

public class JSAllocator implements IAllocator {

    public byte[][][] byteArray(int len, int len2, int len3){
        byte[][][] res = new byte[len][len2][len3];
        for(int i = 0; i < len; i++){
            res[i] = new byte[len2][len3];
            for(int j = 0; j < len2; j++){
                res[i][j] = new byte[len3];
                Arrays.fill(res[i][j], (byte) 0);
            }
        }
        return res;
    }
}
