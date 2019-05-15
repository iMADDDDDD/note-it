package com1032.org.iy00024_cw1;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    // Defining an adapter for an ArrayList of type String. It will hold the note inputs.
    private ArrayAdapter<String> mAdapter;

    // Defining an ArrayList of type String to hold every note input.
    private ArrayList<String> mNoteInputs = new ArrayList<String>();

    // Defining a ListView which will display every note input on the main screen.
    private ListView mListView;

    /**
     * This 3 following fields will hold the date for each note Input.
     */

    // Defining an ArrayList which hold the year of the due date.
    private ArrayList<Integer> yearArray;

    // Defining an ArrayList which hold the month of the due date.
    private ArrayList<Integer> monthArray;

    // Defining an ArrayList which hold the day of the due date.
    private ArrayList<Integer> dayArray;

    // Defining an ArrayList which hold the details of a note.
    private ArrayList<String> detailsArray;

    /**
     * Declaring 3 fields of type int to initiate the date to the current date.
     */

    /** year */
    private int year;
    /** month */
    private int month;
    /** day */
    private int day;


    // Defining static final field of type int which will show the dialog
    static final int DATE_DIALOG_ID = 100;

    // Application is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Sets the content to be the activity_main (See in res/layout/activity_main.xml for more information).
        setContentView(R.layout.activity_main);

        // When application is created, we initialize 4 ArrayLists which hold all details for the note.
        /** Initializing ArrayList for year */
        yearArray = new ArrayList<>();
        /** Initializing ArrayList for month */
        monthArray = new ArrayList<>();
        /** Initializing ArrayList for day */
        dayArray = new ArrayList<>();
        /** Initializing ArrayList for details of note */
        detailsArray = new ArrayList<>();

        // Initializing the Toolbar which holds all action buttons.
        Toolbar toolbar = (Toolbar) findViewById(R.id.action_toolbar);
        setSupportActionBar(toolbar);

        // Initializing the listView.
        mListView = (ListView)findViewById(R.id.notesView);
        // Initializing the ArrayAdapter.
        mAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.list_view, mNoteInputs);
        // 'Connecting' the ListView to the ArrayAdapter.
        mListView.setAdapter(mAdapter);



        // Setting every item of the listView on a long click listener
        /** A long click on an item will completely remove the note and its details */
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // It removes the note
                mNoteInputs.remove(position);
                // Including year
                yearArray.remove(position);
                // and month
                monthArray.remove(position);
                // and day
                dayArray.remove(position);
                // even the details of the note.
                detailsArray.remove(position);

                // Updates the listView
                mAdapter.notifyDataSetChanged();

                return false;
            }
        });


        /** This part defines the floating 'PLUS' button in the bottom right-corner */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /** Sets the floating button on listener */
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * On click of the floating button, a new AlertDialog shows up.
                 * It contains an EditText input. User will enter note details.
                 */
                final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

                // Sets the title of the alertDialog
                alert.setTitle("Add a title");

                // Declaring an EditText which will allow the user to input the title of the note.
                final EditText noteInput = new EditText(MainActivity.this);
                // Defining an EditText for adding details about the note.
                final EditText detail = new EditText(MainActivity.this);

                /** Consider this only for visual purposes */
                final TextView blank = new TextView(MainActivity.this);


                // Sets a placeHolder for the details input.
                detail.setHint("Add details...");

                // Setting a placeHolder for title input.
                noteInput.setHint("Add title...");

                // Defining the type of keyboard on screen (to avoid bugs)
                noteInput.setInputType(InputType.TYPE_CLASS_TEXT);
                detail.setInputType(InputType.TYPE_CLASS_TEXT);

                // Creating a View group to input every EditText on the alertDialog.
                View mapView = new View(MainActivity.this);
                Context context = mapView.getContext();

                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                // Adding the input of title and details
                layout.addView(noteInput);

                layout.addView(blank); /** This is only for visual purposes */

                layout.addView(detail);


                /** Setting every input into the alertDialog */
                alert.setView(layout);


                /** It shows automatically the keyboard for the ease of the user */
                InputMethodManager imm = (InputMethodManager)getSystemService(MainActivity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);


                // Setting a button 'Add' that will save every input for the note
                alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        // Converting the input of the user to String
                        String detailInput = detail.getText().toString();

                        // Converting the input of the user to String
                        String inputTitle = noteInput.getText().toString();


                        if (noteInput.length() == 0 || detailInput.length() == 0) {
                            Toast.makeText(MainActivity.this, R.string.fill, Toast.LENGTH_SHORT).show();
                        } else {
                            // Saving the title of the note into the ArrayList of notes
                            mNoteInputs.add(inputTitle);
                            // Updating the listView
                            mAdapter.notifyDataSetChanged();

                            // Saving the details of the input into the ArrayList of details
                            detailsArray.add(detailInput);

                            // Upon creating a note, a Toast will display a confirmation of creating the note
                            Toast bread = Toast.makeText(MainActivity.this, R.string.noteConfirmed, Toast.LENGTH_SHORT);
                            bread.show();

                            /** After adding the note, user will be invited to add a due date to its note */

                            // Getting the today's calendar
                            final Calendar calendar = Calendar.getInstance();
                            // Defining variables to current date
                            year = calendar.get(Calendar.YEAR);
                            month = calendar.get(Calendar.MONTH);
                            day = calendar.get(Calendar.DAY_OF_MONTH);

                            // Shows the date Picker.
                            showDialog(DATE_DIALOG_ID);

                        }


                    }
                });


                /** If user want to cancel its note input, only press the 'Cancel' button */

                // Defining a 'Cancel' button
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Only a confirmation message will be displayed confirming the cancellation of the note input
                        Toast bread = Toast.makeText(MainActivity.this, R.string.noteUnconfirmed, Toast.LENGTH_SHORT);
                        bread.show();
                    }
                });

                // Shows the alertDialog
                alert.show();
            }
        });


        /** Below is the code for each item on the listView */

        // Setting each item of ListView into a click listener
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /** onClick, an Alert dialog will show up, presenting all details about the note */
                AlertDialog.Builder alertDetails = new AlertDialog.Builder(MainActivity.this);

                // Creating a view to group up every detail View.
                View mapView = new View(MainActivity.this);
                Context context = mapView.getContext();

                /** Only for visual purposes */
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setGravity(Gravity.CENTER);

                // Defining a TextView which will display every information about the note
                final TextView dateView = new TextView(MainActivity.this);
                final TextView detailsView = new TextView(MainActivity.this);
                final TextView blankSpace = new TextView(MainActivity.this);
                final TextView blank2 = new TextView(MainActivity.this);

                blankSpace.setText("");
                blank2.setText("");

                // Setting the title of the Alert dialog to be the title of the note
                alertDetails.setTitle(mNoteInputs.get(position));

                // Adding every View to the layout
                layout.addView(blank2);
                layout.addView(dateView);
                layout.addView(blankSpace);
                layout.addView(detailsView);

                // Only for visual purposes
                dateView.setTextSize(20);
                detailsView.setTextSize(20);

                // Displaying the due date
                dateView.setText("Due date: " + dayArray.get(position) + "-" + monthArray.get(position) + "-" + yearArray.get(position));

                // Displaying the details of the note
                detailsView.setText(detailsArray.get(position));

                // Setting every View to the alert Dialog
                alertDetails.setView(layout);

                // Setting a button that will confirm the details of the note
                alertDetails.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Do nothing
                    }
                });


                // Shows the alert Dialog
                alertDetails.show();

            }
        });
    }

    /** This method allows to show the date picker, when user will have to chose a due date for its note */
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                DatePickerDialog datePicker = new DatePickerDialog(MainActivity.this, datePickerListener, year, month, day);
                return datePicker;

        }
        return null;
    }


    // Setting the DatePicker dialog into a listener. It will allow the user to pick his due date for the note
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            // Adds the selected year into the ArrayList of years
            yearArray.add(selectedYear);

            // Adds the selected month into the ArrayList of months (N.B: natural indexing causes a delay of 1 month in the due date, fixed)
            monthArray.add(selectedMonth + 1);

            // Adds the selected day into the ArrayList of days
            dayArray.add(selectedDay);

        }
    };



    /** Allows to initialize the main menu of the application */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }





    /** Allows every item of menu to be selected */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {

            /**
             * if user has clicked on 'add_note' icon, same procedure as the floating button will be applied
             */
            case R.id.add_note:

                final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

                alert.setTitle("Add a title");

                final EditText noteInput = new EditText(MainActivity.this); // This too
                final EditText detail = new EditText(MainActivity.this);
                final TextView blank = new TextView(MainActivity.this);


                detail.setHint("Add details...");

                noteInput.setInputType(InputType.TYPE_CLASS_TEXT);
                detail.setInputType(InputType.TYPE_CLASS_TEXT);

                View mapView = new View(MainActivity.this);
                Context context = mapView.getContext();

                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                layout.addView(noteInput);
                layout.addView(blank);
                layout.addView(detail);

                noteInput.setHint("Add title...");

                alert.setView(layout);

                InputMethodManager imm = (InputMethodManager)getSystemService(MainActivity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        String detailInput = detail.getText().toString();

                        String inputTitle = noteInput.getText().toString();


                        if (noteInput.length() == 0 || detailInput.length() == 0) {
                            Toast.makeText(MainActivity.this, R.string.fill, Toast.LENGTH_SHORT).show();
                        } else {
                            // Saving the title of the note into the ArrayList of notes
                            mNoteInputs.add(inputTitle);
                            // Updating the listView
                            mAdapter.notifyDataSetChanged();

                            // Saving the details of the input into the ArrayList of details
                            detailsArray.add(detailInput);

                            // Upon creating a note, a Toast will display a confirmation of creating the note
                            Toast bread = Toast.makeText(MainActivity.this, R.string.noteConfirmed, Toast.LENGTH_SHORT);
                            bread.show();

                            /** After adding the note, user will be invited to add a due date to its note */

                            // Getting the today's calendar
                            final Calendar calendar = Calendar.getInstance();
                            // Defining variables to current date
                            year = calendar.get(Calendar.YEAR);
                            month = calendar.get(Calendar.MONTH);
                            day = calendar.get(Calendar.DAY_OF_MONTH);

                            // Shows the date Picker.
                            showDialog(DATE_DIALOG_ID);

                        }


                    }
                });



                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast bread = Toast.makeText(MainActivity.this, R.string.noteUnconfirmed, Toast.LENGTH_SHORT);
                        bread.show();

                    }
                });

                alert.show();

                break;


            /** If user has clicked on the 'grade_app' icon */
            case R.id.grade_app:
                // It sends the user to web site
                String url = "http://strawpoll.me/7044154";
                // Starting a new intent
                Intent intent = new Intent(Intent.ACTION_VIEW);
                // Parsing the URL
                intent.setData(Uri.parse(url));
                // Starting the intent (opening a web browser at the destination URL)
                startActivity(intent);
                break;



            /** If user has clicked on 'calendar' icon */
            case R.id.show_calendar:
                // It only shows the calendar for the curiosity of user
                final Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    }

                };

                new DatePickerDialog(MainActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

                break;



            default:
                break;


        }
        return super.onOptionsItemSelected(item);
    }



    /** If application is paused */
    @Override
    protected void onPause() {
        super.onPause();

        // Defining shared preferences. They will help us storing every note and its details if app is closed.
        SharedPreferences prefs = getPreferences( MODE_PRIVATE );
        SharedPreferences.Editor editor = prefs.edit();

        // Make sure nothing is in the editor
        editor.clear();

        // Looping through the adapter which contains the notes
        for (int i = 0; i < mAdapter.getCount(); i++) {
            // Save them into the editor
            editor.putString(String.valueOf(i), mAdapter.getItem(i));
        }


        /** It puts every ArrayList size into the editor */
        editor.putInt("yearSize", yearArray.size());
        editor.putInt("monthSize", monthArray.size());
        editor.putInt("daySize", dayArray.size());
        editor.putInt("detailsSize", detailsArray.size());


        // Looping through the yearArray to save every value in it into the editor
        for (int j = 0; j < yearArray.size(); j++) {
            editor.putInt("yearValues" + j, yearArray.get(j));
        }

        // Looping through the monthArray to save every value in it into the editor
        for (int k = 0; k < monthArray.size(); k++) {
            editor.putInt("monthValues" + k, monthArray.get(k));
        }

        // Looping through the dayArray to save every value in it into the editor
        for (int l = 0; l < dayArray.size(); l++) {
            editor.putInt("dayValues" + l, dayArray.get(l));
        }

        // Looping through the detailsArray to save every value in it into the editor
        for (int m = 0; m < detailsArray.size(); m++) {
            editor.putString("detailsValues" + m, detailsArray.get(m));
        }



        editor.commit();
    }

    /** If application is resumed */
    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = getPreferences( MODE_PRIVATE );

        // Defining new ArrayLists (which are the same as before pausing the application)
        yearArray = new ArrayList<>();
        monthArray = new ArrayList<>();
        dayArray = new ArrayList<>();
        detailsArray = new ArrayList<>();


        // Making sure that the listView is empty
        mNoteInputs.clear();

        // Getting the size of every ArrayList previously stored in the editor
        int yearSize = prefs.getInt("yearSize", 0);
        int monthSize = prefs.getInt("monthSize", 0);
        int daySize = prefs.getInt("daySize", 0);
        int detailsSize = prefs.getInt("detailsSize", 0);

        // Looping and adding every value previously stored into the same ArrayList
        for (int j = 0; j < yearSize; j++) {
            yearArray.add(prefs.getInt("yearValues" + j, -1));
        }

        // Looping and adding every value previously stored into the same ArrayList
        for (int k = 0; k < monthSize; k++) {
            monthArray.add(prefs.getInt("monthValues" + k, -1));
        }

        // Looping and adding every value previously stored into the same ArrayList
        for (int l = 0; l < daySize; l++) {
            dayArray.add(prefs.getInt("dayValues" + l, -1));
        }

        // Looping and adding every value previously stored into the same ArrayList
        for (int m = 0; m < detailsSize; m++) {
            detailsArray.add(prefs.getString("detailsValues" + m, null));
        }

        // Looping and adding every value of the adapter to update the listView
        for (int i = 0;; ++i) {
            final String string = prefs.getString(String.valueOf(i), "");
            if (!string.equals("")) {
                mAdapter.add(string);
            } else {
                break;
            }
        }
    }
}
