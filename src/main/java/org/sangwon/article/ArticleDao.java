package org.sangwon.article;

import java.util.List;

import org.sangwon.chap11.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Repository
public class ArticleDao {

	static final String LIST_ARTICLES = "select articleId, title, userId, name, left(cdate,16) cdate from article order by articleId desc limit ?,?";

	static final String COUNT_ARTICLES = "select count(articleId) from article";

	static final String GET_ARTICLE = "select articleId, title, content, userId, name, left(cdate,16) cdate, udate from article where articleId=?";

	static final String ADD_ARTICLE = "insert article(title,content,userId,name) values(?,?,?,?)";
	
	/**
	 * 글 수정하는 sql
	 */
	static final String UPDATE_ARTICLE = "UPDATE article SET title=?, content=? WHERE articleId=?";
	/**
	 * 글 삭제하는 sql
	 */
	static final String DELETE_ARTICLE = "DELETE FROM article WHERE articleId=?";

	@Autowired
	JdbcTemplate jdbcTemplate;

	RowMapper<Article> articleRowMapper = new BeanPropertyRowMapper<>(
			Article.class);

	/**
	 * 글목록
	 */
	public List<Article> listArticles(int offset, int count) {
		return jdbcTemplate.query(LIST_ARTICLES, articleRowMapper, offset,
				count);
	}

	/**
	 * 글 목록 건수
	 */
	public int getArticlesCount() {
		return jdbcTemplate.queryForObject(COUNT_ARTICLES, Integer.class);
	}

	/**
	 * 글조회
	 */
	public Article getArticle(String articleId) {
		return jdbcTemplate.queryForObject(GET_ARTICLE, articleRowMapper,
				articleId);
	}

	/**
	 * 글등록
	 */
	public int addArticle(Article article) {
		return jdbcTemplate.update(ADD_ARTICLE, article.getTitle(),
				article.getContent(), article.getUserId(), article.getName());
	}

	/**
	 * 수정
	 */
	int updateArticle(Article article) {
		return jdbcTemplate.update(UPDATE_ARTICLE, article.getTitle(),
				article.getContent(), article.getArticleId());
	}

	/**
	 * 삭제
	 */
	int deleteArticle(String articleId){
		return jdbcTemplate.update(DELETE_ARTICLE, articleId);
		}
}

