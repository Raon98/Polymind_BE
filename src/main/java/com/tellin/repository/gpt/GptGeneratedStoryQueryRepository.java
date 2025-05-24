package com.tellin.repository.gpt;

import com.tellin.entity.gpt.GptGeneratedStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface GptGeneratedStoryQueryRepository extends JpaRepository<GptGeneratedStory,Long>,GptGeneratedStoryQueryCustomRepository {

}
