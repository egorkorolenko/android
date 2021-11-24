package com.example.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toast.*
import androidx.fragment.app.DialogFragment
import android.R




class MyDialogFragment: DialogFragment() {

    //Одна кнопка
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return activity?.let {
//            val builder = AlertDialog.Builder(it)
//            builder.setTitle("Важное сообщение!")
//                .setMessage("Покормите кота!")
//                .setPositiveButton("ОК, иду на кухню") {
//                        dialog, id ->  dialog.cancel()
//                }
//            builder.create()
//        } ?: throw IllegalStateException("Activity cannot be null")
//    }

    //две кнопки
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return activity?.let {
//            val builder = AlertDialog.Builder(it)
//            builder.setTitle("Выбор есть всегда")
//                .setMessage("Выбери пищу")
//                .setCancelable(true)
//                .setPositiveButton("Вкусная пища") { dialog, id ->
//                    makeText(activity, "Вы сделали правильный выбор",
//                        LENGTH_LONG
//                    ).show()
//                }
//                .setNegativeButton("Здоровая пища",
//                    DialogInterface.OnClickListener { dialog, id ->
//                        makeText(activity, "Возможно вы правы",
//                            LENGTH_LONG
//                        ).show()
//                    })
//            builder.create()
//        } ?: throw IllegalStateException("Activity cannot be null")
//    }

    //три кнопки
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return activity?.let {
//            val builder = AlertDialog.Builder(it)
//            builder.setTitle("Выбор есть всегда")
//                .setMessage("Выбери пищу")
//                .setCancelable(true)
//                .setPositiveButton("Вкусная пища") { dialog, id ->
//                    makeText(activity, "Вы сделали правильный выбор",
//                        LENGTH_LONG
//                    ).show()
//                }
//                .setNegativeButton("Здоровая пища",
//                    DialogInterface.OnClickListener { dialog, id ->
//                        makeText(activity, "Возможно вы правы",
//                            LENGTH_LONG
//                        ).show()
//                    })
//                .setNeutralButton("Вкусная и здоровая пища"){
//                    dialog,id ->
//                    makeText(activity,"Вы нашли компромисс", LENGTH_LONG).show()
//                }
//            builder.create()
//        } ?: throw IllegalStateException("Activity cannot be null")
//    }

    //список
    private val catNames = arrayOf("Васька", "Рыжик", "Мурзик")

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return activity?.let {
//            val builder = AlertDialog.Builder(it)
//            builder.setTitle("Выберите кота")
//                .setItems(catNames
//                ) { dialog, which ->
//                    Toast.makeText(activity, "Выбранный кот: ${catNames[which]}",
//                        Toast.LENGTH_SHORT).show()
//                }
//            builder.create()
//        } ?: throw IllegalStateException("Activity cannot be null")
//    }

    //переключатели
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return activity?.let {
//            val selectedItems = ArrayList<Int>() // Where we track the selected items
//            val builder = AlertDialog.Builder(it)
//            builder.setTitle("Выберите любимое имя кота")
//                .setSingleChoiceItems(catNames, -1
//                ) { dialog, item ->
//                    Toast.makeText(activity, "Любимое имя кота:  ${catNames[item]}",
//                        Toast.LENGTH_SHORT).show()
//                }
//                .setPositiveButton("OK"
//                ) { dialog, id ->
//                     User clicked OK, so save the selectedItems results somewhere
//                     or return them to the component that opened the dialog
//                }
//                .setNegativeButton("Отмена") {
//                        dialog, id ->
//                }
//
//            builder.create()
//        } ?: throw IllegalStateException("Activity cannot be null")
//    }

//    //флажки
//    private val checkedItems = booleanArrayOf(false, true, false)
//
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return activity?.let {
//            val builder = AlertDialog.Builder(it)
//            builder.setTitle("Выберите котов")
//                .setMultiChoiceItems(catNames, checkedItems) {
//                        dialog, which, isChecked ->
//                    checkedItems[which] = isChecked
//                    val name = catNames[which]
//                    Toast.makeText(activity, name, Toast.LENGTH_LONG).show()
//                }
//                .setPositiveButton("Готово"
//                ) {
//                        dialog, id ->
//                    for (i in catNames.indices) {
//                        val checked = checkedItems[i]
//                        if (checked) {
//                            Log.i("Dialog", catNames[i])
//                        }
//                    }
//                }
//                .setNegativeButton("Отмена") {
//                        dialog, _ ->  dialog.cancel()
//                }
//            builder.create()
//        } ?: throw IllegalStateException("Activity cannot be null")
//    }

    //передать данные в активность
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Вы жертвуете миллион коту")
            .setTitle("Важно! Максимальный перепост")
            .setPositiveButton("OK",
                DialogInterface.OnClickListener { dialog, id -> (activity as MainActivity?)?.okClicked() })
            .setNegativeButton("Отмена",
                DialogInterface.OnClickListener { dialog, id -> (activity as MainActivity?)?.cancelClicked() })
        return builder.create()
    }
}
