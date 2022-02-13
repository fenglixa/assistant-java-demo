package com.ibm.watson.assistant.v2;

import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.assistant.v2.model.*;

/**
 * assistant connect by java sdk example
 *
 */
public class App {
    public static void main(String[] args) throws Exception {
        Authenticator authenticator = new IamAuthenticator("API key");
        String instansID = "<instansID>";
        String assistantId = "<assistantId>";
        String ServiceUrl ="https://api.xxxx/instances/";
        String textMessage = "what's my credit";

        Assistant service = new Assistant("2022-02-22", authenticator);
        service.setServiceUrl(ServiceUrl + instansID);

        // get session ID
        CreateSessionOptions createSessionOptions = new CreateSessionOptions.Builder().assistantId(assistantId).build();
        SessionResponse sessionResponse = service.createSession(createSessionOptions).execute().getResult();
        String sessionId = sessionResponse.getSessionId();

        MessageInputOptions inputOptions = new MessageInputOptions.Builder().returnContext(true).build();
        MessageInput input = new MessageInput.Builder().messageType("text").text(textMessage).options(inputOptions)
                .build();

        // get the msg output
        MessageOptions messageOptions = new MessageOptions.Builder(assistantId, sessionId)
                .input(input).build();
        MessageResponse messageResponse = service.message(messageOptions).execute().getResult();

        System.out.println("Response message: " + messageResponse.getOutput().getGeneric().get(0).text());

        // delete the sessions
        DeleteSessionOptions options = new DeleteSessionOptions.Builder(assistantId, sessionId).build();
        service.deleteSession(options).execute();
    }
}