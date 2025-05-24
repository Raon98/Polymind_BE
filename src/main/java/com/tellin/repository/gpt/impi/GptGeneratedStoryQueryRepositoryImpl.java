package com.tellin.repository.gpt.impi;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tellin.entity.gpt.GptGeneratedStory;
import com.tellin.entity.gpt.QGptGeneratedStory;
import com.tellin.entity.gpt.QGptUserStoryHistory;
import com.tellin.repository.gpt.GptGeneratedStoryQueryCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GptGeneratedStoryQueryRepositoryImpl implements GptGeneratedStoryQueryCustomRepository {

    private  final JPAQueryFactory queryFactory;

    @Override
    public Optional<GptGeneratedStory> findUnseenStory(String userId, String style, String level, String topic) {
        QGptGeneratedStory g = QGptGeneratedStory.gptGeneratedStory;
        QGptUserStoryHistory h = QGptUserStoryHistory.gptUserStoryHistory;

        return Optional.ofNullable(
                queryFactory
                        .selectFrom(g)
                        .where(
                                g.story_style.eq(style),
                                g.story_level.eq(level),
                                g.story_topic.eq(topic),
                                g.story_id.notIn(
                                        JPAExpressions.select(h.story.story_id)
                                                .from(h)
                                                .where(h.user.userId.eq(userId))
                                )
                        )
                        .limit(1)
                        .fetchOne()
        );
    }

}
