package com.gl4.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ClassAttendance : AppCompatActivity() {

    val spinner : Spinner by lazy { findViewById(R.id.spinner) }
    lateinit var student:ArrayList<Student>
    lateinit var recycleView:RecyclerView
lateinit var search:EditText
 lateinit var filterlist:ArrayList<Student>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_attendance)
        student=setUpStudentModels()
        recycleView=findViewById(R.id.recyclerView)
        search=findViewById(R.id.search)
        var adapter= StudentAdapter(student)
        val textWatcher=object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                            adapter.filter.filter(p0) 
              
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                 Toast.makeText(this@ClassAttendance,"You clicked on search ",Toast.LENGTH_SHORT).show()
                             adapter.filter.filter(p0)

            }

            override fun afterTextChanged(p0: Editable?) {
                       adapter.filter.filter(p0)
            }

        }




        search.addTextChangedListener(textWatcher)



          //Adapter
        recycleView.setAdapter(adapter)
        recycleView.setLayoutManager(LinearLayoutManager(this))


               //spinner Adapter

        var matieres = listOf<String>("Cours","TP")

        spinner.adapter = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,matieres)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@ClassAttendance,"You clicked on ${matieres[position]} ",Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }

    }







   fun setUpStudentModels():ArrayList<Student>{
        var names = resources.getStringArray(R.array.student_name)
       var lastnames = resources.getStringArray(R.array.student_lastname)
       var genres=resources.getStringArray(R.array.student_genre)
       val list: ArrayList<Student> = ArrayList()
       for (i in names.indices){

           list.add(Student(names[i],lastnames[i],genres[i],true))
       }


       return list
    }

}