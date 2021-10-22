package web.impl.jvm.impl;

import web.IAllocator;

class JVMAllocator implements IAllocator {
    @Override
    public byte[][][] byteArray(int len1, int len2, int len3) {
        return new byte[len1][len2][len3];
    }

    public int[][][] intArray(int len1, int len2, int len3) {
        return new int[len1][len2][len3];
    }
}
