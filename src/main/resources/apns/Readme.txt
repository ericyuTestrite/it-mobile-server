APNS

Bundle Id: com.testritegroup.app.testriteItPush



Send to Push-Server
===================
curl \
-H "Content-Type: application/json" \
-X POST -d '{"userId": "t2427","appId":"com.testritegroup.itpush", "uuid":"1234","devicePlatform":"iOS","regId":"regid12345"}' \
http://localhost:8082/pushRegister


Test Ad
===================
curl \
-H "Content-Type: application/json" \
-X POST -d '{"username": "t2427111","password":"12345"}' \
http://localhost:8082/adAuth

Send Push
==========
curl -H "Content-Type: application/json" -X POST -d '{"appId":"com.testritegroup.app.testriteItPush", "from":"t2427",  "receiverUserId":"t2427", "message":"太好了","title":"Testing" }' http://service.testritegroup.com/api/sendPush


curl -H "Content-Type: application/json" -X GET http://localhost:8082/pushLog/t2427/appId/com.testritegroup.app.testriteItPush