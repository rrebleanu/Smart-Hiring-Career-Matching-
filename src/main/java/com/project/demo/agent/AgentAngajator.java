package com.project.demo.agent;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
public class AgentAngajator  {

    private final ChatClient chatClient;

    public AgentAngajator(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String run(String cvText, String jobDescription) {

        String userInput = """
            Compare this CV with the job description.

            JOB DESCRIPTION (IN ROMANIAN OR ENGLISH):
            %s

            CV (ROMANIAN OR ENGLISH):
            %s

            Give a score from 0 to 100.
            Explain why.
            """.formatted(jobDescription, cvText);


        return chatClient.prompt()
                .system("""
                You are an Romanian AI Hiring Agent.
                Evaluate how well a candidate matches a job.
                Give objective scores.
                Answer only in Romanian.
            """)
                .user(userInput)
                .call()
                .content();
    }
}


