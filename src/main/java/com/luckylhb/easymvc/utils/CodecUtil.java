package com.luckylhb.easymvc.utils;

import com.luckylhb.easymvc.constants.SystemConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 编码与解码操作工具类
 * Created by lucky on 2017/3/12.
 */
public final class CodecUtil {

    private static final Logger logger = LoggerFactory.getLogger(CodecUtil.class);

    /**
     * 将 URL 编码
     *
     * @param source
     * @return
     */
    public static String encodeURL(String source) {
        String target;
        try {
            target = URLEncoder.encode(source, SystemConst.UTF8);
        } catch (UnsupportedEncodingException e) {
            logger.error("encode url failure...", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * 将 URL 解码
     *
     * @param source
     * @return
     */
    public static String decodeURL(String source) {
        String target;
        try {
            target = URLDecoder.decode(source, SystemConst.UTF8);
        } catch (UnsupportedEncodingException e) {
            logger.error("decode url failure...", e);
            throw new RuntimeException(e);
        }
        return target;
    }
}
