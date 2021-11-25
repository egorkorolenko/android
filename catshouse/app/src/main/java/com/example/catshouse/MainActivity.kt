package com.example.catshouse

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.catshouse.data.HotelContract.GuestEntry
import com.example.catshouse.data.HotelDbHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private var mDbHelper: HotelDbHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fab = findViewById<View>(R.id.button) as FloatingActionButton
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, EditorActivity::class.java)
            startActivity(intent)
        }
        mDbHelper = HotelDbHelper(this)
    }

    override fun onStart() {
        super.onStart()
        displayDatabaseInfo()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_insert_new_data -> {
                insertGuest()
                displayDatabaseInfo()
                return true
            }
            R.id.action_delete_all_entries ->{
                deleteAll()
            displayDatabaseInfo()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    @SuppressLint("SetTextI18n")
    private fun displayDatabaseInfo() {
        val db = mDbHelper!!.readableDatabase

        val projection = arrayOf(
            GuestEntry._ID,
            GuestEntry.COLUMN_NAME,
            GuestEntry.COLUMN_CITY,
            GuestEntry.COLUMN_GENDER,
            GuestEntry.COLUMN_AGE
        )

        val cursor = db.query(
            GuestEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )
        val displayTextView = findViewById<View>(R.id.text_view_info) as TextView
        cursor.use {
            displayTextView.text = "Таблица содержит " + cursor.count + " гостей.\n\n"
            displayTextView.append(
                GuestEntry._ID + " - " +
                        GuestEntry.COLUMN_NAME + " - " +
                        GuestEntry.COLUMN_CITY + " - " +
                        GuestEntry.COLUMN_GENDER + " - " +
                        GuestEntry.COLUMN_AGE + "\n"
            )

            val idColumnIndex = cursor.getColumnIndex(GuestEntry._ID)
            val nameColumnIndex = cursor.getColumnIndex(GuestEntry.COLUMN_NAME)
            val cityColumnIndex = cursor.getColumnIndex(GuestEntry.COLUMN_CITY)
            val genderColumnIndex = cursor.getColumnIndex(GuestEntry.COLUMN_GENDER)
            val ageColumnIndex = cursor.getColumnIndex(GuestEntry.COLUMN_AGE)

            while (cursor.moveToNext()) {
                val currentID = cursor.getInt(idColumnIndex)
                val currentName = cursor.getString(nameColumnIndex)
                val currentCity = cursor.getString(cityColumnIndex)
                val currentGender = cursor.getInt(genderColumnIndex)
                val currentAge = cursor.getInt(ageColumnIndex)
                displayTextView.append(
                    (("\n" + currentID + " - " +
                            currentName + " - " +
                            currentCity + " - " +
                            currentGender + " - " +
                            currentAge))
                )
            }
        }
    }

    private fun insertGuest() {

        val db = mDbHelper!!.writableDatabase
        val values = ContentValues()
        values.put(GuestEntry.COLUMN_NAME, "Мурзик")
        values.put(GuestEntry.COLUMN_CITY, "Мурманск")
        values.put(GuestEntry.COLUMN_GENDER, GuestEntry.GENDER_MALE)
        values.put(GuestEntry.COLUMN_AGE, 7)
        db.insert(GuestEntry.TABLE_NAME, null, values)
    }


    private fun deleteAll() {
        val db = mDbHelper!!.writableDatabase
        db.delete(GuestEntry.TABLE_NAME,null,null)
    }
}