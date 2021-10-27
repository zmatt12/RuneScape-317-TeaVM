package web.impl.js.fs;

import org.teavm.classlib.fs.VirtualFileAccessor;
import org.teavm.jso.core.JSNumber;
import web.impl.js.fs.generic.GenericFileSystem;

import java.io.IOException;
import java.util.Arrays;

//This class is adapted from the BufferedRandomAccessFile implementation take from cassandra
//https://github.com/facebookarchive/cassandra/blob/master/src/org/apache/cassandra/io/BufferedRandomAccessFile.java
public final class BufferedFileAccessor extends FileAccessor {

    static final int LogBuffSz_ = 10; // 1024 buffer
    public static final int BuffSz_ = (1 << LogBuffSz_);
    static final long BuffMask_ = ~(((long) BuffSz_) - 1L);

    /*
     * This implementation is based on the buffer implementation in Modula-3's
     * "Rd", "Wr", "RdClass", and "WrClass" interfaces.
     */
    private boolean dirty_; // true iff unflushed bytes exist
    private boolean closed_; // true iff the file is closed
    private long curr_; // current position in file
    private long lo_, hi_; // bounds on characters in "buff"
    private byte[] buff_; // local buffer
    private long maxHi_; // this.lo + this.buff.length
    private boolean hitEOF_; // buffer contains last file block?
    private long diskPos_; // disk position

    public BufferedFileAccessor(JSNumber fd, GenericFileSystem fs) {
        this(fd, fs, 0);
    }

    public BufferedFileAccessor(JSNumber fd, GenericFileSystem fs, int size) {
        super(fd, fs);
        this.init(size);
    }

    private void init(int size)
    {
        this.dirty_ = this.closed_ = false;
        this.lo_ = this.curr_ = this.hi_ = 0;
        this.buff_ = (size > BuffSz_) ? new byte[size] : new byte[BuffSz_];
        this.maxHi_ = (long) BuffSz_;
        this.hitEOF_ = false;
        this.diskPos_ = 0L;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (this.curr_ >= this.hi_)
        {
            // test for EOF
            // if (this.hi < this.maxHi) return -1;
            if (this.hitEOF_)
                return 0;

            // slow path -- read another buffer
            this.seek((int) this.curr_);
            if (this.curr_ == this.hi_)
                return 0;
        }
        len = Math.min(len, (int) (this.hi_ - this.curr_));
        int buffOff = (int) (this.curr_ - this.lo_);
        System.arraycopy(this.buff_, buffOff, b, off, len);
        this.curr_ += len;
        return len;
    }

    public void write(byte[] b, int off, int len) throws IOException
    {
        while (len > 0)
        {
            int n = this.writeAtMost(b, off, len);
            off += n;
            len -= n;
            this.dirty_ = true;
        }
    }

    /*
     * Write at most "len" bytes to "b" starting at position "off", and return
     * the number of bytes written.
     */
    private int writeAtMost(byte[] b, int off, int len) throws IOException
    {
        if (this.curr_ >= this.hi_)
        {
            if (this.hitEOF_ && this.hi_ < this.maxHi_)
            {
                // at EOF -- bump "hi"
                this.hi_ = this.maxHi_;
            }
            else
            {
                // slow path -- write current buffer; read next one
                this.seek((int) this.curr_);
                if (this.curr_ == this.hi_)
                {
                    // appending to EOF -- bump "hi"
                    this.hi_ = this.maxHi_;
                }
            }
        }
        len = (int) Math.min(len, this.hi_ - this.curr_);
        int buffOff = (int) (this.curr_ - this.lo_);
        System.arraycopy(b, off, this.buff_, buffOff, len);
        this.curr_ += len;
        return len;
    }

    @Override
    public int tell() throws IOException {
        return (int) this.curr_;
    }

    @Override
    public void seek(int pos) throws IOException
    {
        if (pos >= this.hi_ || pos < this.lo_)
        {
            // seeking outside of current buffer -- flush and read
            this.flushBuffer();
            this.lo_ = pos & BuffMask_; // start at BuffSz boundary
            this.maxHi_ = this.lo_ + (long) this.buff_.length;
            if (this.diskPos_ != this.lo_)
            {
                super.seek((int) this.lo_);
                this.diskPos_ = this.lo_;
            }
            int n = this.fillBuffer();
            this.hi_ = this.lo_ + (long) n;
        }
        else
        {
            // seeking inside current buffer -- no read required
            if (pos < this.curr_)
            {
                // if seeking backwards, we must flush to maintain V4
                this.flushBuffer();
            }
        }
        this.curr_ = pos;
    }

    @Override
    public void skip(int amount) throws IOException {
        seek(tell() + amount);
    }

    @Override
    public int size() throws IOException {
        return (int) Math.max(this.curr_, super.size());
    }

    @Override
    public void resize(int size) throws IOException {
        System.out.println("Need to implment lul");
        throw new UnsupportedOperationException("Unimplemented");
    }

    @Override
    public void close() throws IOException {
        this.flush();
        this.closed_ = true;
        super.close();
    }

    @Override
    public void flush() throws IOException
    {
        this.flushBuffer();
        super.flush();
    }

    private void flushBuffer() throws IOException
    {
        if (this.dirty_)
        {
            if (this.diskPos_ != this.lo_)
                super.seek((int) this.lo_);
            int len = (int) (this.curr_ - this.lo_);
            super.write(this.buff_, 0, len);
            this.diskPos_ = this.curr_;
            this.dirty_ = false;
        }
    }

    private int fillBuffer() throws IOException
    {
        int cnt = 0;
        int rem = this.buff_.length;
        while (rem > 0)
        {
            int n = super.read(this.buff_, cnt, rem);
            if (n < 1)
                break;
            cnt += n;
            rem -= n;
        }
        if ( (cnt < 0) && (this.hitEOF_ = (cnt < this.buff_.length)) )
        {
            // make sure buffer that wasn't read is initialized with -1
            Arrays.fill(this.buff_, cnt, this.buff_.length, (byte) 0xff);
        }
        this.diskPos_ += cnt;
        return cnt;
    }
}
