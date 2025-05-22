package com.tellin.dto.request.gpt;

import org.springframework.stereotype.Component;

@Component
public class GptPromptTemplate {
    public static final String CHUNK_ANALYSIS_PROMPT = """
        Write a short %s-style English story for a %s-level learner.
                                                The story should be no more than 10 sentences.
                                                Use simple, clear English.
      
                                                Topic: %s

        1. Split it into **chunks** based on grammatical roles:
           - subject
           - verb
           - object
           - modifier

        2. Return a **JSON object** that contains:
           - "chunks": an array of phrase objects with:
             - "text": the chunk text
             - "role": one of [subject, verb, object, modifier]
             - "orderIndex": the order of appearance in the sentence (starting from 0)
           - "meaningKo": the complete Korean translation of the original sentence

     
        """;
}
