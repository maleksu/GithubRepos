package com.df.githubrepos.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.df.githubrepos.R
import com.df.githubrepos.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        val adapter = CommitAdapter()
        binding.commentsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.commentsRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
        binding.commentsRecyclerView.adapter = adapter
        setObservers()
    }

    private fun setObservers() {
        viewModel.selectedRepo.observe(this, { repo->
            repo?.commits?.let {
                (binding.commentsRecyclerView.adapter as CommitAdapter).updateItems(it)
            }?:run {
                (binding.commentsRecyclerView.adapter as CommitAdapter).updateItems(emptyList())
                Toast.makeText(this@MainActivity, "Can't find the repo", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.textToShare.observe(this, { string->
            if(string.isNotBlank()){
                Toast.makeText(this@MainActivity, string, Toast.LENGTH_SHORT).show()
                shareCommits(string)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchItem: MenuItem? = menu.findItem(R.id.action_search)
        searchItem?.let {

            val searchView = searchItem.actionView as SearchView

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        viewModel.loadData(query)
                        searchView.onActionViewCollapsed()
                    }
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    //Not used in case all data is downloaded from the server
                    return false
                }

            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    fun prepareCommits(v: View) {
        val selectedCommitsIdx = (binding.commentsRecyclerView.adapter as CommitAdapter).getSelectedItems()
        viewModel.prepareCommitsToShare(selectedCommitsIdx)
    }

    private fun shareCommits(text:String){
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type="text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(shareIntent,getString(R.string.send_by)))
    }
}