package helloworld;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class Function implements RequestHandler<Map<String, Object>, String> {

    private final String slackWebhookUrl = System.getenv("SLACK_WEBHOOK_URL");

    @Override
    public String handleRequest(Map<String, Object> input, Context context) {
        context.getLogger().log("Input: " + input);

        // Extract the issue information from the GitHub webhook payload
        String githubPayload = (String) input.get("body");
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode githubEvent = objectMapper.readTree(githubPayload);

            // Check if the event is an issue creation
            if ("issues".equals(githubEvent.get("event_type").asText())) {
                String htmlUrl = githubEvent.get("issue").get("html_url").asText();

                // Now, you can use the htmlUrl or post it to Slack or any other desired action
                context.getLogger().log("Issue HTML URL: " + htmlUrl);
                // Perform Slack posting or other actions here
            }
        } catch (IOException e) {
            context.getLogger().log("Error parsing GitHub payload: " + e.getMessage());
        }

        return "Success";
    }
}




