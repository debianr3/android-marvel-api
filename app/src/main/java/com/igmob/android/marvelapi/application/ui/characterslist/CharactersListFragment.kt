package com.igmob.android.marvelapi.application.ui.characterslist

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.igmob.android.marvelapi.R
import com.igmob.android.marvelapi.domain.core.Character
import com.igmob.android.marvelapi.domain.core.CharactersListRepository
import com.igmob.android.marvelapi.domain.core.Resource
import com.igmob.android.marvelapi.infrastructure.networking.MarvelApi
import okhttp3.HttpUrl

class CharactersListFragment(private val repository: CharactersListRepository) : Fragment() {

    private lateinit var viewModel: CharactersListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_characters_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(
            this,
            CharactersListViewModel.Factory(repository)
        ).get(CharactersListViewModel::class.java)

        viewModel.successResourceState.observe(viewLifecycleOwner, Observer {
            populateCharacters(it)
        })

        viewModel.errorResourceState.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, "Error fetching characters", Toast.LENGTH_SHORT).show()
        })

        viewModel.fetchCharactersList()
    }

    private fun populateCharacters(resource: Resource.Success<List<Character>>) {
        val characters = resource.data
        // TODO: Show empty message
        val viewAdapter = CharacterListAdapter(characters ?: listOf())

        view!!.findViewById<RecyclerView>(R.id.characters_list_rv).apply {
            setHasFixedSize(true)

            layoutManager = LinearLayoutManager(context)

            adapter = viewAdapter

            addItemDecoration(
                MarginItemDecoration(
                    resources.getDimension(R.dimen.character_list_item_padding).toInt()
                )
            )
        }

        view!!.findViewById<TextView>(R.id.attributionText).apply {
            if (resource.attributionText == null) {
                visibility = View.GONE
            } else {
                visibility = View.VISIBLE
                text = resource.attributionText
            }
        }
    }

    companion object {
        fun newInstance(repository: CharactersListRepository) = CharactersListFragment(repository)
    }
}

class MarginItemDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = spaceHeight
            }
            left = spaceHeight
            right = spaceHeight
            bottom = spaceHeight
        }
    }
}

class CharacterListAdapter(
    private val characters: List<Character>
) :
    RecyclerView.Adapter<CharacterListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.characters_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(characters[position])
    }


    override fun getItemCount() = characters.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title = itemView.findViewById<TextView>(R.id.title)
        private val description = itemView.findViewById<TextView>(R.id.description)
        private val image = itemView.findViewById<SimpleDraweeView>(R.id.image)

        fun bind(character: Character) {
            title.text = character.name
            description.text = character.description

            val httpUrl = HttpUrl.parse(character.thumbnail.imageSquareUri())
            if (httpUrl != null) {
                val urlBuilder = httpUrl.newBuilder()
                MarvelApi.getHash(urlBuilder)
                image.setImageURI(urlBuilder.build().toString())
            }
        }
    }
}