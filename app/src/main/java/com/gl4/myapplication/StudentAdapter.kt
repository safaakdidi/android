package com.gl4.myapplication

import android.R.*

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.*

import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList


class StudentAdapter
    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */(private val localDataSet: ArrayList<Student>) :
        RecyclerView.Adapter<StudentAdapter.ViewHolder>(),Filterable  {
    var dataFilterList = ArrayList<Student>()
    init {
        // associer le tableau des données initiales
        dataFilterList = localDataSet
    }
    override fun getFilter(): Filter {
        return object : Filter(){

           //run on background thread
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    dataFilterList = localDataSet
                } else {
                    val resultList = ArrayList<Student>()
                    for (student in localDataSet) {
                        if (student.prenom.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(student)
                        }
                    }
                    dataFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = dataFilterList
                return filterResults
            }
//runs in UI thread
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                dataFilterList = results?.values as ArrayList<Student>
                notifyDataSetChanged()
            }

        }
    }



        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder).
         */
        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
          val imgView :ImageView
          val txtName:TextView
          val txtGenre:TextView
          val checkbox:CheckBox

            init {
                // Define click listener for the ViewHolder's View
                imgView=view.findViewById(R.id.imageView)
                txtName=view.findViewById(R.id.textViewName)
                txtGenre=view.findViewById(R.id.textViewGenre)
                checkbox=view.findViewById(R.id.checkBox)
            }
        }

        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            // Create a new view, which defines the UI of the list item

            val view: View = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.student_item, viewGroup, false)
            return ViewHolder(view)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

            // Get element from your dataset at this position and replace the
            // contents of the view with that element

            viewHolder.txtName.text=localDataSet[position].nom+" "+localDataSet[position].prenom
            viewHolder.txtGenre.text=localDataSet[position].genre
            if (localDataSet[position].genre=="female"){
                viewHolder.imgView.setImageResource(R.drawable.avatar)
            }else{
                viewHolder.imgView.setImageResource(R.drawable.boy)
            }
            viewHolder.checkbox.setChecked(dataFilterList[position].present);
            viewHolder.checkbox.setOnCheckedChangeListener{
                buttonView,isChecked->
                localDataSet[position].present=!isChecked
            }

        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount(): Int {
            return localDataSet.size
        }




    }

