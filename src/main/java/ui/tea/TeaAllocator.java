package ui.tea;

import ui.IAllocator;

import java.util.Arrays;

public class TeaAllocator implements IAllocator {

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

    @Override
    public int[][][] intArray(int len1, int len2, int len3) {
        int[][][] res = new int[len1][len2][len3];
        for(int i = 0; i < len1; i++){
            res[i] = new int[len2][len3];
            for(int j = 0; j < len2; j++){
                res[i][j] = new int[len3];
                Arrays.fill(res[i][j],0);
            }
        }
        return res;
    }
}
