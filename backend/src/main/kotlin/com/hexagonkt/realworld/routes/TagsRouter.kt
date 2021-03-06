package com.hexagonkt.realworld.routes

import com.hexagonkt.http.server.Router
import com.hexagonkt.realworld.injector
import com.hexagonkt.realworld.messages.TagsResponseRoot
import com.hexagonkt.realworld.services.Article
import com.hexagonkt.store.Store
import kotlin.text.Charsets.UTF_8

internal val tagsRouter = Router {
    val articles: Store<Article, String> = injector.inject<Store<Article, String>>(Article::class)

    get {
        val field = Article::tagList.name
        val tags = articles.findAll(listOf(field))
            .flatMap {
                it[field]?.let { tags ->
                    if (tags is Collection<*>)
                        tags.map { tag -> tag.toString() }
                    else
                        null
                } ?: emptyList()
            }
            .distinct()

        ok(TagsResponseRoot(tags), charset = UTF_8)
    }
}
