# Android-App
Objective:
This application is used to find the location of a mobile phone and send automatic updates to another phone number on a time interval basis when the user moves out of a given radial distance.

Description:

The input parameters present in the UI of the application:-

1. Phone number to which the text message has to be sent
2. The minimum distance beyond which the message has to be triggered.
3. The time interval at which the messages has to be sent.

It uses 4 different services:

1. Internet- This helps in connecting to a network and use HTTP to send and receive data.
2. GPS: This helps in finding the current location and also get periodic location updates. It is also helpful in adding the starting point or initial location
3. Text messaging - SMS Manager is used to manage SMS operations to send text messages.
4. Geo coder- Geo coding and reverse Geo coding helps in transforming a street address into coordinates and vice-verse.

The input parameters are entered and submitted. Now, whenever the phone moves out of the threshold radius, a text message containing the current location is sent to the phone number provided on a time interval basis. 

Steps to run the application:
1. Open the application
2. Enter phone number of any mobile to send the updates on the location. Eg: 8588588585
3. Enter the minimum distance in meters which marks the boundary of the location after which the gps updates are monitored. Eg: 1
4. Enter the time interval at which the message has to be sent. Eg: 20
5. Press on START button to start the application.
6. Now, when the phone is moved to the distance mentioned above, it triggers the location update and sends message to the phone number provided.
   Eg: When the phone is moved to a distance of 1 meter, a message is sent to 8588588585 regarding the location(address) of the phone after every 20 minute.
7. Kill the application by pressing back button in the device.
8. Pressing the BACK button in the appliction minimises the application and it runs in the background
