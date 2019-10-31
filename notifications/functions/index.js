'use strict'
// The Firebase Admin SDK to access the Firebase Database.
const admin = require('firebase-admin');
// The Cloud Functions for Firebase SDK to create Cloud Functions and setup triggers.
const functions = require('firebase-functions');
//initializa admin
admin.initializeApp();
//tuition request notification
exports.notifyTuitionRequest = functions.firestore.document('tuitions/{tuitionId}').onCreate((change, context) => {
    const tuitionId = context.params.tuitionId;
    // get tuitions data
    return admin.firestore().collection("tuitions").doc(tuitionId).get().then(tuition=>{
        const tutorId = tuition.data().tutorId;
        const studentId = tuition.data().studentId;
        const senderId = tuition.data().senderId;
        //define function to get student data
        const student = admin.firestore().collection("students").doc(studentId).get();
        //get function to get tutor data
        const tutor = admin.firestore().collection("tutors").doc(tutorId).get();
        //get tutor and student data
        return Promise.all([student, tutor]).then(results=>{
            var name;
            var sender;
            var token;
            if(senderId === studentId){
                name = results[0].data().studentName;
                token = results[1].data().tokenId;
                sender = "student";
            }else if(senderId === tutorId){
                name = results[1].data().tutorName;
                token = results[0].data().tokenId;
                sender = "tutor"
            }
            const senderName = name;
            const receiverTokenId = token;
            const senderType = sender;
            //log data for testing
            console.log("Tuition request send from: " + senderName);
            console.log("Sender type is: " + senderType);
            console.log("Receiver token id: " + receiverTokenId);
            //create notification
            const payLoad = { notification:{
                title : "Tuition Request From " + senderName,
                message : "Please click on notification to view request",
                icon : "default",
                click_action : "com.noumi.sms.TUITION_REQUEST_NOTIFICATION"
                },data: {
                    tuitionId: tuitionId,
                    type: "tuition_request"
                }
            };
            //send notification
            return admin.messaging().sendToDevice(receiverTokenId, payLoad).then(result=>{
                console.log("Tuition request sent");
                return true;
            });
        });
    });
});
//chat notification
exports.notifyChatRequest = functions.firestore.document('chats/{chatId}').onCreate((change, context) => {
    const chatId = context.params.chatId;
    // get chat data
    return admin.firestore().collection("chats").doc(chatId).get().then(chat=>{
        const tutorId = chat.data().tutorId;
        const studentId = chat.data().studentId;
        //define function to get student data
        const student = admin.firestore().collection("students").doc(studentId).get();
        //get function to get tutor data
        const tutor = admin.firestore().collection("tutors").doc(tutorId).get();
        
        //build notification
        return Promise.all([student, tutor]).then(results=>{
            const studentName = results[0].data().studentName;
            const tutorTokenId = results[1].data().tokenId;
            //log data for testing
            console.log("studentName: " + studentName);
            console.log("Tutor token id: " + tutorTokenId);
            //create notification
            const payLoad = { notification:{
                title : "New Chat started by " + studentName,
                message : "Please click on notification to view chat",
                icon : "default",
                click_action : "com.noumi.sms.CHAT_REQUEST_NOTIFICATION"
                },data: {
                    chatId: chatId,
                    studentName: studentName,
                    type: "chat_request"
                }
            };
            //send notification
            return admin.messaging().sendToDevice(tutorTokenId, payLoad).then(result=>{
                console.log("Chat request sent");
                return true;
            });
        });
    });
});
//rating notification
exports.notifyRatingUpdate = functions.firestore.document('ratings/{ratingId}').onUpdate((change, context) => {
    const ratingId = context.params.ratingId;
    // get chat data
    return admin.firestore().collection("ratings").doc(ratingId).get().then(rating=>{
        const tutorId = rating.data().tutorId;
        const studentId = rating.data().studentId;
        //define function to get student data
        const student = admin.firestore().collection("students").doc(studentId).get();
        //get function to get tutor data
        const tutor = admin.firestore().collection("tutors").doc(tutorId).get();

        //build notification
        return Promise.all([student, tutor]).then(results=>{
            const studentName = results[0].data().studentName;
            const tutorTokenId = results[1].data().tokenId;
            //log data for testing
            console.log("studentName: " + studentName);
            console.log("Tutor token id: " + tutorTokenId);
            //create notification
            const payLoad = { notification:{
                title : "You have been rated / reviewed by " + studentName,
                message : "Please click on notification to view ratings",
                icon : "default",
                click_action : "com.noumi.sms.RATING_UPDATE_NOTIFICATION"
                },data: {
                    ratingId: ratingId,
                    studentName: studentName,
                    type: "rating_update"
                }
            };
            //send notification
            return admin.messaging().sendToDevice(tutorTokenId, payLoad).then(result=>{
                console.log("Rating update notified");
                return true;
            });
        });
    });
});




