# Configuration:
(src/main/resources/application.properties)

- firebase.serverkey: Your FCM server key. You can find it in https://console.firebase.google.com/u/0/project/{PROJECT_NAME}/settings/cloudmessaging/
- spring.data.mongodb.uri: Your mongodb URI

# APIs:
### Add client

POST: 
```
https://{{domain.com}}/clients
```

Body request:
```
{
  "token": "XXf4FwONwj0_8:APA91bFlljTtfuYh_EigPcJztvnYBZcJOF0uBmf3n7FmvFpZB2dW6tcvgrNgPPOKKdpLh2FmOziven8kMtrH5-4AYuak7hLLJG51nZ0LUCeuAcwGPyox8GTHCeaNDzvsX2VI1A8pJB5c",
  "subscription": {
    "endpoint": "https://fcm.googleapis.com/fcm/send/dS6OXnCXue4:APA91bF4BGmoir-IgktGXzf5qI4ANpoFQsloVC_pBEuqxvDZ545DmnEjGYeqXShb-A4YKssWctmmRTtSh6kRGxHJaWBrKALvUrK0Sl4700GskfMPGOaV_JlkcRfZ414zK20Pj3gAmkru",
    "keys": {
      "p256dh": "BFpHxFmVXmZJiuByOfinazH5lnFA-XvRrP1yLrg_ANmTdBcDWsdHjY2WeYVCZSn1VtSgmNR3rnSHBbuoGSKpS6k=",
      "auth": "Ox_6I2qXGihg29ySJW3PRQ=="
    }
  },
  "tags": {
    "source": "cpo",
    "os": "android",
    "other_tag: "value"
  }
}
```

Response:
```
{
    "data": {
        "_id": "5bf3ec5ebcf8b9003ebda357",
    "status_code": 200,
    "meta": null
}

```

### Preview 
(Preview number of valid subscribers)

POST:
```
https://{{domain.com}}/notifications/preview
```
Authen: 
```
Basic authentication
```

Body request:
```
{
  "filter": {
    "active": true,
    "$or": [
      {
        "tags.source": "cpo",
        "tags.offer_id": "kichmen-vn"
      }
    ]
  },
  "name": "Day la name",
  "description": "Day la description",
  "notification": {
    "title": "Đàn ông nên ăn gì thì tốt?",
    "body": "Những thức ăn có ích cho cậu nhỏ",
    "icon": "https://dantricdn.com/FaA3gEccccccccccccos/Image/2014/03b/buon-31314-346ff.jpg",
    "image": "https://img.infonet.vn/w800/Uploaded/2018/lqmcpqmv/2015_07_22/cv_1.jpg",
    "data": {
      "url": "http://bit.ly/2B4opNF"
    }
  }
}

```

Response:

```
{
    "data": {
        "total_count": 87
    },
    "status_code": 200,
    "meta": null
}
```


### Push 

POST:
```
https://{{domain.com}}/notifications/push
```
Authen: 
```
Basic authentication
```

Body request:
```
{
  "filter": {
    "active": true,
    "$or": [
      {
        "tags.source": "cpo",
        "tags.offer_id": "kichmen-vn"
      }
    ]
  },
  "name": "Day la name",
  "description": "Day la description",
  "notification": {
    "title": "Đàn ông nên ăn gì thì tốt?",
    "body": "Những thức ăn có ích cho cậu nhỏ",
    "icon": "https://dantricdn.com/FaA3gEccccccccccccos/Image/2014/03b/buon-31314-346ff.jpg",
    "image": "https://img.infonet.vn/w800/Uploaded/2018/lqmcpqmv/2015_07_22/cv_1.jpg",
    "data": {
      "url": "http://bit.ly/2B4opNF"
    }
  }
}
```

Response:

```
{
    "data": {
        "total_count": 87
    },
    "status_code": 200,
    "meta": null
}
```