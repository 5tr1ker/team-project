package com.team.todaycheck.main.repository.Impl;

// �⺻ �ν���Ʈ
import static com.team.todaycheck.main.entity.QPost.post;
import static com.team.todaycheck.main.entity.QRecommander.recommander;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.team.todaycheck.main.entity.Post;
import com.team.todaycheck.main.entity.Recommander;
import com.team.todaycheck.main.repository.CustomPostRepository;

public class CustomPostRepositoryImpl extends QuerydslRepositorySupport implements CustomPostRepository {
	
	private JPAQueryFactory queryFactory;
	
	public CustomPostRepositoryImpl(JPAQueryFactory queryFactory) {
		super(Post.class);
		this.queryFactory = queryFactory;
	}
	
	/*
	 * ��ȸ�� 1 ������Ű�� ����
	 */
	@Override
	public void updateView(int postnumber) {
		JPAUpdateClause update = new JPAUpdateClause(getEntityManager(), post);
		
		update.where(post.postKey.eq(postnumber)).set(post.views , post.views.add(1)).execute();
	}

	/*
	 * ��õ�� Ȯ�� �� update 1 ����
	 */
	@Override
	public boolean increaseRecommander(int postNumber , String userId) {
		JPAUpdateClause update = new JPAUpdateClause(getEntityManager(), post);
		Recommander recommandResult = queryFactory.select(recommander).from(recommander).join(recommander.post , post).where(post.postKey.eq(postNumber).and(recommander.recommender.eq(userId))).fetchOne();
		Post postResult = queryFactory.select(post).from(post).where(post.postKey.eq(postNumber)).fetchOne();

		if(recommandResult == null) {
			postResult.addRecommander(Recommander.builder().recommender(userId).build());
			update.where(post.postKey.eq(postNumber)).set(post.recommendation , post.recommendation.add(1)).execute();
			return true;
		} else {
			return false;
		}
	}
	
}
