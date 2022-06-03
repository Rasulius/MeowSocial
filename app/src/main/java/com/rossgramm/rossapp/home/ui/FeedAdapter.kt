import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rossgramm.rossapp.R
import com.rossgramm.rossapp.base.common.LoadRandomImageFromAssets
import com.rossgramm.rossapp.home.data.Post

//TODO: добавить view binding

class FeedAdapter(private val listener: Listener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface Listener {
        fun openComments(postId: String)
    }

    private var posts: List<Post> = mutableListOf()
    private lateinit var context: Context

    // Базовый класс контента
    open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class CardViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val nickname: TextView = itemView.findViewById(R.id.home_nickname)
        val userAddress: TextView = itemView.findViewById(R.id.user_address)
        val comments: TextView = itemView.findViewById(R.id.post_comment)
        val postImage: ImageView = itemView.findViewById(R.id.post_image)

        // Просмотреть все комментарии
        val viewComment: TextView = itemView.findViewById(R.id.view_the_comment)

        //Кнопка меню '...'
        val homeBurger: TextView = itemView.findViewById(R.id.home_burger)
    }

    class FriendViewHolder(itemView: View) : BaseViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        when (ContentType.fromInt(viewType)) {
            ContentType.FRIENDS -> {
                val friendHolder = FriendViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_friends_first_element_home, parent, false)
                )
                return friendHolder
            }
            ContentType.CARD -> {
                return CardViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_home_post, parent, false)
                )
            }
        }
    }

    // Для первого элемента возвращаем карточку друзья, для второго типа карточку с контентом
    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            ContentType.FRIENDS.value
        } else {
            ContentType.CARD.value
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    fun updatePosts(newPosts: List<Post>) {
        posts = newPosts
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // Первый элемент список друзей
        if (position > 0) {
            val temp = holder as CardViewHolder
            temp.nickname.text = posts[position].author
            temp.userAddress.text = posts[position].address
            temp.comments.text = posts[position].message
            temp.homeBurger.text = "..."
            temp.viewComment.text = "Просмотреть все комментарии..."
            if (posts[position].picture_url == null) {
                LoadRandomImageFromAssets(temp.postImage, context).loadImage() //временно для тестов
            } else {
                Glide.with(temp.postImage)
                    .load(posts[position].picture_url)
                    .into(temp.postImage)
            }
            // Обрабатываем открытие комментария
            temp.viewComment.setOnClickListener {
                val postID = posts[position].id
                listener.openComments(postID!!)
            }
            LoadRandomImageFromAssets(temp.postImage, context).loadImage()
        }
    }
}

enum class ContentType(val value: Int) {
    FRIENDS(0),
    CARD(1);

    companion object {
        fun fromInt(value: Int) = values().first { it.value == value }
    }
}
