package com.xuan.lovean.utils.textviewhtml;

import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import com.xuan.lovean.utils.textviewhtml.helper.ImgGetter4Path;
import com.xuan.lovean.utils.textviewhtml.helper.ImgGetter4Url;

/**
 * TextView使用Html格式的工具类
 * 
 * @author xuan
 */
public abstract class TextViewHtmlUtils {

    /**
     * 设置html文本格式的内容给textView，但是处理不了图像
     * 
     * @param textView
     * @param htmlStr
     */
    public static void setTextByHtml(TextView textView, String htmlStr) {
        Spanned text = Html.fromHtml(htmlStr);
        textView.setText(text);
    }

    /**
     * 这里的图像和自定义tag要自定义实现
     * 
     * @param textView
     * @param htmlStr
     * @param imageGetter
     * @param tagHandler
     */
    public static void setTextByHtml(TextView textView, String htmlStr, Html.ImageGetter imageGetter,
            Html.TagHandler tagHandler) {
        Spanned text = Html.fromHtml(htmlStr, imageGetter, tagHandler);
        textView.setText(text);
    }

    /**
     * 一般不需要自定tagHandler，就设置成null就可以了
     * 
     * @param textView
     * @param htmlStr
     * @param imageGetter
     */
    public static void setTextByHtml(TextView textView, String htmlStr, Html.ImageGetter imageGetter) {
        TextViewHtmlUtils.setTextByHtml(textView, htmlStr, imageGetter, null);
    }

    /**
     * 会有图片的处理，图片是从网络上加载
     * 
     * @param textView
     * @param htmlStr
     */
    public static void setTextAndImgByHtml4Url(TextView textView, String htmlStr) {
        TextViewHtmlUtils.setTextByHtml(textView, htmlStr, new ImgGetter4Url(), null);
    }

    /**
     * 会有图片的处理，图片是从本地加载
     * 
     * @param textView
     * @param htmlStr
     */
    public static void setTextAndImgByHtml4Path(TextView textView, String htmlStr) {
        TextViewHtmlUtils.setTextByHtml(textView, htmlStr, new ImgGetter4Path(), null);
    }

}
