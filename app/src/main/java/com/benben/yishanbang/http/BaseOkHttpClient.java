package com.benben.yishanbang.http;

import android.app.Activity;
import android.net.Uri;

import com.benben.commoncore.utils.LogUtils;
import com.benben.yishanbang.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Description:封装OkHttp
 *
 * @author zjn
 * Email：168455992@qq.com
 * @date 2019/1/11
 */
public class BaseOkHttpClient {
    private Builder mBuilder;

    private BaseOkHttpClient(Builder builder) {
        this.mBuilder = builder;
    }

    public Request buildRequest() {
        Request.Builder builder = new Request.Builder();
        builder.addHeader("X-AUTH-TOKEN", "" + (MyApplication.mPreferenceProvider.getToken() == null ?
                "" : MyApplication.mPreferenceProvider.getToken()));
//  builder.addHeader("Content-Type", "application/json; charset=UTF-8")
////                    .addHeader("Accept-Encoding", "*")
//                .addHeader("Connection", "keep-alive")
//                .addHeader("Accept", "*/*")
//                .addHeader("Access-Control-Allow-Origin", "*")
//                .addHeader("Access-Control-Allow-Headers", "X-Requested-With")
//                .addHeader("Vary", "Accept-Encoding");
//
        LogUtils.e("TAG", "************ Token =" + (MyApplication.mPreferenceProvider.getToken() == null ?
                "" : MyApplication.mPreferenceProvider.getToken()));
        if ("GET".equals(mBuilder.method)) {
            builder.url(buildGetRequestParam());
            LogUtils.e("TAG", "************ GET -- RequestUrl =" + buildGetRequestParam());
            builder.get();
        } else if ("POST".equals(mBuilder.method)) {
            LogUtils.e("TAG", "************ POST -- RequestUrl =" + mBuilder.url);
            builder.url(mBuilder.url);
            try {
                builder.post(buildPostRequestParam());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return builder.build();
    }

    private String jsonSort(Map<String, String> paramsMap) {
        String str = "";
        Set<String> keySet = paramsMap.keySet();
        Iterator<String> iter = keySet.iterator();
        StringBuffer sb = new StringBuffer("");
        while (iter.hasNext()) {
            String key = iter.next();
            sb.append(key).append("=").append(paramsMap.get(key)).append("&");
        }
        str = sb.toString();
        return str.substring(0, str.length() - 1);
    }

    /**
     * GET拼接参数
     *
     * @return
     */
    private String buildGetRequestParam() {
        Uri.Builder builder = Uri.parse(mBuilder.url).buildUpon();
        if (mBuilder.params.size() <= 0) {
            String url = builder.build().toString();
            return url;
        }
        for (RequestParameter p : mBuilder.params) {
            builder.appendQueryParameter(p.getKey(), p.getObj() == null ? "" : p.getObj().toString());
        }
        String url = builder.build().toString();
      //  LogUtils.e("TAG", "************GET---参数 =" + url);//application/json; charset=utf-8

        return url;
    }

    /**
     * POST拼接参数
     *
     * @return
     */
    private RequestBody buildPostRequestParam() throws JSONException {
        if (mBuilder.isJsonParam) {//如果是json  就以json形式传参数
            JSONObject jsonObj = new JSONObject();
            for (RequestParameter p : mBuilder.params) {
                jsonObj.put(p.getKey(), p.getObj());//这
            }
            String json = jsonObj.toString();
          //  LogUtils.e("TAG", "************POST---参数 =" + json);//application/json; charset=utf-8
            return RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), json);
        }
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        JSONObject jsonObj = new JSONObject();
        for (RequestParameter p : mBuilder.params) {
            if (p.getFile() != null) {
                MediaType type = MediaType.parse("image/png");
                builder.addFormDataPart(p.getKey(), p.getObj() == null ? "" : p.getObj().toString(), RequestBody.create(type, p.getFile()));
            } else {
                jsonObj.put(p.getKey(), p.getObj());
                builder.addFormDataPart(p.getKey(), p.getObj() == null ? "" : p.getObj().toString());
            }
        }
        String json = jsonObj.toString();
        LogUtils.e("TAG", "post=" + json);
        return builder.build();
    }

    /**
     * 回调调用
     *
     * @param callBack
     */
    public void enqueue(Activity activity, BaseCallBack callBack) {
        OkHttpManager.getInstance().request(activity, this, callBack);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String url;
        private String method;
        private List<RequestParameter> params;
        private boolean isJsonParam;

        public BaseOkHttpClient build() {
            return new BaseOkHttpClient(this);
        }

        private Builder() {
            method = "GET";
            params = new ArrayList<>();
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        /**
         * GET请求
         *
         * @return
         */
        public Builder get() {
            method = "GET";
            return this;
        }

        /**
         * POST请求
         *
         * @return
         */
        public Builder post() {
            method = "POST";
            return this;
        }

        /**
         * JSON参数
         *
         * @return
         */
        public Builder json() {
            isJsonParam = true;
            return post();
        }

        /**
         * Form请求
         *
         * @return
         */
        public Builder form() {
            return this;
        }

        /**
         * 添加参数
         *
         * @param key
         * @param value
         * @return
         */
        public Builder addParam(String key, Object value) {
            if (params == null) {
                params = new ArrayList<>();
            }
            params.add(new RequestParameter(key, value));
            return this;
        }

        public Builder addFile(String key, String fileName, File file) {
            if (params == null) {
                params = new ArrayList<>();
            }
            params.add(new RequestParameter(key, fileName, file));
            return this;
        }
    }

    public static String getSHA(String info) {
        byte[] digesta = null;
        try {
            // 得到一个SHA-1的消息摘要
            MessageDigest alga = MessageDigest.getInstance("SHA-1");
            // 添加要进行计算摘要的信息
            alga.update(info.getBytes());
            // 得到该摘要
            digesta = alga.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 将摘要转为字符串
        String rs = byte2hex(digesta);
        return rs;
    }

    public String sha1(String data) throws NoSuchAlgorithmException {
        //加盐   更安全一些
        data += "lyz";
        //信息摘要器                                算法名称
        MessageDigest md = MessageDigest.getInstance("SHA1");
        //把字符串转为字节数组
        byte[] b = data.getBytes();
        //使用指定的字节来更新我们的摘要
        md.update(b);
        //获取密文  （完成摘要计算）
        byte[] b2 = md.digest();
        //获取计算的长度
        int len = b2.length;
        //16进制字符串
        String str = "0123456789abcdef";
        //把字符串转为字符串数组
        char[] ch = str.toCharArray();

        //创建一个40位长度的字节数组
        char[] chs = new char[len * 2];
        //循环20次
        for (int i = 0, k = 0; i < len; i++) {
            byte b3 = b2[i];//获取摘要计算后的字节数组中的每个字节
            // >>>:无符号右移
            // &:按位与
            //0xf:0-15的数字
            chs[k++] = ch[b3 >>> 4 & 0xf];
            chs[k++] = ch[b3 & 0xf];
        }

        //字符数组转为字符串
        return new String(chs);
    }

    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (byte aB : b) {
            stmp = (Integer.toHexString(aB & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs;
    }
}
