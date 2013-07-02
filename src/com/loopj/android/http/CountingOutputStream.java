package com.loopj.android.http;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: wangkang
 * Date: 13-6-18
 * Time: 上午12:22
 * To change this template use File | Settings | File Templates.
 */
public final class CountingOutputStream extends FilterOutputStream {

    private CountingListener countingListener;
    private long count;

    /**
     * Wraps another output stream, counting the number of bytes written.
     *
     * @param out the output stream to be wrapped
     */
    public CountingOutputStream(OutputStream out) {
        super(out);
    }

    public CountingOutputStream(OutputStream out, CountingListener countingListener) {
        super(out);
        this.countingListener = countingListener;
    }

    /**
     * Returns the number of bytes written.
     */
    public long getCount() {
        return count;
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        out.write(b, off, len);
        count += len;
        if (countingListener != null) {
            countingListener.onWriteCount(count);
        }
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
        count++;
        if (countingListener != null) {
            countingListener.onWriteCount(count);
        }
    }

    @Override
    public void write(byte[] buffer) throws IOException {
        super.write(buffer);
        if (countingListener != null) {
            countingListener.onWriteCount(count);
        }
    }

    public OutputStream getOutputStream() {
        return out;
    }

    public interface CountingListener {
        void onWriteCount(long count);
    }
}
