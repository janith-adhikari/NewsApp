package com.ewind.newsapptest.data.source.remote.apis


import com.ewind.newsapptest.data.source.remote.model.Response
import com.ewind.newsapptest.util.Constant
import com.ewind.newsapptest.util.network.QueryConst
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines")
    fun getTopNews(
        @Query(QueryConst.PAGE) page: Int,
        @Query(QueryConst.PAGE_SIZE) pageSize: Int = Constant.PER_PAGE,
        @Query(QueryConst.COUNTRY) country: String? = "us",
        @Query(QueryConst.LANGUAGE) language: String? = "en",
        @Query(QueryConst.CATEGORY) category: String = "general"
    ): Observable<Response>

    @GET("everything")
    fun getNews(
        @Query(QueryConst.Q) keyword: String? = null,
        @Query(QueryConst.LANGUAGE) language: String? = "en",
        @Query(QueryConst.PAGE) page: Int,
        @Query(QueryConst.PAGE_SIZE) pageSize: Int = Constant.PER_PAGE
    ): Observable<Response>
}