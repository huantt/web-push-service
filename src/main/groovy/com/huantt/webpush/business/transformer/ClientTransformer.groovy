package com.huantt.webpush.business.transformer

import com.huantt.webpush.business.model.Client
import groovy.util.logging.Slf4j
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.util.WebUtils

import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author huantt on 11/28/18
 */
@Component
@Slf4j
class ClientTransformer {

    @Autowired
    HttpServletRequest request
    @Autowired
    HttpServletResponse response

    public void fillClientInfo(Client client) {
        try {
            String userAgent = request.getHeader('user-agent')
            String clientIp = request.getHeader('X-Forwarded-For') ?: request.getRemoteAddr()
            client.with {
                setReferer(request.getHeader('Referer'))
                setIp(clientIp)
                setUserAgent(userAgent)
            }
        } catch (Exception e) {
            log.warn("@Error while filling clientInfo: ", e)
        }
    }

    public void trackUniqueClient(Client client) {
        String firstTimeId = WebUtils.getCookie(request, 'first_time_id')?.value
        Integer subscribedTimes = WebUtils.getCookie(request, 'subscribed_times')?.value?.toInteger()

        if (firstTimeId == null) {
            response.addCookie(new Cookie('first_time_id', client._id.toString()))
        } else {
            client.setFirstTimeId(firstTimeId)
            client.setSubscribedTimes(++subscribedTimes)
        }
        response.addCookie(new Cookie('id', client._id.toString()))
        response.addCookie(new Cookie('subscribed_times', client.getSubscribedTimes().toString()))
    }

    public ObjectId getIdFromCookies() {
        String _id = WebUtils.getCookie(request, 'id')?.value
        return (_id) ? new ObjectId(_id) : null
    }
}
