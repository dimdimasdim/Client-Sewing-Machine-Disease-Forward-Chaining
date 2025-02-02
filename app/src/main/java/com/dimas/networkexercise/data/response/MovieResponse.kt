package com.dimas.networkexercise.data.response


import com.dimas.networkexercise.domain.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Int?>?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("video")
    val video: Boolean?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
) {

    fun mapToMovie(): Movie {
        return Movie(
            id = id ?: 0,
            image = "https://image.tmdb.org/t/p/w500${posterPath.orEmpty()}",
            title = title.orEmpty(),
            desc = overview.orEmpty(),
            vote = voteCount ?: 0,
            voteAverage = voteAverage ?: 0.0,
            releaseDate = releaseDate.orEmpty()
        )
    }

}

fun List<MovieResponse>?.mapToMovie(): List<Movie> {
    return this?.map { it.mapToMovie() }.orEmpty()
}