'use strict'
// The Firebase Admin SDK to access the Firebase Database.
const admin = require('firebase-admin');
// The Cloud Functions for Firebase SDK to create Cloud Functions and setup triggers.
const functions = require('firebase-functions');
//initializa admin
admin.initializeApp();

exports.sendTuitionRequestNotification = functions.firestore.document('tuitions/{tuitionId}').onCreate((change, context) => {
    const tuitionId = context.params.tuitionId;
    // get tuitions data
    return admin.firestore().collection("tuitions").doc(tuitionId).get().then(tuition=>{
        const tutorId = tuition.data().tutorId;
        const studentId = tuition.data().studentId;
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
                title : "Tuition Request From " + studentName,
                message : "Please click on notification to view request",
                icon : "default",
                click_action : "com.noumi.sms.TUITION_REQUEST_NOTIFICATION"
            }};
            //send notification
            return admin.messaging().sendToDevice(tutorTokenId, payLoad).then(result=>{
                console.log("Tuition request sent");
                return true;
            });
        });
    });
});
