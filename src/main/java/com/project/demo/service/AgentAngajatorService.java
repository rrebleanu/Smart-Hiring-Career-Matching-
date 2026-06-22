package com.project.demo.service;

import com.project.demo.agent.AgentAngajator;
import com.project.demo.model.Anunt;
import org.springframework.stereotype.Service;

@Service
public class AgentAngajatorService {

    private final AgentAngajator agentAngajator;

    public AgentAngajatorService(AgentAngajator agentAngajator) {
        this.agentAngajator = agentAngajator;
    }

    public String proceseaza(String cvText, Anunt anunt ) {

        if (cvText.length() > 12000) {
            cvText = cvText.substring(0, 12000);
        }
        if(cvText.isEmpty()) cvText = "CV is empty";
        if(cvText.length() <= 400) cvText = "CV is too short";
        return agentAngajator.run(cvText, anunt.getDescriereJob());

    }
}