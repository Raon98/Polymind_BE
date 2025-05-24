package com.tellin.entity.gpt;

import com.tellin.entity.user.User;
import com.tellin.support.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gpt_user_story_history")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GptUserStoryHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long history_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "story_id")
    private GptGeneratedStory story;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "content")
    private GptGeneratedStory content;
}
