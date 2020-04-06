package com.brotek.selectionprogram.ui.test
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brotek.selectionprogram.Constant

import com.brotek.selectionprogram.R
import com.brotek.selectionprogram.adapter.SelectionAdapter
import com.brotek.selectionprogram.task.TaskManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton

@Suppress("DEPRECATION")
class TestFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = TestFragment()
    }

    private lateinit var viewModel: TestViewModel
    private lateinit var taskManager: TaskManager
    private val TAG = TestFragment.javaClass.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.test_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(TestViewModel::class.java)

        //fab
        val fab = root.findViewById<FloatingActionButton>(R.id.floatingActionButton_test)
        fab.setOnClickListener(this)

        //recyclerView
        val recycler = root.findViewById<RecyclerView>(R.id.recyclerView);
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(requireContext());
        viewModel.allSelections.observe(viewLifecycleOwner, Observer {
            val adapter = SelectionAdapter(it)
            recycler.adapter = adapter
        })
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        taskManager = TaskManager(requireActivity().application)
    }

    override fun onClick(v: View?) {
        Log.d(TAG, v?.id.toString())
        if(v?.id == R.id.floatingActionButton_test){
            val view = LayoutInflater.from(requireContext()).inflate(R.layout.selection_num,null)
            MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Enter number")
                    .setIcon(R.drawable.ic_investigation)
                    .setView(view)
                    .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                        val editText = view.findViewById<EditText>(R.id.editText_selection_num);
                        taskManager.task(Constant.HTTP+"192.168.50.98"+Constant.URL_SINGLE_ITEM,editText.text)
                    })
                    .show()
        }
    }
}
