package com.example.catshouse

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import com.example.catshouse.data.HotelContract.GuestEntry
import com.example.catshouse.data.HotelDbHelper

class EditorActivity : AppCompatActivity() {
    private var mNameEditText: EditText? = null
    private var mCityEditText: EditText? = null
    private var mAgeEditText: EditText? = null
    private var mGenderSpinner: Spinner? = null

    private var mGender = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        mNameEditText = findViewById<View>(R.id.edit_guest_name) as EditText
        mCityEditText = findViewById<View>(R.id.edit_guest_city) as EditText
        mAgeEditText = findViewById<View>(R.id.edit_guest_age) as EditText
        mGenderSpinner = findViewById<View>(R.id.spinner_gender) as Spinner
        setupSpinner()
    }

    private fun setupSpinner() {
        val genderSpinnerAdapter: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            this,
            R.array.array_gender_options, R.layout.support_simple_spinner_dropdown_item
        )
        genderSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        mGenderSpinner!!.adapter = genderSpinnerAdapter
        mGenderSpinner!!.setSelection(2)
        mGenderSpinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selection = parent.getItemAtPosition(position) as String
                if (!TextUtils.isEmpty(selection)) {
                    mGender = if (selection == getString(R.string.gender_female)) {
                        GuestEntry.GENDER_FEMALE
                    } else if (selection == getString(R.string.gender_male)) {
                        GuestEntry.GENDER_MALE
                    } else {
                        GuestEntry.GENDER_UNKNOWN
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                mGender = 2 // Unknown
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_editor, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save -> {
                insertGuest()
                finish()
                return true
            }
            R.id.action_delete ->
                return true
            R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertGuest() {
        val name = mNameEditText!!.text.toString().trim { it <= ' ' }
        val city = mCityEditText!!.text.toString().trim { it <= ' ' }
        val ageString = mAgeEditText!!.text.toString().trim { it <= ' ' }
        val age = ageString.toInt()
        val mDbHelper = HotelDbHelper(this)
        val db: SQLiteDatabase = mDbHelper.writableDatabase
        val values = ContentValues()
        values.put(GuestEntry.COLUMN_NAME, name)
        values.put(GuestEntry.COLUMN_CITY, city)
        values.put(GuestEntry.COLUMN_GENDER, mGender)
        values.put(GuestEntry.COLUMN_AGE, age)

        val newRowId = db.insert(GuestEntry.TABLE_NAME, null, values)

        if (newRowId == -1L) {
            Toast.makeText(this, "Ошибка при заведении гостя", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Гость заведён под номером: $newRowId", Toast.LENGTH_SHORT).show()
        }
    }
}