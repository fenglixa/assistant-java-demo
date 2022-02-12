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
        // Authenticator authenticator = new IamAuthenticator("DJ_EDC_DBdxSqkQTlZ14nm3DPiIoMkmHaksb8y3ns-I-");
        // String instansID = "4222bd72-de61-4912-8fca-7fc1eeb2e1cb";
        // String assistantId = "f1a27a00-c792-49e6-946b-866c23975eaa";
        // String ServiceUrl = "https://api.au-syd.assistant.watson.cloud.ibm.com/instances/";
        // String textMessage = "你好";
        Authenticator authenticator = new IamAuthenticator("S6KL23uVaNoURlRfLaVGEk46ghCcDILUPvU3ETzZy5Gd");
        String instansID = "0f067339-148a-421f-a0f2-af7c679a2b3e";
        String assistantId = "485c93ac-774d-4cb7-b6cc-5bd5c15807d9";
        String ServiceUrl ="https://api.eu-gb.assistant.watson.cloud.ibm.com/instances/";
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
        MessageOptions messageOptions = new MessageOptions.Builder(assistantId, sessionId)
                .input(input).build();
        MessageResponse messageResponse = service.message(messageOptions).execute().getResult();

        System.out.println("Response message: " + messageResponse.getOutput().getGeneric().get(0).text());

        // delete the sessions
        DeleteSessionOptions options = new DeleteSessionOptions.Builder(assistantId, sessionId).build();
        service.deleteSession(options).execute();
    }
}