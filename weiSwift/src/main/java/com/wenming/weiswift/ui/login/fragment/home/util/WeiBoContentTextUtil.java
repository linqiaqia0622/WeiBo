package com.wenming.weiswift.ui.login.fragment.home.util;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.legacy.ShortUrlAPI;
import com.wenming.weiswift.ui.common.login.AccessTokenKeeper;
import com.wenming.weiswift.ui.common.login.Constants;
import com.wenming.weiswift.ui.login.fragment.home.video.MyWebView;
import com.wenming.weiswift.utils.LogUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wenmingvs on 16/4/16.
 */
public class WeiBoContentTextUtil {

    private static final String AT = "@[\u4e00-\u9fa5\\w]+";// @人
    private static final String TOPIC = "#[\u4e00-\u9fa5\\w]+#";// ##话题
    private static final String URL = "http://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";// url
    private static final String ALL = "(@[\u4e00-\u9fa5\\w]+)|(#[\u4e00-\u9fa5\\w]+#)|(http://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])";

    public static SpannableString getWeiBoContent(String source, final Context context, TextView textView) {
        SpannableString spannableString = new SpannableString(source);
        //设置正则
        Pattern pattern = Pattern.compile(ALL);
        Matcher matcher = pattern.matcher(spannableString);

        if (matcher.find()) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            matcher.reset();
        }

        while (matcher.find()) {
            final String at = matcher.group(1);
            final String topic = matcher.group(2);
            final String url = matcher.group(3);

            //处理@用户
            if (at != null) {
                int start = matcher.start(1);
                int end = start + at.length();
                WeiBoContentClickableSpan myClickableSpan = new WeiBoContentClickableSpan(context) {
                    @Override
                    public void onClick(View widget) {
                        Toast.makeText(context, "点击了用户：" + at, Toast.LENGTH_SHORT).show();
                    }
                };
                spannableString.setSpan(myClickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            //处理##话题
            if (topic != null) {
                int start = matcher.start(2);
                int end = start + topic.length();
                WeiBoContentClickableSpan clickableSpan = new WeiBoContentClickableSpan(context) {

                    @Override
                    public void onClick(View widget) {
                        Toast.makeText(context, "点击了话题：" + topic, Toast.LENGTH_LONG).show();
                    }
                };
                spannableString.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            // 处理url地址
            if (url != null) {
                int start = matcher.start(3);
                int end = start + url.length();
                WeiBoContentClickableSpan clickableSpan = new WeiBoContentClickableSpan(context) {

                    @Override
                    public void onClick(View widget) {
//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                        context.startActivity(browserIntent);

                        ShortUrlAPI shortUrlAPI = new ShortUrlAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
                        String[] strings = new String[]{url};
                        shortUrlAPI.expand(strings, new RequestListener() {
                            @Override
                            public void onComplete(String s) {
                                LogUtil.d(s);

                            }

                            @Override
                            public void onWeiboException(WeiboException e) {

                            }
                        });


                        Intent intent = new Intent(context, MyWebView.class);
                        intent.putExtra("url", "http://video.weibo.com/show?fid=1034:317e76a095b325e998441c0d9b81d60d");
                        context.startActivity(intent);

                        //Toast.makeText(context, "点击了网址：" + url, Toast.LENGTH_LONG).show();
                    }
                };
                spannableString.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

        }
        return spannableString;
    }

    public static SpannableString getWeiBoContent(String source, final Context context, EditText editText) {
        SpannableString spannableString = new SpannableString(source);
        //设置正则
        Pattern pattern = Pattern.compile(ALL);
        Matcher matcher = pattern.matcher(spannableString);

        if (matcher.find()) {
            editText.setMovementMethod(LinkMovementMethod.getInstance());
            matcher.reset();
        }

        while (matcher.find()) {
            final String at = matcher.group(1);
            final String topic = matcher.group(2);
            final String url = matcher.group(3);

            //处理@用户
            if (at != null) {
                int start = matcher.start(1);
                int end = start + at.length();
                WeiBoContentClickableSpan myClickableSpan = new WeiBoContentClickableSpan(context) {
                    @Override
                    public void onClick(View widget) {
                        Toast.makeText(context, "点击了用户：" + at, Toast.LENGTH_SHORT).show();
                    }
                };
                spannableString.setSpan(myClickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            //处理##话题
            if (topic != null) {
                int start = matcher.start(2);
                int end = start + topic.length();
                WeiBoContentClickableSpan clickableSpan = new WeiBoContentClickableSpan(context) {

                    @Override
                    public void onClick(View widget) {
                        Toast.makeText(context, "点击了话题：" + topic, Toast.LENGTH_LONG).show();
                    }
                };
                spannableString.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            // 处理url地址
            if (url != null) {
                int start = matcher.start(3);
                int end = start + url.length();
                WeiBoContentClickableSpan clickableSpan = new WeiBoContentClickableSpan(context) {

                    @Override
                    public void onClick(View widget) {
                        Toast.makeText(context, "点击了网址：" + url, Toast.LENGTH_LONG).show();
                    }
                };
                spannableString.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

        }
        return spannableString;
    }
}
