package com.huantt.common.webpush
/**
 * @author huantt on 11/5/18
 */
class PushingResponse {

    Date time
    Notification notification
    Integer responseCode
    Long multicastId
    Integer success
    Integer failure
    List<HashMap<String, Object>> results
    PushingError error

    public PushingResponse fillResultsToken(List<String> clientTokens) {
        if (clientTokens.size() != this.results.size()) {
            throw new RuntimeException("Client tokens must be the same length as the results")
        }
        for (int i = 0; i < this.results.size(); i++) {
            this.results[i].put("token", clientTokens[i])
        }
        return this
    }

    public static class PushingError {
        List<String> clientTokens
        String errorMessage

        public PushingError(List<String> clientTokens, String errorMessage) {
            this.clientTokens = clientTokens
            this.errorMessage = errorMessage
        }
    }
}
