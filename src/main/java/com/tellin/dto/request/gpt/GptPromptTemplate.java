package com.tellin.dto.request.gpt;

import org.springframework.stereotype.Component;

@Component
public class GptPromptTemplate {
        public static final String SYSTEM_PROMPT = """
You are a strict JSON generator.
Only return a JSON array with 6 to 8 English sentences.
Each item must include the sentence in English and Korean and a breakdown in 'chunks'.
Do not stop early. If you return fewer than 6, it's invalid.
""";

        public static final String CHUNK_ANALYSIS_PROMPT = """
You are an AI English teacher assistant.

Write a %s-style English story for a %s-level learner.  
The story **must contain exactly 6 to 8 full sentences**, written in **simple and clear English**.  
If you return fewer than 6 sentences, it will be considered invalid.  

Topic: %s

For each sentence, return:
1. The English sentence (`sentenceEn`)
2. Its Korean translation (`sentenceKo`)
3. A list of "chunks" with:
   - `text`: the phrase
   - `role`: one of [subject, verb, object, modifier]
   - `orderIndex`: the phrase order starting from 0

Return everything as a **pure JSON array**.
**Do not wrap the response in markdown or code block.**
Make sure the response ends with a closing bracket `]`.

Example format:
[
  {
    "sentenceEn": "Example sentence.",
    "sentenceKo": "예시 문장입니다.",
    "chunks": [
      { "text": "Example", "role": "subject", "orderIndex": 0 },
      { "text": "sentence", "role": "object", "orderIndex": 1 }
    ]
  },
  ...
]
""";
    }
