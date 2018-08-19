package com.example.dev4.glowroad.datamodels;

import com.example.dev4.glowroad.constant.Constants;
import com.example.dev4.glowroad.search.SearchResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IPhotoSearchServiceApi {

    String SEARCH_QUERY = "/services/rest/";
    String FORMAT_PARAM_NAME = "format";
    String NOJSONCB_PARAM_NAME = "nojsoncallback";
    String API_KEY_PARAM_NAME = "api_key";
    String PER_PAGE_PARAM_NAME = "per_page";
    String METHOD_PARAM_NAME = "method";
    String EXTRAS_PARAM_NAME = "extras";
   String PAGE_PARAM_NAME = "page";
    String TEXT_PARAM_NAME = "text";

    @GET(SEARCH_QUERY)
    Observable<SearchResponse> search(
            @Query(METHOD_PARAM_NAME) String method,
            @Query(API_KEY_PARAM_NAME) String apiKey,
            @Query(FORMAT_PARAM_NAME) String format,
            @Query(NOJSONCB_PARAM_NAME) String noJsonCallback,
            @Query(TEXT_PARAM_NAME) String text,
            @Query(EXTRAS_PARAM_NAME) String extras,
            @Query(PER_PAGE_PARAM_NAME) String perPage,
            @Query(PAGE_PARAM_NAME) int page);
}
