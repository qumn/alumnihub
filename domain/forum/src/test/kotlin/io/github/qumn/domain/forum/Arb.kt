package io.github.qumn.domain.forum

import io.github.qumn.domain.forum.model.PostFactory
import io.github.qumn.domain.forum.model.Tag
import io.github.qumn.domain.system.api.user.model.UID
import io.github.qumn.framework.storage.arb.urn
import io.github.qumn.test.arb.id
import io.kotest.property.Arb
import io.kotest.property.arbitrary.bind
import io.kotest.property.arbitrary.list
import io.kotest.property.arbitrary.map
import io.kotest.property.arbitrary.string

fun Arb.Companion.post() =
    Arb.bind(
        Arb.id(),
        Arb.string(minSize = 1, maxSize = 20),
        Arb.string(),
        Arb.tags(),
        Arb.imgs()
    ) { id, title, content, tags, imgs ->
        PostFactory.createNew(UID(id), title, content, tags, imgs)
    }

fun Arb.Companion.tags() =
    Arb.list(Arb.string(minSize = 1, maxSize = 10)).map { it.map { Tag(it) } }

fun Arb.Companion.imgs() =
    Arb.list(Arb.urn())