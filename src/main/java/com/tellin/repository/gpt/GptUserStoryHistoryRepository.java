package com.tellin.repository.gpt;

import com.tellin.entity.gpt.GptUserStoryHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GptUserStoryHistoryRepository extends JpaRepository<GptUserStoryHistory,Long> {
}
