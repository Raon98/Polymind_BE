package com.tellin.entity.gpt;

import com.tellin.support.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "gpt_generated_story",
    indexes = {@Index(name = "level_and_style_and_topic_idx", columnList = "story_level, story_style, story_topic")})
public class GptGeneratedStory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long story_id;
    private String story_style;
    private String story_level;
    private String story_topic;
    @Lob
    private String content;

}
