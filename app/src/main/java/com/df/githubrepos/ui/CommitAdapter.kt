package com.df.githubrepos.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.df.githubrepos.databinding.CommitRowBinding
import com.df.githubrepos.domain.data.model.GithubCommitModel

class CommitAdapter : RecyclerView.Adapter<CommitsViewHolder>() {

    private var commits: List<GithubCommitModel> = emptyList()
    private val selected = HashSet<Int>()

    private lateinit var binding: CommitRowBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitsViewHolder {
        binding = CommitRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommitsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommitsViewHolder, position: Int) {
        val commit = commits[position]
        holder.bind(commit, selected.contains(position))
        holder.itemView.setOnClickListener {
            if (!selected.remove(position)) {
                selected.add(position)
            }
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = commits.size

    fun updateItems(items: List<GithubCommitModel>) {
        commits = items
        selected.clear()
        notifyDataSetChanged()
    }

    fun getSelectedItems(): HashSet<Int> {
        return selected
    }
}

class CommitsViewHolder(
    private val binding: CommitRowBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(commit: GithubCommitModel, selected: Boolean) {
        binding.commitModel = commit
        binding.selected = selected
    }
}