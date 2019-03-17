package com.huantt.common.webpush

import com.huantt.common.MapperUtil
import com.huantt.common.httpclient.HttpClient
import com.huantt.webpush.business.model.PushingResponse
import okhttp3.Response

import java.util.function.Consumer

/**
 * - That way uses firebase serverkey to push notification, and use the token to indentifies the client
 * (This token was be generated by firebase client library when the client subscribed)
 * This way's pros: We can send one notification to many clients in only one request(Maximum is 1000 tokens per one time)
 * - The native way use key pair to push notification, and use the subscription object to identifies the client
 * @author huantt on 11/4/18
 */
class NotificationSender {

    final String FIREBASE_ENDPOINT = "https://fcm.googleapis.com/fcm/send"
    final int MAXIMUM_TOKENS_SIZE = 1000

    String serverKey

    public NotificationSender(String serverKey) {
        this.serverKey = serverKey
    }

    public void push(Notification notification, List<String> clientTokens, Consumer<PushingResponse> callback) {
        int size = clientTokens.size()
        for (int i = 0; i < clientTokens.size(); i += MAXIMUM_TOKENS_SIZE) {
            int cursor = (i + MAXIMUM_TOKENS_SIZE < size) ? i + MAXIMUM_TOKENS_SIZE : size
            List<String> subClientTokens = clientTokens.subList(i, cursor)

            Map bodyRequestMap = [
                    'data'            : notification,
                    'registration_ids': subClientTokens
            ]
            String bodyRequest = MapperUtil.writeValueAsString(bodyRequestMap)
            Map headers = ['Authorization': "key=${this.serverKey}"]

            Response response = HttpClient.post(this.FIREBASE_ENDPOINT, headers, bodyRequest)
            PushingResponse pushingResponse
            if (response.code() == 200) {
                pushingResponse = MapperUtil.readValue(response.body().string(), PushingResponse.class)
                pushingResponse.with {
                    fillResultsToken(subClientTokens)
                    setResponseCode(response.code())
                    setNotification(notification)
                }

            } else {
                pushingResponse = new PushingResponse()
                pushingResponse.with {
                    setNotification(notification)
                    setError(new PushingResponse.PushingError(subClientTokens, response.body().string()))
                    setResponseCode(response.code())
                }

            }

            callback.accept(pushingResponse)
        }
    }

    /**
     * Serverkey is a  information. You don't have permission to access this key!
     * */
    @Deprecated
    public String getServerKey() throws RuntimeException {
        throw new RuntimeException("Request refused. Serverkey is a  information!")
    }
}