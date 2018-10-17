package com.dev.jaeder42.todoapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
  private lateinit var recyclerView: RecyclerView
  private lateinit var viewAdapter: RecyclerView.Adapter<*>
  private lateinit var viewManager: RecyclerView.LayoutManager
  private lateinit var floatingActionButton: FloatingActionButton

  private val todoItems = ArrayList<String>()

  override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_main)
      viewManager = LinearLayoutManager(this)
      todoItems.add("Test")
      viewAdapter = MyAdapter(todoItems)
      recyclerView = findViewById<RecyclerView>(R.id.todo_list).apply {
          setHasFixedSize(true)
          layoutManager = viewManager
          adapter = viewAdapter
      }
      floatingActionButton = findViewById(R.id.fab)
      floatingActionButton.setOnClickListener { _ ->
          addNewItemDialog()
      }
  }

  private fun addNewItemDialog() {
      val alert = AlertDialog.Builder(this)
      val itemEditText = EditText(this)
      alert.setMessage("Add New Item")
      alert.setTitle("Enter To Do Item Text")
      alert.setView(itemEditText)
      alert.setPositiveButton("Submit") { dialog, positiveButton ->
          todoItems.add(itemEditText.text.toString())
          println(todoItems.toString())
          viewAdapter.notifyDataSetChanged()
      }
      alert.show()
  }
}

class MyAdapter(private val myDataset: ArrayList<String>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
class MyViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

override fun onCreateViewHolder(parent: ViewGroup,
                                viewType: Int): MyAdapter.MyViewHolder {
    val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false) as TextView
    return MyViewHolder(textView)
}
override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    holder.textView.setOnLongClickListener{ view->
        val item = myDataset[position]
        myDataset.removeAt(position)
        this.notifyDataSetChanged()
        Snackbar.make(view, "Item removed", Snackbar.LENGTH_LONG )
                .setAction("UNDO", { _ ->
                    myDataset.add(position, item)
                    notifyDataSetChanged()
                }).show()
        true
    }
    holder.textView.text = myDataset[position]
}

override fun getItemCount() = myDataset.size
}





