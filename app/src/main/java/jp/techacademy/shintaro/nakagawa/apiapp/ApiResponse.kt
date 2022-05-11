package jp.techacademy.shintaro.nakagawa.apiapp

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApiResponse(
    @SerializedName("results")
    var results: Results
)

data class Results(
    @SerializedName("shop")
    var shop: List<Shop>
)

data class Shop(
    @SerializedName("address")
    var address: String,
    @SerializedName("coupon_urls")
    var couponUrls: CouponUrls,
    @SerializedName("id")
    var id: String,
    @SerializedName("logo_image")
    var logoImage: String,
    @SerializedName("name")
    var name: String
): Serializable

data class CouponUrls(
    @SerializedName("pc")
    var pc: String,
    @SerializedName("sp")
    var sp: String
): Serializable