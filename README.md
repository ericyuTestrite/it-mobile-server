#Push Server inside the Enterprise

##功能
1. 提供所有 App 的推播註冊及推播平台
2. 可註冊不同 app 的 registrationId(Android), tokenId(iOS)
3. 公司內部 AD 帳號整合認證, 並實現一個使用者能夠使用多個不同 device
4. 提供 REST API 讓所有 App 都能整合

##REST API
* 判斷公司的 AD 帳號密碼是否相符, 帳號密碼對了, 就會回覆 “true”, 否則就回覆 “false”
    <pre>
    curl  -H "Content-Type: application/json"  -X POST -d '{"username": "t2427","password":"12345"}'  http://localhost:8082/adAuth
 </pre>
* Push Device Registration
    <pre>
    curl -H "Content-Type: application/json" -X POST -d '{"userId": "t2427","appId":"com.testritegroup.itpush", "uuid":"1234","devicePlatform":"iOS","regId":"regid12345"}'  http://localhost:8082/pushRegister
 </pre>

* Push Message
     <pre>
    curl  -H "Content-Type: application/json"  -X POST -d '{"appId":"com.testritegroup.app.testriteItPush",   "receiverUserId":"t2427", \
	  "message":"太好了",  "title":"Testing" }' http://localhost:8082/sendPush
 </pre>

* Get Push log
 <pre>
  curl -X GET http://localhost:8082/pushLog/t2427/appId/com.testritegroup.app.testriteItPush
 </pre>
##相關技術
1. 使用 SpringBoot 作為後端 REST Server (Java)
2. 使用 mongodb 作為後端 DB Server
