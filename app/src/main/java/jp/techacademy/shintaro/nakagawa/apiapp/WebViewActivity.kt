package jp.techacademy.shintaro.nakagawa.apiapp

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity: AppCompatActivity(), FragmentCallback {

    var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val intent = intent
        val shop = intent.getSerializableExtra("shop") as Shop
        webView.loadUrl(if (shop.couponUrls.sp.isNotEmpty()) shop.couponUrls.sp else shop.couponUrls.pc)

        var realmShop = FavoriteShop.findBy(shop.id)
        if (realmShop != null) {
            isFavorite = realmShop!!.isFavorite
        } else {
            isFavorite = false
        }
        favoriteImageView.apply {
            setImageResource(if (isFavorite) R.drawable.ic_star else R.drawable.ic_star_border) // Picassoというライブラリを使ってImageVIewに画像をはめ込む
            setOnClickListener {
                if (isFavorite) {
                    onDeleteFavorite(shop.id)
                } else {
                    onAddFavorite(shop)
                }
            }
        }
    }

    override fun onClickItem(shop: Shop) {
    }

    override fun onAddFavorite(shop: Shop) { // Favoriteに追加するときのメソッド(Fragment -> Activity へ通知する)
        FavoriteShop.insert(FavoriteShop().apply {
            this.id = shop.id
            this.name = shop.name
            this.address = shop.address
            this.imageUrl = shop.logoImage
            this.url = if (shop.couponUrls.sp.isNotEmpty()) shop.couponUrls.sp else shop.couponUrls.pc
            this.isFavorite = true
        })

        isFavorite = true
        favoriteImageView.setImageResource(if (isFavorite) R.drawable.ic_star else R.drawable.ic_star_border)
    }

    override fun onDeleteFavorite(id: String) { // Favoriteから削除するときのメソッド(Fragment -> Activity へ通知する)
        showConfirmDeleteFavoriteDialog(id)
    }

    private fun showConfirmDeleteFavoriteDialog(id: String) {
        AlertDialog.Builder(this)
            .setTitle(R.string.delete_favorite_dialog_title)
            .setMessage(R.string.delete_favorite_dialog_message)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                deleteFavorite(id)
                isFavorite = false
                favoriteImageView.setImageResource(if (isFavorite) R.drawable.ic_star else R.drawable.ic_star_border)
            }
            .setNegativeButton(android.R.string.cancel) { _, _ ->}
            .create()
            .show()
    }

    private fun deleteFavorite(id: String) {
        FavoriteShop.delete(id)
    }
}