/*
 * Copyright 1999-2012 Alibaba Group.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.dubbo.remoting.buffer;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author <a href="mailto:gang.lvg@alibaba-inc.com">kimi</a>
 */
public class ChannelBufferInputStream extends InputStream {

    private final ChannelBuffer buffer;
    private final int startIndex;
    private final int endIndex;

    public ChannelBufferInputStream(ChannelBuffer buffer) {
        this(buffer, buffer.readableBytes());
    }

    public ChannelBufferInputStream(ChannelBuffer buffer, int length) {
        if (buffer == null) {
            throw new NullPointerException("buffer");
        }
        if (length < 0) {
            throw new IllegalArgumentException("length: " + length);
        }
        if (length > buffer.readableBytes()) {
            throw new IndexOutOfBoundsException();
        }

        this.buffer = buffer;
        startIndex = buffer.readerIndex();
        endIndex = startIndex + length;
        buffer.markReaderIndex();
    }

    /**
     * 已经读了多少字节
     *
     * @return
     */
    public int readBytes() {
        return buffer.readerIndex() - startIndex;
    }

    /**
     * 还有多少字节未读
     *
     * @return
     * @throws IOException
     */
    @Override
    public int available() throws IOException {
        return endIndex - buffer.readerIndex();
    }

    /**
     * 标记读的位置
     *
     * @param readlimit
     */
    @Override
    public void mark(int readlimit) {
        buffer.markReaderIndex();
    }

    /**
     * 是否支持标记
     *
     * @return
     */
    @Override
    public boolean markSupported() {
        return true;
    }

    /**
     * 读取1个字节
     *
     * @return
     * @throws IOException
     */
    @Override
    public int read() throws IOException {
        if (!buffer.readable()) {
            return -1;
        }
        //1111 1111
        //8421 8421
        return buffer.readByte() & 0xff;
    }

    /**
     * 读一个字节数组
     *
     * @param b
     * @param off
     * @param len
     * @return
     * @throws IOException
     */
    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int available = available();
        if (available == 0) {
            return -1;
        }

        len = Math.min(available, len);
        buffer.readBytes(b, off, len);
        return len;
    }

    /**
     * 重置读节点位置
     *
     * @throws IOException
     */
    @Override
    public void reset() throws IOException {
        buffer.resetReaderIndex();
    }

    /**
     * 跳过几个字节
     *
     * @param n
     * @return
     * @throws IOException
     */
    @Override
    public long skip(long n) throws IOException {
        if (n > Integer.MAX_VALUE) {
            return skipBytes(Integer.MAX_VALUE);
        } else {
            return skipBytes((int) n);
        }
    }

    private int skipBytes(int n) throws IOException {
        int nBytes = Math.min(available(), n);
        buffer.skipBytes(nBytes);
        return nBytes;
    }

}
