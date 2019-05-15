# Note It
Version 1.4.2
Theoretical storage taken:  
- 6.58 MB (Tested on Samsung Galaxy A3, Android 5.0.2)
- 7.00 MB (Tested on Asus Memopad 7, Android 5.0)

## Objectives of the application
This application behaves like a "To-do list". User should be able to ADD notes and DELETING them.  
User can check notes more precisely, for example, the details of the note and the DUE date.  
User cannot amend notes. The only way to modify notes is to delete them and create new ones.  

## Requirements met
- Once installed for the first time, nothing is displayed in the to-do list.
- User can add notes to the list with 2 different ways (check section 5 for more details).
- User can delete notes from the list (check section 5 for more details).
- While adding a note, user is invited to add more details about the note and to choose a DUE date.
- Phones/Tablets with Android KitKat 4.4 (API 19) software can enjoy the application. 
 It includes support of rotation from portrait mode to landscape mode. Everything stays at its place when phone/tablet is rotated.
- Application provides a storing method, which stores every note and its details when app is exited and relaunched.

## Application details
### Adding notes
2 different ways have been provided to user to add notes:
 - In the menu bar (action bar), the 3rd icon counting from the right. 
 - A floating "+" button has been provided in the bottom-right corner of the screen for adding a note.
 
### Deleting notes
The only way to delete notes is to make long touch on the chosen item. Everything will be deleted, including the details of the note.


### Viewing details of the notes
To view details of notes (including DUE date and some precise information), a simple touch on the item will display an Alert dialog which displays every detail of the note. 
  
## Application description
### Design
Application is aimed to be simple and fancy for the user.
An action bar which contains the name of the application and 3 other utility methods has been provided for the ease of the user.
The first icon, from the right, displays only a calendar, for curious users.
The second icon opens an URL in the default browser of the user which allows him to rate the application.
The third icon allows the user to add a note and its details.
IMPORTANT NOTE: Allow device to support transition animation. It can be applied by performing the following instructions: 
 - Go to Settings, choose 'Developer Options'.
 - Look for 'Animation Transition scale'.
 - Change from 'OFF' to 'Transistion x1'.

### Extra features
 - A nice icon for the application has been made. A boot up screen has been provided. After 2 seconds, it starts the MainActivity.
 - The application makes use of the action bar with 3 different icons explained in /** Design */ section.
 - Adding and deleting notes with many different ways.
 - Making use of a calendar
 - Making use of an Alert Dialog.

© iMAD. All rights reserved.
