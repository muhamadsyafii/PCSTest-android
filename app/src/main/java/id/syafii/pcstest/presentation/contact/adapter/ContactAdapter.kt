package id.syafii.pcstest.presentation.contact.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.syafii.pcstest.databinding.ItemContactBinding
import id.syafii.pcstest.domain.model.Contact
import id.syafii.pcstest.presentation.contact.adapter.ContactAdapter.ContactViewHolder
import id.syafii.pcstest.utils.ext.debounceClick
import id.syafii.pcstest.utils.ext.loadImageCenterCrop

class ContactAdapter : ListAdapter<Contact, ContactViewHolder>(object : DiffUtil.ItemCallback<Contact>() {
  override fun areItemsTheSame(oldItem: Contact, newItem: Contact) = oldItem.id == newItem.id
  override fun areContentsTheSame(oldItem: Contact, newItem: Contact) = oldItem == newItem
}) {
  var onItemClick : (Contact) -> Unit = {}
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
    return ContactViewHolder(ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false))
  }
  override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
    holder.bindData(getItem(position))
  }
  inner class ContactViewHolder(private val binding : ItemContactBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindData(data : Contact){
      with(binding){
        ivPerson.loadImageCenterCrop(root.context, imageUrl = data.avatar)
        tvName.text = data.name
        tvAddress.text = data.city
        root.debounceClick {
          onItemClick.invoke(data)
        }
      }
    }
  }
}