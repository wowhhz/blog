package com.acefet.blog.convert;

import com.acefet.blog.entity.Article;
import com.acefet.blog.form.ArticleForm;
import com.acefet.blog.util.Util;
import com.acefet.blog.vo.ArticleVO;
import org.springframework.beans.BeanUtils;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ArticleConvert {

    /**
     * 表单对象转换成实例对象
     * @param articleForm
     * @return
     */
    public static Article articleFrom2Article(ArticleForm articleForm){
        Article article = new Article();
        BeanUtils.copyProperties(articleForm,article);
        article.setId(Util.generateShortUuid());
        article.setReleaseTime(new Timestamp(System.currentTimeMillis()));
        article.setLikeNum(0);
        article.setReadNum(0);
        return article;
    }

    /**
     * 实例对象转换成返回对象
     * @param article
     * @return
     */
    public static ArticleVO article2ArticleVO(Article article){
        ArticleVO articleVO = new ArticleVO();
        BeanUtils.copyProperties(article,articleVO);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
        articleVO.setReleaseTime(sdf.format(article.getReleaseTime()));
        return articleVO;
    }
}
